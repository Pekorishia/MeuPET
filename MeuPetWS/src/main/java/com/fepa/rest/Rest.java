package com.fepa.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fepa.servlet.Coleira;

@Path("/")
public class Rest {
	
	static String id = null;
	public static Coleira coleira = null;
	public static String myIP = "10.9.100.132";
	static int count_init = 0;

	// http://10.9.100.132:8080/MeuPetWS/rest/update
	@GET
	@Path("/update")
	public String update() {
		if(coleira != null) {
			System.out.println("Client Request: "+coleira.toString());
			return coleira.toString();
		}
		System.out.println("Coleira null");
		return "";

	}

//	// http://localhost:8080/MeuPetWS/rest/test2?id_player=1
//	@POST
//	@Path("/test2")
//	public String test2(@QueryParam("id_player") int id_player) {
//		return "test2 = " + id_player;
//
//	}
	
	
	// http://10.9.100.132:8080/MeuPetWS/rest/subscribe?idEntity=c1
	@GET
	@Path("/subscribe")
	public String subscribe(@QueryParam("idEntity") String idEntity) {
		
		if(id == null) {
			try {
				id = sendPost(idEntity);
			} catch (Exception e) {
				e.printStackTrace();
				return "Deu ruim";
			}
		}else {
			System.out.println("not changes");
		}
		return id;
		

		
//		return " "+sendPost(idEntity);
		

		// (curl localhost:1026/v1/subscribeContext -s -S --header 'Content-Type:
		// application/json' \
		// --header 'Accept: application/json' -d @- | python -mjson.tool) <<EOF
//		 { "entities": [ { "isPattern": "false", "id": "c1", "type": "Coleira" } ],
//		 "attributes": [ "Temperatura","Umidade" ], "reference":
//		 "http://192.168.0.102:8080/MeuPetWS/receive",
//		 "duration": "P1M", "notifyConditions": [ { "type": "ONCHANGE" } ],
//		 "throttling":
//		 "PT5S" }
		// EOF

	}

	// HTTP POST request
	private String sendPost(String idEntity) throws IOException, JSONException{

		String url = "http://localhost:1026/v1/subscribeContext";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		String data = "{ \"entities\": [ { \"isPattern\": \"false\", \"id\": \""+idEntity+"\", \"type\": \"Coleira\" } ],\n" + 
				"		 \"attributes\": [ \"Temperatura\",\"Umidade\" ], \"reference\":\n" + 
				"		 \"http://"+myIP+":8080/MeuPetWS/receive\",\n" + 
				"		 \"duration\": \"P1M\", \"notifyConditions\": [ { \"type\": \"ONCHANGE\" } ],\n" + 
				"		 \"throttling\":\n" + 
				"		 \"PT5S\" }";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(data);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'POST' request to URL : " + url);
//		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		if(responseCode==200)
			return getID(response.toString());
		else
			throw new JSONException("Código inválido");

	}
	
	private String getID(String json) throws JSONException{
		JSONObject jsonObj = new JSONObject(json);
		JSONObject subscribeResponse = jsonObj.getJSONObject("subscribeResponse");
		String id = subscribeResponse.getString("subscriptionId");

		return id;
	}

}
