package ch.hevs.samproc.bpmnparser.test;

public class FormFromBPMN {

	private boolean field_personne_contact_bool;
	private String field_langue_du_mod_le;
	private String title;
	private int field_landkarte;
	
	public FormFromBPMN(boolean field_personne_contact_bool, String field_langue_du_mod_le, String title, int field_landkarte) {

		this.field_personne_contact_bool = field_personne_contact_bool;
		this.field_langue_du_mod_le = field_langue_du_mod_le;
		this.title = title;
		this.field_landkarte = field_landkarte;
	}
	
	public boolean getField_personne_contact_bool(){
		return this.field_personne_contact_bool;
	}
	
	public String getField_langue_du_mod_le(){
		return this.field_langue_du_mod_le;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public int getField_landkarte(){
		return this.field_landkarte;
	}
}
