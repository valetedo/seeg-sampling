package com.biolab.xtens.contact.bean;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement
public class DataBean extends Data {
	/* DATA_TYPE parameters */
	@Getter @Setter String idEvtType;			// id event associated to the specified data type
	@Getter @Setter String nameDataType;		// data type name
	@Getter @Setter String descrDataType;		// data type description
	@Getter @Setter String fileUpload;			// are there any files associated to data: YES/NO
	
	/* PERSONAL_DATA parameters */
	@Getter @Setter String patName;				// patient name
	@Getter @Setter String patSurname;			// patient surname
	@Getter @Setter String patIdSex;			// patient sex ID
	@Getter @Setter Date patBirthDate;			// patient birth date
	@Getter @Setter Integer idPatient;			// patient ID
	
	/* SAMPLE parameters */
	@Getter @Setter Integer idSample;		// sample ID
	@Getter @Setter String bitCode;			// sample BIT code
	
	/* SCOLL_LIST search parameter */
	@Getter @Setter List<Integer> idScollList;		// scoll list (for data search)
	
	/* other */
	@Getter @Setter String sortField; 		// sort field
}
