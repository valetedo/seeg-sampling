package com.biolab.xtens.contact.bean;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;


@XmlRootElement 
public class PatientBean extends PersonalDataBean {
	
	@Getter @Setter Integer idPrj;						// project ID
	@Getter @Setter String projName;					// project name
	@Getter @Setter Integer idPcoll;					// patient collection ID
	@Getter @Setter String idStatus;					// status
	@Getter @Setter String idAsl;						// ASL ID
	@Getter @Setter String idAreaAsl;					// ASL Area ID
	@Getter @Setter String code;						// patient code
	@Getter @Setter String familyHistoryCardioDisease;	// family history of cardio diesases
	@Getter @Setter String familyHistoryNeuroDisorders; // family history of neurological disorders
	@Getter @Setter String anamnesi;					// anamnesis
	@Getter @Setter Date deletionDate;					// deletion date
	@Getter @Setter Integer idVst;						// ID visit linked to subject
	@Getter @Setter Integer idStd;						// ID study linked to subject
	@Getter @Setter String descrVst;					// descr. visit linked to subject
	@Getter @Setter String descrStd;					// descr. study linked to subject
	@Getter @Setter Integer idDiagnosis;				// diagnosis ID
	@Getter @Setter String nameDiagnosis;				// diagnosis name
	@Getter @Setter List mdataValList;					// list of metadata types (for query purposes)
	@Getter @Setter String mdataQuery;					// a query string composed with metadata	
	@Getter @Setter Date ageDate1;						// age date 1
	@Getter @Setter String ageCondition1;				// age condition 1
	@Getter @Setter Date ageDate2;						// age date 2
	@Getter @Setter String ageCondition2;				// age condition2
}
