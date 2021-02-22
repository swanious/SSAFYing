package com.ssafy.ssafying.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig {

      @Value("${spring.redis.host}")
      private String redisHost;

      @Value("${spring.redis.port}")
      private int redisPort;

      @Value("${spring.redis.pass}")
      private String redisPass;

      @Bean
      public RedisConnectionFactory redisConnectionFactory(){
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
            redisStandaloneConfiguration.setPassword(redisPass);
            RedisConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
            return redisConnectionFactory;
      }

      @Bean
      public RedisTemplate<?, ?> redisTemplate() {
            RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory());
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            return redisTemplate;
      }
      
}