package com.vamshikl.assignment.dbx.dbxIntegration.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	public Boolean isAuthorized(HttpServletRequest request) {
		HttpSession httpSession = request.getSession(false);
		if (null != httpSession && request.getHeader("Token").equals(httpSession.getAttribute("accessToken"))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public Boolean isValidRequest(HttpServletRequest request) {
		if (null != request.getHeader("Token")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
