package ch.hevs.samproc.loadermodule.utils.connections;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ch.hevs.samproc.loadermodule.tests.LoginTest2;
import ch.hevs.samproc.loadermodule.utils.ConnectionUtilities;
import ch.hevs.samproc.loadermodule.utils.FileManager;

public class ConnectionManager {


	public static HashMap<String, String> connectAndReturnLoginToken(String username, String password, String loginParameters){
		
		final String URLlogin = "http://project.ech-bpm.ch/api/user/login";
		
		HashMap<String, String> loginInformation = new HashMap<>();
		
		URL url;
		try {
			url = new URL(URLlogin);
		
			JSONObject account = new JSONObject();
			account.put("username", "alexolivieri");
			account.put("password", "papBILANCIA81");
			System.out.println(account.toString());
			
			HttpURLConnection conn = setUsernameAndPassword(url, username, password);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty(LoginTest2.HEADER_ACCEPT, LoginTest2.HEADER_JSON);
			conn.setRequestProperty("Content-Type", LoginTest2.HEADER_JSON);
			conn.setRequestProperty("Content-Encoding", "UTF-8");
			
			byte[] objectBytes = account.toString().getBytes();
			OutputStream out = conn.getOutputStream();
			out.write(objectBytes, 0, objectBytes.length);
			out.flush();
			out.close();
			
			// Send post request
//			conn.setDoOutput(true);
//			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//			wr.writeBytes(loginParameters);
//			wr.flush();
//			wr.close();
			
//			byte[] jsonBytes = json.getBytes("UTF-8");
//			output.write(jsonBytes);
			
//			byte[] objectBytes = object.toString().getBytes("UTF-8");
//			
//			conn.setDoOutput(true);
//			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//			wr.write(objectBytes);
//			wr.flush();
//			wr.close();
			

//			conn.setDoOutput(true);
//			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//			wr.write(object.toString());
//			wr.flush();
	
			int responseCode = conn.getResponseCode();
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + loginParameters);
			System.out.println("Response Code : " + responseCode);
			
			if(conn.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}else{
				System.out.println("Connection ok");
			}
	
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			conn.disconnect();
	
			//print result
			String responseString = response.toString();
			System.out.println(responseString);
			
			JSONObject responseJSON = new JSONObject(responseString);
			String token = responseJSON.get("token").toString();
			String sessionId = responseJSON.getString("sessid").toString();
			String sessionName = responseJSON.getString("session_name").toString();
			
			loginInformation.put(LoginTest2.TOKEN, token);
			loginInformation.put(LoginTest2.SESSION_ID, sessionId);
			loginInformation.put(LoginTest2.SESSION_NAME, sessionName);
			
			
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loginInformation;
	}
	
	public static String logout(String username, String password, String loginParameters, HashMap<String, String>loginInformation){
		
		final String URLlogout = "http://project.ech-bpm.ch/webservices/user/logout";
		
		URL url;
		
		StringBuffer stringBufferResponse = null;
		
		try {
			url = new URL(URLlogout);
			HttpURLConnection conn = setUsernameAndPassword(url, username, password);
			conn.setRequestMethod("POST");
			conn.setRequestProperty(LoginTest2.HEADER_TOKEN, loginInformation.get(LoginTest2.TOKEN));
			conn.setRequestProperty(LoginTest2.HEADER_COOKIE, createCookie(loginInformation.get(LoginTest2.SESSION_NAME), loginInformation.get(LoginTest2.SESSION_ID)));
			conn.setRequestProperty(LoginTest2.HEADER_ACCEPT, LoginTest2.HEADER_JSON);
			
			// Send post request
			conn.setDoOutput(true);
			DataOutputStream wrL = new DataOutputStream(conn.getOutputStream());
			wrL.writeBytes(loginParameters);
			wrL.flush();
			wrL.close();

			int responseCodeLogout = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + loginParameters);
			System.out.println("Response Code : " + responseCodeLogout);
			
			if(conn.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}else{
				System.out.println("Connection ok");
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String inputLineLogout;
			stringBufferResponse = new StringBuffer();
	
			while ((inputLineLogout = br.readLine()) != null) {
				stringBufferResponse.append(inputLineLogout);
			}
			br.close();
	
			//print result
		
			
			conn.disconnect();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stringBufferResponse.toString();
		
	}
	
	public static List<String> postANewFile(String username, String password, String loginParameters, HashMap<String, String>loginInformation){
		
		final String URLlogout = "http://project.ech-bpm.ch/webservices/file";
		
		URL url;
		
		StringBuffer stringBufferResponse = null;
		
		try {
			url = new URL(URLlogout);
			HttpURLConnection conn = setUsernameAndPassword(url, username, password);
			conn.setRequestMethod("POST");
			conn.setRequestProperty(LoginTest2.HEADER_TOKEN, loginInformation.get(LoginTest2.TOKEN));
			conn.setRequestProperty(LoginTest2.HEADER_COOKIE, createCookie(loginInformation.get(LoginTest2.SESSION_NAME), loginInformation.get(LoginTest2.SESSION_ID)));
			conn.setRequestProperty(LoginTest2.HEADER_ACCEPT, LoginTest2.HEADER_JSON);
//			conn.setRequestProperty("Content-Type", LoginTest2.HEADER_JSON + "; charset=UTF-8");
			
			String authFileEnc = new String(Base64.getEncoder().encode(FileManager.byteArrayFromFile("Files/file1.bpmn")));
			String filename = "file1.bpmn";
//			String filepath = "Files/file1.bpmn";
			String filepath = "public://file1.bpmn";
			
//			JSONObject jsonInfo = new JSONObject();
//			jsonInfo.put("file", authFileEnc);
//			jsonInfo.put("filename", filename);
//			jsonInfo.put("filepath", filepath);			
//				
//			JSONObject content = new JSONObject();
//			content.put("file", jsonInfo);
			
			JSONObject jsonInfo = new JSONObject();
			jsonInfo.put("file", authFileEnc);
			JSONObject jsonInfo1 = new JSONObject();
			jsonInfo1.put("filename", filename);
			JSONObject jsonInfo2 = new JSONObject();
			jsonInfo2.put("filepath", filepath);
			
			
			JSONArray infoArray = new JSONArray();
			infoArray.put(jsonInfo);
			infoArray.put( jsonInfo1);
			infoArray.put( jsonInfo2);
			
			JSONObject content = new JSONObject();
			content.put("file", infoArray);
			
			// Send post request
			conn.setDoOutput(true);
			DataOutputStream wrL = new DataOutputStream(conn.getOutputStream());
			wrL.writeBytes(loginParameters);
			System.err.println(URLEncoder.encode(content.toString(), "UTF-8"));
			wrL.writeBytes(URLEncoder.encode(content.toString(), "UTF-8"));
			wrL.flush();
			wrL.close();

			int responseCodeLogout = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + loginParameters);
			System.out.println("Post parameters : " + content.toString());
			System.out.println("Response Code : " + responseCodeLogout);
			
			if(conn.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}else{
				System.out.println("Connection ok");
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String inputLineLogout;
			stringBufferResponse = new StringBuffer();
	
			while ((inputLineLogout = br.readLine()) != null) {
				stringBufferResponse.append(inputLineLogout);
			}
			br.close();
	
			//print result
		
			
			conn.disconnect();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	private static HttpURLConnection setUsernameAndPassword(URL url, String username, String password){
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String authStringEnc =  ConnectionUtilities.getAuthenticatedString(username, password);
		conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
		return conn;
		
	}
	
	private static String createCookie(String sessionName, String sessionID){
		return String.format("%s=%s", sessionName, sessionID);
	}

}
