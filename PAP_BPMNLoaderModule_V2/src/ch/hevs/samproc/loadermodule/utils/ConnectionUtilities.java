package ch.hevs.samproc.loadermodule.utils;

import java.util.Base64;

public class ConnectionUtilities {

	public static String getAuthenticatedString( String username, String password){
		
		String authString = username + ":" + password;
		String authStringEnc = new String(Base64.getEncoder().encode(authString.getBytes()));
		
		System.out.println("Authentication String " + authStringEnc);
		
		return authStringEnc;
		
	}

}
