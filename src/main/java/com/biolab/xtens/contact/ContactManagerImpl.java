package com.biolab.xtens.contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.biolab.xtens.contact.bean.PatientBean;
import com.google.gson.*;
import com.mysql.jdbc.ResultSetMetaData;

import java.sql.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ContactManagerImpl implements ContactManager{

	String emptySchema;
	private List<JsonObject> schemaList;
	private static String uname = "admin";
	private static String pw = "admin";
	private static String addressCreatePatient = "http://130.251.10.60:8080/xtens/rest/patient/json/create";
	private static String addressCreateData = "http://130.251.10.60:8080/xtens/rest/data/json/create";
	private static int idPatient;


	ContactManagerImpl() {
		schemaList = new ArrayList<JsonObject>();
	}

	@Override
	public void getSchemaFromFile(String filePath)  { // this method gets the json schema from a file
		try{

			FileReader reader = new FileReader(filePath);
			JsonParser parser = new JsonParser();		
			JsonElement parse =parser.parse(reader);
			emptySchema = parse.toString();
		} catch (FileNotFoundException ex) {

			ex.printStackTrace();

		} catch (NullPointerException ex) {

			ex.printStackTrace();

		}
	}

	public void getSchemaFromDatabase(String address) // this method gets the json schema directly from the database
	{

		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(address);
			Statement stmt = conn.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("SELECT `JSON_SCHEMA` FROM `DATA_TYPE` WHERE `NAME_DATA_TYPE` = 'CONTACT'");
			ResultSetMetaData rsmd=(ResultSetMetaData) rs.getMetaData();
			int i=rsmd.getColumnCount();
			while(rs.next()){
				for(int j=1;j<=i;j++){
					emptySchema= rs.getString(j);
				}
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	}



	public void getSchemaFromWebService(String address)
	{
		try {
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(uname,pw)); // utente e password
			WebResource webResource = client.resource(address);
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();

			try {
				ClientResponse response = webResource.queryParams(queryParams).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}
				emptySchema = response.getEntity(String.class);
			}
			catch (Exception e){
				e.printStackTrace();
			}

		}

		catch(Exception e){
			e.printStackTrace();
		}
	}

	
		
	public void populateSchema(ContactParam contactParam)  {
		// this method inserts the data in the json schema. It took these data from the input file(in the Contact class) 


		JsonParser parser = new JsonParser();
		JsonObject jsonObject = (JsonObject) parser.parse(emptySchema);
		JsonArray body = jsonObject.get("body").getAsJsonArray();
		int len = body.size();
		for (int i=0; i < len; i++) {
			JsonObject groupObj = body.get(i).getAsJsonObject();
			JsonArray content = groupObj.get("content").getAsJsonArray();
			for (int j=0, llen = content.size(); j<llen; j++) {
				JsonObject attr = content.get(j).getAsJsonObject();
				if (attr.get("label").getAsString().equals("attribute"))
				{
					if (attr.get("name").getAsString().equals("LABEL"))
					{
						JsonObject value = new JsonObject();
						value.addProperty("value",contactParam.getLabel());
						value.addProperty("unit", (String)null);
						attr.get("instances").getAsJsonArray().add(value);


					}
					else if (attr.get("name").getAsString().equals("ANATOMICAL AREA"))
					{
						JsonObject value = new JsonObject();
						value.addProperty("value",contactParam.getAnatomical_area());
						value.addProperty("unit", (String)null);
						attr.get("instances").getAsJsonArray().add(value);

					}
					else if (attr.get("name").getAsString().equals("EPILEPTIC CONTACT"))
					{
						JsonObject value = new JsonObject();
						value.addProperty("value",contactParam.getEpileptic_contact());
						value.addProperty("unit", (String)null);
						attr.get("instances").getAsJsonArray().add(value);

					}else if (attr.get("name").getAsString().equals("BAD CONTACT"))
					{
						JsonObject value = new JsonObject();
						value.addProperty("value",contactParam.getBad_contact());
						value.addProperty("unit", (String)null);
						attr.get("instances").getAsJsonArray().add(value);

					}
					else if (attr.get("name").getAsString().equals("TP DIST"))
					{
						JsonObject value = new JsonObject();
						value.addProperty("value",Float.toString(contactParam.getTp_dist()));
						value.addProperty("unit", "mm");
						attr.get("instances").getAsJsonArray().add(value);

					}
					else if (attr.get("name").getAsString().equals("X"))
					{
						JsonObject value = new JsonObject();
						value.addProperty("value",Float.toString(contactParam.getX()));
						value.addProperty("unit", "mm");
						attr.get("instances").getAsJsonArray().add(value);


					}
					else if (attr.get("name").getAsString().equals("Y"))
					{
						JsonObject value = new JsonObject();
						value.addProperty("value",Float.toString(contactParam.getY()));
						value.addProperty("unit","mm");
						attr.get("instances").getAsJsonArray().add(value);

					}
					else if (attr.get("name").getAsString().equals("Z"))
					{
						JsonObject value = new JsonObject();
						value.addProperty("value",Float.toString(contactParam.getZ()));
						value.addProperty("unit", "mm");
						attr.get("instances").getAsJsonArray().add(value);

					}


				}

			}


		}
		schemaList.add(jsonObject);
	}
	@Override
	public void saveData()  { // this method saves the jsonObjects of the schemaList in a new file (each jsonObject in a new file) 
		int count = 0;
		try {
			Writer writer;
			for (JsonObject jsonObject : schemaList)
			{
				writer = new FileWriter("output"+ count +".json");
				writer.write(jsonObject.toString());
				writer.close();
				count = count +1;
			}

		} catch (NullPointerException ex) {

			ex.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public Integer createPatient(String name,String surname,String birthdate,String sex)
	{
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(uname, pw));
		WebResource webResource = client.resource(addressCreatePatient);
		try {
			PatientBean patient = new PatientBean();
			patient.setName(name);
			patient.setSurname(surname);
			patient.setBirthDate(Date.valueOf(birthdate));
			patient.setIdSex(sex);
			String json = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create().toJson(patient);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(response.getEntity(String.class));
			String id = jsonObject.get("idPrsData").getAsString();
			System.out.println(id);
		    idPatient = Integer.parseInt(id);
			return idPatient;
			
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ClientResponse createDataContact(int idSample,int idDataType,String idPrcType,String date)
	{
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(uname, pw));
		WebResource webResource = client.resource(addressCreateData);
		
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
			
			queryParams.add("idPatient", Integer.toString(idPatient));
			queryParams.add("idSample", Integer.toString(idSample));
			queryParams.add("idDataType", Integer.toString(idDataType));
			queryParams.add("idPrcType", idPrcType);
			queryParams.add("acquisitionDate", date);

			ClientResponse response = new ClientResponse(0, null, null, null);
			for (JsonObject jsonObject : schemaList) {
				response = webResource.queryParams(queryParams).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonObject.toString());
			}
			schemaList.clear();
			return response;		
			
	} 


}
