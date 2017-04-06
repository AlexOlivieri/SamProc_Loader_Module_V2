package ch.hevs.samproc.entities;

public class Form {

	private String titleValue;
	private String languageValue;
	private String landkarteValue;
	private boolean contactPersonValue;
	
	public Form(String titleValue, String languageValue, String landkarteValue, boolean contactPersonValue) {
		this.titleValue = titleValue;
		this.languageValue = languageValue;
		this.landkarteValue = landkarteValue;
		this.contactPersonValue = contactPersonValue;
	}
	
	public String getTitle(){
		return this.titleValue;
	}
	
	public String getLanguage(){
		return this.languageValue;
	}
	
	public String getLandkarte() {
		return this.landkarteValue;
	}
	
	public boolean getIsContactPerson(){
		return this.contactPersonValue;
	}

}
