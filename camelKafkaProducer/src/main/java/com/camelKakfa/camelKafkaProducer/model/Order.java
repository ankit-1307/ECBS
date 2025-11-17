package com.camelKakfa.camelKafkaProducer.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Data
@AllArgsConstructor
@NoArgsConstructor
@CsvRecord(separator = ",",skipFirstLine = true)
@JacksonXmlRootElement(localName = "order")
public class Order {
    @DataField(pos = 1)
    @JacksonXmlProperty(localName = "orderId")
    private String orderId;
    @DataField(pos = 2)
    @JacksonXmlProperty(localName = "customerName")
    private String customerName;
    @DataField(pos = 3)
    @JacksonXmlProperty(localName = "productName")
    private String productName;
    @DataField(pos = 4)
    @JacksonXmlProperty(localName = "quantity")
    private String quantity;
    @DataField(pos = 5)
    @JacksonXmlProperty(localName = "orderDate")
    private String orderDate;
}
