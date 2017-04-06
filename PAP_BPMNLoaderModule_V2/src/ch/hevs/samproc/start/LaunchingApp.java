package ch.hevs.samproc.start;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONObject;

import ch.hevs.samproc.bpmnparser.test.FormFromBPMN;
import ch.hevs.samproc.bpmnparser.test.XMLParser;
import ch.hevs.samproc.entities.Form;
import ch.hevs.samproc.entities.FormContract;
import ch.hevs.samproc.loadermodule.tests.json.JsonObjectsCreator;
import ch.hevs.samproc.loadermodule.utils.connections.ConnectionManagerJSON;

public class LaunchingApp {

	public static final String TOKEN = "Token";
	public static final String SESSION_ID = "SessionID";
	public static final String SESSION_NAME = "SessionName";
	
	public static final String HEADER_COOKIE = "Cookie";
	public static final String HEADER_TOKEN = "X-CSRF-Token";
//	public static final String HEADER_ACCEPT = "Accept";
	public static final String HEADER_JSON = "application/json";
	
	private static final String usernamePlatform = "project";
	private static final String passwordPlatform = "project2017";
	
	private static final String username = "alexolivieri";
	private static final String password = "papBILANCIA81"; 
	
	public static void main(String[] args) throws IOException {
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("BPMN Files", "bpmn");
		chooser.setFileFilter(filter);
		
		chooser.setMultiSelectionEnabled(true);
		
		List<String[]> files = new ArrayList<String[]>();
		
		JFrame frame = new JFrame("File chooser");
		
		int r = chooser.showSaveDialog(frame);
		if(r == JFileChooser.APPROVE_OPTION){
			File[] selectedFiles = chooser.getSelectedFiles();
			
			
			for(int i=0; i<selectedFiles.length; i++){
				String[] fileAndPath = new String[2];
				fileAndPath[0] = selectedFiles[i].getName();
				fileAndPath[1] = selectedFiles[i].getParent();
//				System.err.println("File and Path: " + fileAndPath[0]);
//				System.err.println("File and Path: " + fileAndPath[1]);
				
				files.add(fileAndPath);
			}
		}
		
		String[] one = files.get(0);
		String[] two = files.get(1);
		
		System.err.println("File and Path: " + one[0]);
		System.err.println("File and Path: " + two[0]);
		
		frame.dispose();
		
		JSONObject account = JsonObjectsCreator.createLoginObject(username, password);
		
		HashMap<String, String> loginInformation = ConnectionManagerJSON.connectAndReturnLoginToken(usernamePlatform, passwordPlatform, account);
		if(loginInformation == null){
			return;
		}
		
//		BufferedReader bR = new BufferedReader(new InputStreamReader(new FileInputStream("Files/Form.txt")));
//		
//		JSONObject formObject = null;
//		String line;
//		while((line = bR.readLine()) != null){
//			System.out.println(line);
//			formObject = new JSONObject(line);
//		}
//		bR.close();
//		
//		System.out.println("JSON object: " + formObject.toString());
//		Form form = new Form(formObject.getString(FormContract.FIELD_TITLE), 
//								formObject.getString(FormContract.FIELD_LANGUAGE), 
//								formObject.getString(FormContract.FIELD_LANDKARTE), 
//								formObject.getBoolean(FormContract.FIELD_IS_CONTACT_PERSON));
		
		List<FormFromBPMN> formFromBPMNList = new ArrayList<FormFromBPMN>();
		
//		String path = "Files";
		List<String> listOfPath = new ArrayList<String>();
		List<String> listOfBpmnFiles = new ArrayList<String>();
		for(int i=0; i<files.size(); i++){
			String[] file = files.get(i);
			listOfBpmnFiles.add(file[0]);
			listOfPath.add(file[1]);
			System.err.println("Important news: " + file[0]);
			System.err.println("Important news: " + file[1].substring(file[1].length()-5, file[1].length()));
			
			System.err.println(file[1].substring(file[1].length()-5, file[1].length()).concat("\\").concat(file[0]));
			
			FormFromBPMN tempForm = XMLParser.getFormFromFile(file[1].substring(file[1].length()-5, file[1].length()).concat("\\").concat(file[0]));
			formFromBPMNList.add(tempForm);
		}
//		listOfBpmnFiles.add("file1.bpmn");
//		listOfBpmnFiles.add("vc.bpmn");
		
		List<String> listOfPostedProcesses = new ArrayList<String>();
		
		String postedProcess = null;
		
		for(int i=0; i<listOfBpmnFiles.size(); i++){
			
			FormFromBPMN tempForm = formFromBPMNList.get(i);
			
			System.err.println(tempForm.getTitle());
			System.err.println(tempForm.getField_langue_du_mod_le());
			System.err.println(String.valueOf(tempForm.getField_landkarte()));
			System.err.println(tempForm.getField_personne_contact_bool());
			
			Form form = new Form(tempForm.getTitle(), 
							tempForm.getField_langue_du_mod_le(),
							String.valueOf(tempForm.getField_landkarte()),
							tempForm.getField_personne_contact_bool());
		
			String fid = ConnectionManagerJSON.postANewFile(usernamePlatform, passwordPlatform, account, loginInformation, listOfPath.get(0), listOfBpmnFiles.get(i));
			
			if(fid != null){
				postedProcess = ConnectionManagerJSON.postANewProcess(usernamePlatform, passwordPlatform, account, loginInformation, fid, form);
				if(postedProcess != null){
					listOfPostedProcesses.add(postedProcess);
				}
			}
		}
		
		for(int i=0; i<listOfPostedProcesses.size(); i++){
			System.out.println(listOfPostedProcesses.get(i));
		}
			
		String logoutState = ConnectionManagerJSON.logout(usernamePlatform, passwordPlatform, account, loginInformation);
		System.out.println(logoutState);
		

	}

}
