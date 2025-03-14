package com.example.booking.Config;

import com.example.booking.DTO.Event.BookingEvent;
import com.example.booking.DTO.Event.FlightBookingEvent;
import com.example.booking.DTO.Request.EmailRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private Map<String, Object> getCommonConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return configProps;
    }

    private <T> ConsumerFactory<String, T> createConsumerFactory(Class<T> clazz) {
        Map<String, Object> configProps = getCommonConfig();
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz);
        deserializer.addTrustedPackages("com.example.booking.DTO.Event", "com.example.booking.DTO.Request");
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConsumerFactory<String, EmailRequest> emailConsumerFactory() {
        return createConsumerFactory(EmailRequest.class);
    }

    @Bean
    public ConsumerFactory<String, BookingEvent> bookingConsumerFactory() {
        return createConsumerFactory(BookingEvent.class);
    }

    @Bean
    public ConsumerFactory<String, FlightBookingEvent> flightBookingConsumerFactory() {
        return createConsumerFactory(FlightBookingEvent.class);
    }

    @Bean
    public KafkaListenerContainerFactory<?> emailKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailConsumerFactory());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> bookingKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookingEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(bookingConsumerFactory());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> flightBookingKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FlightBookingEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(flightBookingConsumerFactory());
        return factory;
    }
}
