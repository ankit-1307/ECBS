package com.camelKakfa.camelKafkaProducer.camel;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.springframework.stereotype.Component;

import com.camelKakfa.camelKafkaProducer.model.Order;
import com.camelKakfa.camelKafkaProducer.model.OrderDTO;

import lombok.extern.slf4j.Slf4j;

@Component
public class CamelRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file:input?fileName=orders.csv&noop=true")
                .unmarshal().bindy(BindyType.Csv, Order.class)
                .setHeader("TotalRecords", simple("${body.size}"))
                .split(body())
                .process(exchange -> {
                    Order order = exchange.getIn().getBody(Order.class);
                    OrderDTO dto = new OrderDTO();
                    dto.setOrderId(order.getOrderId());
                    dto.setProductName(order.getProductName());
                    dto.setDateTime(LocalDateTime.now());
                    exchange.getIn().setBody(dto);
                   // System.out.println("${body}");
                })
                // .end()
                .aggregate(constant(true), new GroupedBodyAggregationStrategy())
                .completionSize(header("TotalRecords"))
                .completionTimeout(1000) // safety net
                .marshal().bindy(BindyType.Csv, OrderDTO.class)
                .process(exchange -> {
                    String csvBody = exchange.getIn().getBody(String.class);
                    String header = "orderId,productName,dateTime";
                    exchange.getIn().setBody(header + "\n" + csvBody);
                })
                .to("file:output?fileName=output${date:now:ddMMyyyy_hhmmssSSS}.csv");
    }
}

@Slf4j
class OrderProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Order order = exchange.getIn().getBody(Order.class);
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setProductName(order.getProductName());
        dto.setDateTime(LocalDateTime.now());
//        System.out.println(dto);
        exchange.getIn().setBody(dto);
    }
}
