package com.wubq.reactive.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author wubq
 * @since 2022/3/19 23:19
 */
@Table
@Data
public class Account implements Persistable<String>, Serializable {
    @Id
    private String id;
    private String number;
    private String customerId;
    private int amount;

    @Override
    @JsonIgnore
    public boolean isNew() {
        boolean b = StringUtils.isEmpty(id);
        if (b) {
            id = UUID.randomUUID().toString();
        }
        return b;
    }
}
