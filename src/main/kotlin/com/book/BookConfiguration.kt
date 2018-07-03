package com.book

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter


@Configuration
class BookConfiguration{

    @Bean
    internal fun protobufHttpMessageConverter(): ProtobufHttpMessageConverter {
        return ProtobufHttpMessageConverter()
    }

}