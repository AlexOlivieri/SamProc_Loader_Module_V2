package ch.hevs.samproc.loadermodule.tests.json;

import org.json.JSONObject;

public class UnitTests {

	public static void main(String[] args) {

		String username = "alexolivieri";
		String password = "papBILANCIA81";
		
		JSONObject loginObject = JsonObjectsCreator.createLoginObject(username, password);
		System.out.println(loginObject.toString());

	}

}
