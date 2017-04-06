package ch.hevs.samproc.loadermodule.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import ch.hevs.samproc.entities.FormContract;

public class CreateJsonForConfiguration {

	public static void main(String[] args) {
		
		JSONObject configurationObject = new JSONObject();
		
		String titleValue = "MyProcess";
		String languageValue = "FR";
		String landkarteValue = "10813";
		boolean contactPersonValue = true;
		
		/* Not used for now */
		List<String> keywordValues = new ArrayList<String>();
		keywordValues.add("my keyword");
		
		configurationObject.put(FormContract.FIELD_TITLE, titleValue);
		configurationObject.put(FormContract.FIELD_LANGUAGE, languageValue);
		configurationObject.put(FormContract.FIELD_LANDKARTE, landkarteValue);
		configurationObject.put(FormContract.FIELD_IS_CONTACT_PERSON, contactPersonValue);
		
		try {
			File myFile = new File("Files/Form.txt");
			FileWriter fW = new FileWriter(myFile, false);
			fW.write(configurationObject.toString());
			fW.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
