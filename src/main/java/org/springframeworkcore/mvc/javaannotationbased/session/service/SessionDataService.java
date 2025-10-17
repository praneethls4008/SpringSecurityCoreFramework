package org.springframeworkcore.mvc.javaannotationbased.session.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SessionDataService {
	void deleteSession(String sessionId);
	void saveSession(String sessionId, Object sessionData, int time) throws JsonProcessingException;
	<T> T getSession(String key, Class<T> clazz) throws JsonProcessingException;
}
