package reactive;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.repeat;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class LoadSimulation extends Simulation {

  // 从系统变量读取 baseUrl、path和模拟的用户数
  String baseUrl = System.getProperty("base.url");
  String testPath = System.getProperty("test.path");
  int sim_users = Integer.parseInt(System.getProperty("sim.users"));
  HttpProtocolBuilder httpProtocol =
      http
          // Here is the root for all relative URLs
          .baseUrl(baseUrl)
          // Here are the common headers
          .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
          .doNotTrackHeader("1")
          .acceptLanguageHeader("en-US,en;q=0.5")
          .acceptEncodingHeader("gzip, deflate")
          .userAgentHeader(
              "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

    // 定义模拟的请求，重复30次
    ChainBuilder helloRequest = repeat(30).on(
        // 自定义测试名称
        exec(http("hello-with-latency")
            // 执行get请求
            .get(testPath))
            // 模拟用户思考时间，随机1~2秒钟
            .pause(1, 2)
    );

  // 定义模拟的场景
  ScenarioBuilder scn =
      scenario("hello")
          // 该场景执行上边定义的请求
          .exec(helloRequest);

  {
    // 配置并发用户的数量在30秒内均匀提高至sim_users指定的数量
    setUp(scn.injectOpen(rampUsers(sim_users).during(30)).protocols(httpProtocol));
  }
}
