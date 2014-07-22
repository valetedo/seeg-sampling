package com.biolab.xtens.contact;

//import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//import java.io.IOException;





public class Contact {
	public static void main(String args[]) throws IOException
	{  
		//String addressDatabase = "jdbc:mysql://130.251.10.60:3306/xtensNiguarda?user=root&password=";
		//String jsonfile = "/home/valentina/file.json";
		/*long lStartTime = new Date().getTime();
		File file = new File("pazienti");
		File[] filesInDir = file.listFiles();
		Arrays.sort(filesInDir);
		String[] nomifile = null;
		String addressWeb = "http://130.251.10.60:8080/xtens/rest/data/json/getDatatypeSchema/4";
		ContactManager contact = new ContactManagerImpl();
		*/
		//contact.getSchemaFromDatabase(addressDatabase);
		//contact.getSchemaFromFile(jsonfile);
		//contact.getSchemaFromWebService(addressWeb);
		List<Map<String,Integer>> map = new ArrayList<Map<String,Integer>>();
		Inspector inspector = new Inspector();
		BufferedReader reader =new BufferedReader(new FileReader(args[0]));
		String line;
		List<String> list = new ArrayList<String>();
		 

		/*for(File f : filesInDir)
		{
			nomifile = (f.toString()).split("/");

			String[] patient = nomifile[1].split("_"); 

			contact.createPatient(patient[0], patient[1], patient[2], patient[3]);

			String s;

			BufferedReader reader2 =new BufferedReader(new FileReader(f) );

			//ContactParam contactParam = new ContactParam();
			//ContactParseImplA formatA = new ContactParseImplA();
			ContactParser formatB = new ContactParserImplB();
			//formatA.parse(s=reader.readLine());

			while((s=reader2.readLine())!=null)
			{
				contact.populateSchema(formatB.parse(s));
			}
			reader2.close();
			//contact.saveData();
			contact.createDataContact(0,4,"STD004","2014-04-29");
		}
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Elapsed milliseconds file: " + difference);*/

		Properties prop = new Properties();
		InputStream inputStream = Contact.class.getClassLoader().getResourceAsStream("config.properties");
		prop.load(inputStream);
		
		while ((line = reader.readLine())!=null)
		{
			list.add(line);
		}
		map = inspector.getAnatomicalAreaStatistics(list,prop.getProperty("user"),prop.getProperty("password"),prop.getProperty("address"));
		inspector.histogram(map);
		
	}
}
