package ch.hevs.samproc.bpmnparser.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser {

//	public static void main(String[] args) throws JSONException, IOException{
	
	public static FormFromBPMN getFormFromFile(String path){
		
	
		String xmlRepresentation = new String();
		
//		BufferedReader bR = new BufferedReader(new InputStreamReader(new FileInputStream("Files/process_1.bpmn")));
		BufferedReader bR;
		try {
			bR = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String line;
			
			while((line = bR.readLine()) != null){
//				System.out.println(line);
				xmlRepresentation = xmlRepresentation.concat(line);
				xmlRepresentation = xmlRepresentation.concat("\n");
			}
			bR.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
//		System.out.println(xmlRepresentation);
		
		
		JSONObject xmlJSONObj = XML.toJSONObject(xmlRepresentation);
		
//		System.out.println(xmlJSONObj.toString());
		
		JSONArray parameters = xmlJSONObj.getJSONObject("bpmn2:definitions")
			.getJSONObject("bpmn2:process")
			.getJSONObject("bpmn2:task")
			.getJSONObject("bpmn2:extensionElements")
			.getJSONObject("imixs:taskConfig")
			.getJSONArray("imixs:parameter");
		
//		System.out.println(parameters.toString());
		
		boolean field_personne_contact_bool = false;
		String field_langue_du_mod_le = null;
		String title = null;
		int field_landkarte = 0;
		
		for(int i=0; i<parameters.length(); i++){
			JSONObject parameter = parameters.getJSONObject(i);
			switch (i) {
			case 0:
				field_personne_contact_bool = parameter.getBoolean("value");
				break;
			case 1:
				field_langue_du_mod_le = parameter.getString("value");
				break;
			case 2:
				title = parameter.getString("value");
				break;
			case 3:
				field_landkarte = parameter.getInt("value");
				break;

			default:
				break;
			}
		}
		
		System.out.println(field_personne_contact_bool);
		System.out.println(field_langue_du_mod_le);
		System.out.println(title);
		System.out.println(field_landkarte);
		
		FormFromBPMN form = new FormFromBPMN(field_personne_contact_bool, field_langue_du_mod_le, title, field_landkarte);
		
		return form;
		
	}

}
