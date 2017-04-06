package ch.hevs.samproc.loadermodule.tests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.impl.client.DefaultHttpClient;

import ch.hevs.samproc.loadermodule.utils.ConnectionUtilities;

public class LogoutTest {

	public LogoutTest() {
		// TODO Auto-generated constructor stub
	}
	
	

	public static void main(String[] args) throws IOException {
		
		String username = "project";
		String password = "project2017";
		
		
		URL url = new URL("http://project.ech-bpm.ch/webservices/user/logout");
		HttpURLConnection conn = setUsernameAndPassword(url, username, password);
		conn.setRequestMethod("POST");
//		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("X-CSRF-Token", "W2MPO11g99KEL9pP5KYAqhpDv2drknj8_MqpbvsuZF8");
		
		String urlParameters = "username=alexolivieri&password=papBILANCIA81";
		
		// Send post request
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

//		BufferedReader in = new BufferedReader(
//		        new InputStreamReader(conn.getInputStream()));
//		String inputLine;
//		StringBuffer response = new StringBuffer();
//
//		while ((inputLine = in.readLine()) != null) {
//			response.append(inputLine);
//		}
//		in.close();

		//print result
//		System.out.println(response.toString());
		
		if(conn.getResponseCode() != 200){
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}else{
			System.out.println("Connection ok");
		}

	}
	
	private static HttpURLConnection setUsernameAndPassword(URL url, String username, String password){
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String authString = username + ":" + password;
//		String authStringEnc = new String(Base64.getEncoder().encode(authString.getBytes()));
		String authStringEnc =  ConnectionUtilities.getAuthenticatedString(username, password);
		conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
		return conn;
		
	}
}
