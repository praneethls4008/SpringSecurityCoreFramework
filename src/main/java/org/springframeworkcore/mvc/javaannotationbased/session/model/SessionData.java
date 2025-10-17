package org.springframeworkcore.mvc.javaannotationbased.session.model;

public record SessionData(
		String sessionId, 
		String username, 
		String role, 
		long lastAccessTime
		)
{}