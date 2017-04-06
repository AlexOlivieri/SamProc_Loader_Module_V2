package ch.hevs.samproc.loadermodule.utils.connections;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;

import org.json.JSONObject;

import ch.hevs.samproc.entities.Form;
import ch.hevs.samproc.loadermodule.tests.LoginTest2;
import ch.hevs.samproc.loadermodule.utils.ConnectionUtilities;
import ch.hevs.samproc.loadermodule.utils.FileManager;
import tests.JSONFromStringToJSONobject;

public class ConnectionManagerJSON {


	public static HashMap<String, String> connectAndReturnLoginToken(String username, String password, JSONObject account){
		
		final String URLlogin = "http://project.ech-bpm.ch/api/user/login.json";
		
		HashMap<String, String> loginInformation = new HashMap<>();
		
		URL url;
		try {
			url = new URL(URLlogin);
			
			HttpURLConnection conn = setUsernameAndPassword(url, username, password);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", LoginTest2.HEADER_JSON);

			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(account.toString());
			wr.flush();
			wr.close();
	
			int responseCode = conn.getResponseCode();
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + account.toString());
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
	
	public static String logout(String username, String password, JSONObject account, HashMap<String, String>loginInformation){
		
		final String URLlogout = "http://project.ech-bpm.ch/api/user/logout.json";
		
		URL url;
		
		StringBuffer stringBufferResponse = null;
		
		try {
			url = new URL(URLlogout);
			HttpURLConnection conn = setUsernameAndPassword(url, username, password);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			System.out.println(loginInformation.get(LoginTest2.TOKEN));
			conn.setRequestProperty(LoginTest2.HEADER_TOKEN, loginInformation.get(LoginTest2.TOKEN));
			conn.setRequestProperty(LoginTest2.HEADER_COOKIE, createCookie(loginInformation.get(LoginTest2.SESSION_NAME), loginInformation.get(LoginTest2.SESSION_ID)));
			conn.setRequestProperty("Content-Type", LoginTest2.HEADER_JSON);

			
			// Send post request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(account.toString());
			wr.flush();
			wr.close();
			

			int responseCodeLogout = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + account.toString());
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
	
	public static String postANewFile(String username, String password, JSONObject account, HashMap<String, String>loginInformation, String path, String file){
		
		final String URLlogout = "http://project.ech-bpm.ch/api/file.json";
		
		String fid = null;
		
		URL url;
		
		StringBuffer response = null;
		
		try {
			url = new URL(URLlogout);
			HttpURLConnection conn = setUsernameAndPassword(url, username, password);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty(LoginTest2.HEADER_TOKEN, loginInformation.get(LoginTest2.TOKEN));
			conn.setRequestProperty(LoginTest2.HEADER_COOKIE, createCookie(loginInformation.get(LoginTest2.SESSION_NAME), loginInformation.get(LoginTest2.SESSION_ID)));
			conn.setRequestProperty("Content-Type", LoginTest2.HEADER_JSON);
			
//			String authFileEnc = new String(Base64.getEncoder().encode(FileManager.byteArrayFromFile("Files/file1.bpmn")));
//			String filename = "file1.bpmn";
//			String filepath = "public://file1.bpmn";
			
			String fileWithPath = path.concat("/").concat(file);
			System.out.println(fileWithPath);
			
			byte[] bpmnFileAsByteArray = FileManager.byteArrayFromFile(fileWithPath);
			if(bpmnFileAsByteArray == null){
				return fid;
			}
			String authFileEnc = new String(Base64.getEncoder().encode(bpmnFileAsByteArray));
			String filename = file;
			String filepath = "public://".concat(file);
			
			
			JSONObject content = new JSONObject();
			JSONObject info = new JSONObject();
			info.put("file", authFileEnc);
			info.put("filename", filename);
			info.put("filepath", filepath);
			content.put("file", info);
			
			// Send post request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(content.toString());
			wr.flush();
			wr.close();

			int responseCodeLogout = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + account.toString());
			System.out.println("Post parameters : " + content.toString());
			System.out.println("Response Code : " + responseCodeLogout);
			
			if(conn.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}else{
				System.out.println("Connection ok");
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String inputLineLogout;
			response = new StringBuffer();
	
			while ((inputLineLogout = br.readLine()) != null) {
				response.append(inputLineLogout);
			}
			br.close();
			
			conn.disconnect();
			
			String responseString = response.toString();
			
			JSONObject responseObject = new JSONObject(responseString);
			fid = responseObject.get("fid").toString();
			
			System.out.println(fid);
			
		
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
		
		return fid;
		
	}
	
	public static String postANewProcess(String username, String password, JSONObject account, HashMap<String, String>loginInformation, String fid, Form form){
		
		final String URLlogout = "http://project.ech-bpm.ch/api/node.json";
		
		String nid = null;
		
		URL url;
		
		StringBuffer response = null;
		
		try {
			url = new URL(URLlogout);
			HttpURLConnection conn = setUsernameAndPassword(url, username, password);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty(LoginTest2.HEADER_TOKEN, loginInformation.get(LoginTest2.TOKEN));
			conn.setRequestProperty(LoginTest2.HEADER_COOKIE, createCookie(loginInformation.get(LoginTest2.SESSION_NAME), loginInformation.get(LoginTest2.SESSION_ID)));
			conn.setRequestProperty("Content-Type", LoginTest2.HEADER_JSON);
			
			JSONObject processContent = JSONFromStringToJSONobject.getProcessContent(fid, form);
			
			// Send post request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(processContent.toString());
			wr.flush();
			wr.close();

			int responseCodeLogout = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + account.toString());
			System.out.println("Post parameters : " + processContent.toString());
			System.out.println("Response Code : " + responseCodeLogout);
			
			if(conn.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}else{
				System.out.println("Connection ok");
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String inputLineLogout;
			response = new StringBuffer();
	
			while ((inputLineLogout = br.readLine()) != null) {
				response.append(inputLineLogout);
			}
			br.close();
			
			conn.disconnect();
			
			String responseString = response.toString();
			
			JSONObject responseObject = new JSONObject(responseString);
			nid = responseObject.get("nid").toString();
			
			System.out.println(responseString);
			
		
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
		
		return nid;
		
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
