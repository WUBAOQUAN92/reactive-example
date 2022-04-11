package com.wubq.reactive.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author wubq
 * @since 2022/3/25 11:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "event") //collection名为event
public class MyEvent {
    @Id
    private Long id;    // 2
    private String message;
}
