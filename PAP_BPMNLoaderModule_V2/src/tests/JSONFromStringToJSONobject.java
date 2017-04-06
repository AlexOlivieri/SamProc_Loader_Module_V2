package tests;

import org.json.JSONArray;
import org.json.JSONObject;

import ch.hevs.samproc.entities.Form;

public class JSONFromStringToJSONobject {

	public JSONFromStringToJSONobject() {
		// TODO Auto-generated constructor stub
	}

	public static JSONObject getProcessContent(String fid, Form form) {
		
		JSONObject content = new JSONObject();
//		content.put("title", "title");
		content.put("title", form.getTitle());
		content.put("type", "process");
		JSONObject langue = new JSONObject();
		JSONObject und1 = new JSONObject();
//		und1.put("value", "EN");
		und1.put("value", form.getLanguage());
		langue.put("und", und1);
		content.put("field_langue_du_mod_le", langue);
		JSONObject motivation = new JSONObject();
		JSONObject und2 = new JSONObject();
		und2.put("value", "cycle_time");
		motivation.put("und", und2);
		content.put("field_motivation", motivation);
		
		JSONObject landkarte = new JSONObject();
		JSONArray und3 = new JSONArray();
		JSONObject value1 = new JSONObject();
//		value1.put("tid", "10813");
		value1.put("tid", form.getLandkarte());
		und3.put(value1);
		landkarte.put("und", und3);
		content.put("field_landkarte", landkarte);
		
		JSONObject contactPerson = new JSONObject();
		JSONArray und4 = new JSONArray();
		JSONObject value2 = new JSONObject();
//		value2.put("value", "1");
		String isContactPerson = null;
		if(form.getIsContactPerson()){
			isContactPerson = "1";
		}else{
			isContactPerson = "0";
		}
		value2.put("value", isContactPerson);
		und4.put(value2);
		contactPerson.put("und", und4);
		content.put("field_personne_contact_bool", contactPerson);
		
		JSONObject isBPMN = new JSONObject();
		JSONArray und5 = new JSONArray();
		JSONObject value3 = new JSONObject();
		value3.put("value", "0");
		und5.put(value3);
		isBPMN.put("und", und5);
		content.put("field_not_bpmn2", isBPMN);
		
		JSONObject file = new JSONObject();
		JSONArray und6 = new JSONArray();
		JSONObject value4 = new JSONObject();
		value4.put("fid", fid);
		und6.put(value4);
		file.put("und", und6);
		content.put("field_file", file);
		
		System.out.println(content.toString());
		
		return content;
	
	}

}
