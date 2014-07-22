package com.biolab.xtens.contact;

import com.sun.jersey.api.client.ClientResponse;

public interface ContactManager {
	void getSchemaFromFile(String filePath);
	void getSchemaFromDatabase(String address);
	void getSchemaFromWebService(String address);
	void populateSchema(ContactParam contactparam);
	void saveData();
	Integer createPatient(String name,String surname,String birthdate,String sex);
	ClientResponse createDataContact(int idSample,int idDataType,String idPrcType,String date);
	
}
