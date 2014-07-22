package com.biolab.xtens.contact.bean;



import lombok.Getter;
import lombok.Setter;


public class PersonalDataBean extends PersonalData {
	
	@Getter @Setter String sex;
	@Getter @Setter String birMunicip;
	@Getter @Setter String resMunicip;
	@Getter @Setter String domMunicip;
	@Getter @Setter String birSubarea;
	@Getter @Setter String resSubarea;
	@Getter @Setter String domSubarea;
	@Getter @Setter String birAbbrev;
	@Getter @Setter String resAbbrev;
	@Getter @Setter String domAbbrev;
	@Getter @Setter String nationality;
	@Getter @Setter String citizenship;
	@Getter @Setter String maritalStatus;
	@Getter @Setter String familyDoctorName;
	@Getter @Setter String familyDoctorSurname;
	/*
	@Getter @Setter String extContact;
	@Getter @Setter String extTelPref;
	@Getter @Setter String extTelNum;
	@Getter @Setter String extAddress;
	 */
	@Getter @Setter String extNotes;
	
}

