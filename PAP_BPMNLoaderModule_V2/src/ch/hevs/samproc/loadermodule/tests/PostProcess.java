package ch.hevs.samproc.loadermodule.tests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import ch.hevs.samproc.loadermodule.utils.ConnectionUtilities;
import ch.hevs.samproc.loadermodule.utils.FileManager;

public class PostProcess {

	public PostProcess() {
		// TODO Auto-generated constructor stub
	}
	
	

	public static void main(String[] args) {
		
		String username = "project";
		String password = "project2017";	
		
		URL url;
		
		try {
			url = new URL("http://project.ech-bpm.ch/webservices/user/login");
			
		
			HttpURLConnection conn = setUsernameAndPassword(url, username, password);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			
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
			
//			if(conn.getResponseCode() != 200){
//				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
//			}else{
//				System.out.println("Connection ok");
//			}
	
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
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
			
			System.out.println(token);
			
			conn.disconnect();
			
			URL urlPostFile = new URL("http://project.ech-bpm.ch/webservices/file");
			
			HttpURLConnection connPostFile = setUsernameAndPassword(urlPostFile, username, password);
			connPostFile.setRequestMethod("POST");
			connPostFile.setRequestProperty("Content-Type", "application/json");
			connPostFile.setRequestProperty("Accept", "application/json");
			connPostFile.setRequestProperty("X-CSRF-Token", token);
			
			//String postFileParameters = "username=alexolivieri&password=papBILANCIA81";
			
			String authFileEnc = new String(Base64.getEncoder().encode(FileManager.byteArrayFromFile("Files/file1.bpmn")));
			String filename = "file1.bpmn";
			String filepath = "Files/file1.bpmn";
			
			String postFileParameters = "username=alexolivieri&password=papBILANCIA81"; // &file=" + authFileEnc + "&filename=" + filename +"&filepath=" + filepath;
			
			// Send post request
			connPostFile.setDoOutput(true);
			DataOutputStream wrPostFile = new DataOutputStream(connPostFile.getOutputStream());
			wrPostFile.writeBytes(postFileParameters);
			wrPostFile.flush();
			wrPostFile.close();
			
			int responseCodePostFile = connPostFile.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + urlPostFile);
			System.out.println("Post parameters : " + postFileParameters);
			System.out.println("Response Code : " + responseCodePostFile);
		
			if(connPostFile.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : " + connPostFile.getResponseCode());
			}else{
				System.out.println("Connection ok");
			}
			
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
		conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
		return conn;
		
	}
}
