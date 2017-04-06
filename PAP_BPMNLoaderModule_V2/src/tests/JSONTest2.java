package tests;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONTest2 {

	public JSONTest2() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		JSONObject content = new JSONObject();
		JSONObject info = new JSONObject();
		info.put("file", "Ciao");
		info.put("filename", "Akex");
		info.put("filepath", "Olivieri");
		content.put("file", info);
		
		
//		JSONArray infoArray = new JSONArray();
//		infoArray.put(jsonInfo);
//		infoArray.put( jsonInfo1);
//		infoArray.put( jsonInfo2);
//		
//		JSONObject content = new JSONObject();
//		content.put("file", infoArray);
		
		System.out.println(content.toString());

	}

}
