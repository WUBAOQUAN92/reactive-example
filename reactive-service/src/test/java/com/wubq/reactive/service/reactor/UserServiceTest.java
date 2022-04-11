package com.wubq.reactive.service.reactor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author wubq
 * @since 2022/3/20 23:46
 */
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    private FavoriteService favoriteService;

    @Test
    public void test() throws InterruptedException {
        Long userId = 1L;
        userService.getFavorites(userId, new Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> list) {
                if (list.isEmpty()) {
                    suggestionService.getSuggestions(new Callback<List<Favorite>>() {
                        public void onSuccess(List<Favorite> list) {
                            UiUtils.submitOnUiThread(() -> {
                                list.stream().limit(5).forEach(UiUtils::show);
                            });
                        }

                        public void onError(Throwable error) {
                            UiUtils.errorPopup(error);
                        }
                    });
                } else {
                    list.stream().limit(5).forEach(favId -> favoriteService.getDetails(favId, new Callback<Favorite>() {
                        public void onSuccess(Favorite details) {
                            UiUtils.submitOnUiThread(() -> UiUtils.show(details));
                        }

                        public void onError(Throwable error) {
                            UiUtils.errorPopup(error);
                        }
                    }));
                }
            }

            @Override
            public void onError(Throwable error) {
                UiUtils.errorPopup(error);
            }
        });
        Thread.sleep(1000);
    }

    @Test
    public void test2() {

        Long userId = 1L;
        userService.getFavorites(userId).flatMap(favoriteService::getDetails)
            .switchIfEmpty(suggestionService.getSuggestions()).take(5).publishOn(UiUtils.uiThreadScheduler())
            .subscribe(UiUtils::show, UiUtils::errorPopup);
    }
}
