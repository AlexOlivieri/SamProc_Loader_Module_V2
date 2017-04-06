package ch.hevs.samproc.loadermodule.tests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import ch.hevs.samproc.loadermodule.utils.ConnectionUtilities;

public class DownloadFileOfAProcess {

	private static final String username = "project";
	private static final String password = "project2017";
	private static final String LOGINparameters = "username=alexolivieri&password=papBILANCIA81";
	
	public DownloadFileOfAProcess() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		System.out.println("Download State: " + downloadFile(username, password, LOGINparameters));
	}
	
	public static boolean downloadFile(String username, String password, String loginParameters){
		
		final String URLlogin = "http://project.ech-bpm.ch/fr/process/download/1010/bpmn";
		
		String saveDir = "Files";
		
		final int BUFFER_SIZE = 4096;
		
		HashMap<String, String> loginInformation = new HashMap<>();
		
		URL url;
		try {
			url = new URL(URLlogin);
		
			HttpURLConnection conn = setUsernameAndPassword(url, username, password);
			conn.setRequestMethod("GET");
			conn.setRequestProperty(LoginTest2.HEADER_ACCEPT, LoginTest2.HEADER_JSON);
			
			// Send post request
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(loginParameters);
			wr.flush();
			wr.close();
	
			int responseCode = conn.getResponseCode();
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + loginParameters);
			System.out.println("Response Code : " + responseCode);
			
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}else{
				System.out.println("Connection ok");
			}
			
			String filename = "";
			String disposition = conn.getHeaderField("Content-Disposition");
			String contentType = conn.getContentType();
			int contentLength = conn.getContentLength();
			
			if(disposition != null){
				int index = disposition.indexOf("filename=");
				if(index > 0){
					filename = disposition.substring(index + 10, disposition.length() -1);
				}
			}else{
				System.err.println("Disposition is nnull");
			}
	
			InputStream inputStream = conn.getInputStream();
			String saveFilePath = saveDir + File.separator + filename;
			
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);
			
			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while((bytesRead = inputStream.read(buffer)) != -1){
				outputStream.write(buffer, 0, bytesRead);
			}
			
			outputStream.close();
			inputStream.close();
			
			return true;
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
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

}
