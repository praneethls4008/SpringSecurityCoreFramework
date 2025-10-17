package org.springframeworkcore.mvc.javaannotationbased.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer{
	/**
	 * 	Implements WebApplicationInitializer and in onStartup(ServletContext) registers a DelegatingFilterProxy named (by default) "springSecurityFilterChain" and maps it to /*.
	 *	Optionally, allows insertion of extra filters (you can override methods to add other Filter instances before/after the delegating proxy) or limit mapping to specific servlet names.
	 *  This class Superclass registers the DelegatingFilterProxy(entry point for security)
	 *	empty — the superclass will register springSecurityFilterChain 
	 *	Requires Spring Security Web Jar
	 *	Servlet 3+ SPI: the container loads ServletContainerInitializer implementations declared in JARs under META-INF/services/javax.servlet.ServletContainerInitializer.
	 *	Spring supplies SpringServletContainerInitializer (in spring-web) and marks it with @HandlesTypes(WebApplicationInitializer.class).
	 *	The container calls SpringServletContainerInitializer.onStartup(...) and passes it the classes found on the classpath that implement/are assignable to WebApplicationInitializer.
	 *	SpringServletContainerInitializer instantiates each discovered WebApplicationInitializer and calls onStartup(ServletContext).
	 *	Result: your WebApplicationInitializer code runs and registers DispatcherServlet, listeners, filters, etc. — all programmatic, no web.xml needed.
	 *	Servlet container SPI = OS driver loader. Spring’s SpringServletContainerInitializer is a driver that discovers and runs the app’s init scripts (your WebApplicationInitializer).
	 * 	DelegatingFilterProxy is a Servlet Filter whose job is to find a bean in the Spring WebApplicationContext and invoke that bean’s doFilter() method. It is the bridge between Servlet container filter lifecycle and Spring beans.
	 */
	
	/**
	 * Client HTTP Request
		     |
		Servlet Container selects Filter chain for request path (order = registration order / servlet mapping)
		     |
		---- Filter 1 (e.g., DelegatingFilterProxy) ----> DelegatingFilterProxy
		     |                                              |
		     |                                              +--looks up target bean "springSecurityFilterChain"
		     |                                              |
		     v                                              v
		---- FilterChainProxy (target bean) ----------------> determines SecurityFilterChain for request
		     |
		     +-- Security Filter A: SecurityContextPersistenceFilter (loads SecurityContext from session)
		     +-- Security Filter B: CSRF filter, etc.
		     +-- Security Filter C: UsernamePasswordAuthenticationFilter (if login attempted)
		     +-- ...
		     +-- Security Filter N: FilterSecurityInterceptor (performs authorization)
		     |
		     v
		Next filter / servlet in original container chain (usually DispatcherServlet)
		     |
		DispatcherServlet -> HandlerMapping -> Controller -> return Response
		     |
		Response flows back through the same security filters in reverse: SecurityContextPersistenceFilter stores context
	 */
	
	
	/**
	 * 
	 */
	
	
	
}
