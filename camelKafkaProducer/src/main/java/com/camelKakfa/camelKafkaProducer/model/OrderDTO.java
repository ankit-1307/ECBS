package com.camelKakfa.camelKafkaProducer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.time.LocalDateTime;

@CsvRecord(name = "orderDTO", separator = ",")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @DataField(pos = 1)
    private String orderId;

    @DataField(pos = 2)
    private String productName;

    @DataField(pos = 3,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime = LocalDateTime.now();
}
