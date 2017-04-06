package ch.hevs.samproc.loadermodule.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.json.JSONObject;

import ch.hevs.samproc.loadermodule.utils.ConnectionUtilities;

public class LoginTest {

	public LoginTest() {
		// TODO Auto-generated constructor stub
	}
	
	

	public static void main(String[] args)  {
		
		String username = "project";
		String password = "project2017";	
		
		URL url;
		try {
			url = new URL("http://project.ech-bpm.ch/webservices/user/login");
		
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			String authStringEnc =  ConnectionUtilities.getAuthenticatedString(username, password);
			conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
			
			String propertyToken1 = conn.getRequestProperty("Authorization");
			System.out.println("Property Token: " +propertyToken1);
			conn.setRequestMethod("POST");
	//		conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			
			String propertyToken2 = conn.getRequestProperty("Accept");
			System.out.println("Accept: " +propertyToken2);
			
			String urlParameters = "username=alexolivieri&password=papBILANCIA81";
			
			// Send post request
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	
			System.out.println(" Method : " + conn.getRequestMethod());
			
			int responseCode = conn.getResponseCode();
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
			
			if(conn.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}else{
				System.out.println("Connection ok");
			}
			
			InputStream is = conn.getInputStream();
			if(is == null){
				System.err.println("InputStream is null");
			}else{
				System.err.println("InputStream not null");
			}
	
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(is));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	
			//print result
			String responseString = response.toString();
			System.out.println(responseString);
			
			JSONObject responseJSON = new JSONObject(responseString);
			String token = responseJSON.get("token").toString();
			String sessionId = responseJSON.getString("sessid").toString();
			String sessionName = responseJSON.getString("session_name").toString();
			
			System.out.println(token);
			
//			conn.disconnect();
			
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			final String HEADER_COOKIE = "Cookie";
			String cookie = String.format("%s=%s", sessionName, sessionId);
			
			URL urllogout = new URL("http://project.ech-bpm.ch/webservices/user/logout");
			HttpURLConnection connLogout = (HttpURLConnection) urllogout.openConnection();
			connLogout.setRequestProperty("Authorization", "Basic " + authStringEnc);
//			HttpURLConnection connLogout = setUsernameAndPassword(urllogout, username, password);
			
	
	//		HttpURLConnection connLogout = setUsernameAndPassword(urllogout, username, password);
			connLogout.setRequestMethod("POST");
			connLogout.setRequestProperty("X-CSRF-Token", token);
//			connLogout.setRequestProperty("X-USER-SESSION-TOKEN", sessionId);
			connLogout.setRequestProperty(HEADER_COOKIE, cookie);
//			connLogout.setRequestProperty("Accept", "application/json");
//			connLogout.setRequestProperty("Accept-Charset", "UTF-8");
//			connLogout.setRequestProperty("Accept-Encoding", "application/json");
//			conn.setRequestProperty("Content-Type", "application/json");
			
//			urlParameters = "username=alexolivieri&password=papBILANCIA81";
			
			connLogout.setDoOutput(true);
			DataOutputStream wrL = new DataOutputStream(connLogout.getOutputStream());
			wrL.writeBytes(urlParameters);
			wrL.flush();
			wrL.close();
			
//			int responseCodeLogout = connLogout.getResponseCode();
	
			
			System.out.println("\nSending 'POST' request to URL : " + urllogout);
			System.out.println("Post parameters : " + urlParameters);
//			System.out.println("Response Code : " + responseCodeLogout);
			
			is = connLogout.getInputStream();
			if(is == null){
				System.err.println("InputStream is null");
			}else{
				System.err.println("InputStream not null");
			}
			
			BufferedReader br = new BufferedReader(
			        new InputStreamReader(is));
			
			String inputLineLogout;
			StringBuffer stringBufferResponse = new StringBuffer();
	
			while ((inputLineLogout = br.readLine()) != null) {
				System.out.println(inputLineLogout);
				stringBufferResponse.append(inputLineLogout);
			}
			in.close();
	
			//print result
			String responseStringLogout = response.toString();
			System.out.println(responseStringLogout);
			conn.disconnect();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		System.out.println("String Encoded: " +authStringEnc);
		
		conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
		
		String propertyToken1 = conn.getRequestProperty("Authorization");
		System.out.println("Property Token: " +propertyToken1);
		
		return conn;
		
	}
}
