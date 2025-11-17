package com.camelKakfa.camelKafkaProducer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CamelKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Logger logger = LoggerFactory.getLogger(CamelKafkaProducer.class);

    @PostMapping("/send")
    public String sendMessage(String message) {
        logger.info("Kafka Producer message: {}", message);
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy" +
                " " +
                "HH:mm:ss");

        kafkaTemplate.send("my-topic", message + " " + dateTime.format(formatter));
        return message;
    }
}
