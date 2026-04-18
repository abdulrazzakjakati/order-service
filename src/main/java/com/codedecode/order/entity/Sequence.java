package com.codedecode.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequences")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sequence {

    @Id
    private String id;

    @SuppressWarnings("squid:S1700")
    private long sequence;
}
