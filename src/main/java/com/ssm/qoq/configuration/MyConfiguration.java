package com.ssm.qoq.configuration;

import com.ssm.qoq.entity.InviteCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class MyConfiguration {
    @Autowired
    private RedisConnectionFactory factory;
    @Bean
    public RedisTemplate<String, InviteCode> numTemplate(){
        RedisTemplate<String, InviteCode> template=new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<InviteCode>(InviteCode.class));
        template.setConnectionFactory(factory);
        return  template;
    }
    @Bean
    public RedisTemplate<String, String> codeTemplate(){
        RedisTemplate<String, String> template=new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(factory);
        return  template;
    }
}
