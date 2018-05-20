package com.vineyard.courseproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Optional;

@Configuration
//@Profile("dev") //@Profile(Constants.SPRING_PROFILE_PRODUCTION) or "default"
public class LocalRedisConfiguration {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

        try {

            Optional<String> redisUrl = Optional.ofNullable(System.getenv("REDISTOGO_URL"));

            if(redisUrl.isPresent()) {
                URI redisUri = new URI(redisUrl.get());

                redisStandaloneConfiguration.setHostName(redisUri.getHost());//localhost
                redisStandaloneConfiguration.setPort(redisUri.getPort());//6379
            } else {
                redisStandaloneConfiguration.setHostName("localhost");//localhost
                redisStandaloneConfiguration.setPort(6379);//6379
            }

            redisStandaloneConfiguration.setDatabase(0);
            redisStandaloneConfiguration.setPassword(RedisPassword.of("password"));

            JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
            jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));// 60s connection timeout

            return jedisConnectionFactory;
//
//
// SPRING BOOT 1.5.13

//            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//            jedisConnectionFactory.setUsePool(true);
//            jedisConnectionFactory.setHostName(redisUri.getHost());
//            jedisConnectionFactory.setPort(redisUri.getPort());
//            jedisConnectionFactory.setTimeout(60);
//             jedisConnectionFactory.setPassword(redisUri.getUserInfo().split(":",2)[1]);
//
//            return jedisConnectionFactory;
//
//            /** SPRING BOOT 2.0.2*/
//
//             RedisStandaloneConfiguratu
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}