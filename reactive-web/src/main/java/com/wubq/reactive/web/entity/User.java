package com.wubq.reactive.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wubq
 * @since 2022/3/23 16:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User implements Persistable<String>, Serializable {
    @Id
    private String id;      // 注解属性id为ID
    @Indexed(unique = true) // 注解属性username为索引，并且不能重复
    private String username;
    private String name;
    private String phone;
    private Date birthday;

    @Override
    public boolean isNew() {
        return StringUtils.isEmpty(id);
    }
}
