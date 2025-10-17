package org.springframeworkcore.mvc.javaannotationbased.session.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframeworkcore.mvc.javaannotationbased.session.service.SessionDataService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class SessionDataServiceImpl implements SessionDataService{

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private final JedisPool jedisPool;
	
	@Autowired
	public SessionDataServiceImpl(JedisPool jedisPool){
		this.jedisPool = jedisPool;
	}

	@Override
	public void saveSession(String sessionId, Object sessionData, int time) throws JsonProcessingException {
		try (Jedis jedis = jedisPool.getResource()){
			jedis.setex(sessionId, time, objectMapper.writeValueAsString(sessionData));
		}
	}

	
	@Override
	public <T> T getSession(String key, Class<T> clazz) throws JsonProcessingException {
        try (Jedis jedis = jedisPool.getResource()) {
            String value = jedis.get(key);
            if (value == null) return null;
            return objectMapper.readValue(value, clazz);
        }
    }

	@Override
	public void deleteSession(String sessionId) {
		try (Jedis jedis = jedisPool.getResource()){
			jedis.del(sessionId);
		}
		
	}
	
	
}
