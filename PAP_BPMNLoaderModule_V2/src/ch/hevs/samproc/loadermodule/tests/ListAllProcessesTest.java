package ch.hevs.samproc.loadermodule.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import ch.hevs.samproc.loadermodule.utils.ConnectionUtilities;

public class ListAllProcessesTest {

	public ListAllProcessesTest() {
		// TODO Auto-generated constructor stub
	}
	
	

	public static void main(String[] args) {
		
		String username = "project";
		String password = "project2017";
		
		StringBuilder stringBuilder = new StringBuilder();

		try{
			URL url = new URL("http://project.ech-bpm.ch/webservices/allprocess");
			//URL url = new URL("http://ech-bpm.ch/webservices/allprocess");
			//HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection conn = setUsernameAndPassword(url, username, password);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if(conn.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}else{
				System.out.println("Connection ok");
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line = reader.readLine()) != null){
				stringBuilder.append(line);
			}
			
			System.out.println(stringBuilder.toString());
			
		}catch (MalformedURLException e){
			e.printStackTrace();
		}catch (IOException e) {
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
		conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
		return conn;
		
	}
}
