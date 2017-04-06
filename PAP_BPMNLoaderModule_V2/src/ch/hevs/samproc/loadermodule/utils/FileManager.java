package ch.hevs.samproc.loadermodule.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class FileManager {

	public FileManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		byte[] data = byteArrayFromFile("Files/file1.bpmn");
	}
	
	public static byte[] byteArrayFromFile(String file){
		Path path = Paths.get(file);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
			String authFileEnc = new String(Base64.getEncoder().encode(data));
			System.out.println(authFileEnc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
