package com.biolab.xtens.contact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.spi.DirStateFactory.Result;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.biolab.xtens.contact.bean.DataBean;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Inspector {
	
	//private String uname = "admin";
	//private String pw = "admin";
	//private String address = "http://10.186.10.60:8080/xtens/rest/data/json/getMDataList";
	//private String address = "http://10.186.10.60:8080/xtens/rest/data/json/query";

	
	
	
	
	
	List<Map<String,Integer>> getAnatomicalAreaStatistics(List<String> list, String uname, String pw, String address) throws FileNotFoundException
	{
		Map<String,Integer> result_dx = new HashMap<String, Integer>();
		Map<String,Integer> result_sx = new HashMap<String, Integer>();
		List<Map<String,Integer>> result = new ArrayList<Map<String,Integer>>();
		//int count=0;

		try {
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(uname,pw)); // utente e password
			WebResource webResource = client.resource(address+"/xtens/rest/data/json/getMDataList");
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
			MultivaluedMap<String, String> queryParams_data = new MultivaluedMapImpl();


			try {
				int count = 0;
				for (String obj : list)
				{
					int id_sx = 0;
					int id_dx = 0;
					long lStartTime = new Date().getTime();
					DataBean dataBean = new DataBean();
					//String obj = "S_subparietal";
					queryParams.add("idMdataField", Integer.toString(5));
					queryParams.add("mdataValue",obj);
					ClientResponse response = webResource.queryParams(queryParams).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
					if (response.getStatus() != 200) {
						throw new RuntimeException("Failed : HTTP error code : "
								+ response.getStatus());
					}
					JsonParser parser = new JsonParser();
					JsonArray jsonArray = (JsonArray) parser.parse(response.getEntity(String.class));
					System.out.println(jsonArray);
					int id = jsonArray.size();
					if (id!=0){
						for (JsonElement jsonElement : jsonArray)
						{
							String idData = ((JsonObject) jsonElement).get("idData").getAsString();
						
						
							queryParams_data.add("idData",idData);
							
							ClientResponse response2 = webResource.queryParams(queryParams_data).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
							if (response2.getStatus() != 200) {
								throw new RuntimeException("Failed : HTTP error code : "
										+ response2.getStatus());
							}
							JsonParser parser_data = new JsonParser();
							JsonArray jsonArray_data = (JsonArray) parser_data.parse(response2.getEntity(String.class));
							for(JsonElement jsonElement_data : jsonArray_data)
							{
								if (((JsonObject)jsonElement_data).get("fieldName").getAsString().equals("LABEL")){
									String label = ((JsonObject) jsonElement_data).get("fieldValue").getAsString();
									if (label.contains("'"))
									{
										id_sx = id_sx + 1;
									}
									else{
										id_dx = id_dx + 1;
									}
								}
							}

							//System.out.println(jsonArray_data);
							queryParams_data.clear();

						}
						result_dx.put(obj,id_dx);
						result_sx.put(obj,id_sx);

					}
					else
					{
						result_dx.put(obj, 0);
						result_sx.put(obj, 0);
					}


					//count = count +id;
					queryParams.clear();
					long lEndTime = new Date().getTime();
					long difference = lEndTime - lStartTime;
					System.out.println("Elapsed milliseconds: " + difference + "id" + count++);


				}
			}	catch (Exception e){
				e.printStackTrace();
			}

		}

		catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println(count);
		FileOutputStream area_sx = new FileOutputStream("Area_sx.txt");
		PrintStream writer_sx = new PrintStream(area_sx);
		for (Map.Entry<String, Integer> entry_sx : result_sx.entrySet()) {

			writer_sx.print(entry_sx.getKey() + " ");
			writer_sx.println(entry_sx.getValue());
		}

		FileOutputStream area_dx = new FileOutputStream("Area_dx.txt");
		PrintStream writer_dx = new PrintStream(area_dx);
		for (Map.Entry<String, Integer> entry_dx : result_dx.entrySet()) {

			writer_dx.print(entry_dx.getKey() + " ");
			writer_dx.println(entry_dx.getValue());
		}
		result.add(result_sx);
		result.add(result_dx);
		return result;

	}
	
	public void histogram(List<Map<String,Integer>> map) throws IOException{
		
		int b = 0; 
		for (Map<String,Integer> obj : map)
		 {
		 int a = 0;
		 DefaultCategoryDataset cdata = new DefaultCategoryDataset();
		 for (Map.Entry<String, Integer> entry : obj.entrySet())
		 {
			 if ((a = entry.getValue())!= 0) 
	      cdata.setValue(a, entry.getKey() + " = " + a, entry.getKey());
		 }
		 JFreeChart chart = ChartFactory.createBarChart3D("histogram","anatomical_area" ,"value" , cdata, PlotOrientation.VERTICAL, false, false, false);
	
		 final CategoryPlot plot = chart.getCategoryPlot();
		 final CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(
	                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
	            );
	    ChartUtilities.saveChartAsPNG(new File("histogramtot"+b+".png"), chart, 3000, 1000);
	    b = b+1;
	}
	}
	
	
	
}



