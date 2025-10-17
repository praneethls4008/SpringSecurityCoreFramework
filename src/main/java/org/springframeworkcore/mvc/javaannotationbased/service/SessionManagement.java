package org.springframeworkcore.mvc.javaannotationbased.service;

import java.util.Map;

public interface SessionManagement {
	public void saveSession(String sessionId, String username, boolean remember);
	public Map<String,String> getSession(String sessionId);
	public void deleteSession(String sessionId);
}
