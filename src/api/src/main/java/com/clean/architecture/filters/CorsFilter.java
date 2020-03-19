package com.clean.architecture.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.clean.architecture.api.ApiApplicationProperties;

/**
 * Servlet Filter implementation class CorsFilter
 * 
 * @author sgregory
 */

@Component
public class CorsFilter implements Filter {
	
	@Override
	public void destroy() {}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}	

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		addHeaders(response);
		
        // For HTTP OPTIONS method reply to pre-flight request with OK status code -- per CORS handshake
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
		       
    	chain.doFilter(request, response);
	}
	
	private void addHeaders(HttpServletResponse resp) {
		resp.addHeader("Access-Control-Allow-Origin", ApiApplicationProperties.getCorsDomain());
        resp.addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST, DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
	}
}
