package cn.ecut.travel.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisTemplate redisTemplate; // 此时注入的是系统默认的，jdk序列化方式

    @Bean // 把方法的返回值交给IOC容器，此时就会覆盖默认的RedisTemplate
    public RedisTemplate redisTemplate() {
        redisTemplate.setKeySerializer(RedisSerializer.string()); // 修改key的序列化方式为string
        redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer()); // 修改value的序列化方式为为json
        return redisTemplate; // 返回模板对象
    }
}
