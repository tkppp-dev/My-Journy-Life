package com.tkppp.myjournylife.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Value("\${spring.redis.host}")
    lateinit var host: String

    @Value("\${spring.redis.port}")
    var port: Int = 0

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory{
        return LettuceConnectionFactory(host, port);
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, String>{
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.setConnectionFactory(redisConnectionFactory())

        // key, value serializer 설정 안할시 redis-cli 에서 이진데이터로 표시됩
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()

        // hashMap 을 저장할때 key, value Serializer 설정
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = StringRedisSerializer()
        return redisTemplate
    }
}