package org.springframeworkcore.mvc.javaannotationbased.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

	@Bean
	JedisPool jedis() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20);        // max connections
        poolConfig.setMaxIdle(10);         // idle connections
        poolConfig.setMinIdle(2);          // minimum idle
        poolConfig.setTestOnBorrow(true);  // validate before giving connection
        poolConfig.setTestOnReturn(true);

        return new JedisPool(poolConfig, "localhost", 6379);
	}
}
