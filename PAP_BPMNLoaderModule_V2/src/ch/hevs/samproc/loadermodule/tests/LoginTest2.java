package ch.hevs.samproc.loadermodule.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import ch.hevs.samproc.loadermodule.utils.connections.ConnectionManager;

public class LoginTest2 {

	public LoginTest2() {
		// TODO Auto-generated constructor stub
	}
	
	public static final String TOKEN = "Token";
	public static final String SESSION_ID = "SessionID";
	public static final String SESSION_NAME = "SessionName";
	
	public static final String HEADER_COOKIE = "Cookie";
	public static final String HEADER_TOKEN = "X-CSRF-Token";
	public static final String HEADER_ACCEPT = "Accept";
	public static final String HEADER_JSON = "application/json";
	
	private static final String username = "project";
	private static final String password = "project2017";
	
	private static final String LOGINparameters = "username=alexolivieri&password=papBILANCIA81";
	
	public static void main(String[] args) throws IOException {
		
		HashMap<String, String> loginInformation = ConnectionManager.connectAndReturnLoginToken(username, password, LOGINparameters);
		
		String logoutState = ConnectionManager.logout(username, password, LOGINparameters, loginInformation);
		System.out.println(logoutState);
		
//		List<String> listResponse = ConnectionManager.postANewFile(username, password, LOGINparameters, loginInformation);
	}
}
