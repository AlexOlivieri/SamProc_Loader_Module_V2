package ch.hevs.samproc.GUI.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.tomcat.util.descriptor.web.ServletDef;

public class ChooseFiles {

	public ChooseFiles() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {


		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("BPMN Files", "bpmn");
		chooser.setFileFilter(filter);
		
		chooser.setMultiSelectionEnabled(true);
		
		
	
		
//		chooser.setFileFilter(new FileFilter() {
//			
//			@Override
//			public String getDescription() {
//				
//				return "Git Image";
//			}
//			
//			@Override
//			public boolean accept(File f) {
//				
//				return f.getName().toLowerCase().endsWith(".bpmn");
//			}
//		});
//		
		List<String[]> files = new ArrayList<String[]>();
		
		JFrame frame = new JFrame("File chooser");
		
		int r = chooser.showSaveDialog(frame);
		if(r == JFileChooser.APPROVE_OPTION){
			File[] selectedFiles = chooser.getSelectedFiles();
			String[] fileAndPath = new String[2];
			
			for(int i=0; i<selectedFiles.length; i++){
				fileAndPath[0] = selectedFiles[i].getName();
				fileAndPath[1] = selectedFiles[i].getParent();
				int index = selectedFiles[i].getParent().lastIndexOf("\\");
				System.out.println(index);
//				files.add(selectedFiles[i].getParent().substring(index+1));
				files.add(fileAndPath);
			}
			
			for(int i=0; i<files.size(); i++){
				System.out.println(files.get(i));
			}
		}
		

		frame.dispose();
		String[] file = files.get(0);
		
		try {
			FileInputStream fis = new FileInputStream(file[1].concat("\\").concat(file[0]));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(file[1].concat("\\").concat(file[0]));
	}
}
