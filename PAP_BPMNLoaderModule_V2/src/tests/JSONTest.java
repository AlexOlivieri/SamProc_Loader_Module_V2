package tests;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONTest {

	public JSONTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		JSONObject jsonInfo = new JSONObject();
		jsonInfo.put("file", "Ciao");
		jsonInfo.put("filename", "Akex");
		jsonInfo.put("filepath", "Olivieri");
		
		
//		JSONArray infoArray = new JSONArray();
//		infoArray.put(jsonFile);
//		infoArray.put( jsonFile);
//		infoArray.put( jsonFilename);
		
		JSONObject content = new JSONObject();
		content.put("file", jsonInfo);
		
		System.out.println(content.toString());

	}

}
