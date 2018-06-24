package br.ufrn.aircontrol.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class AirControlServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//Do nothing
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String json = getBody(req);

//		AirConditioner airC;
		try {
			@SuppressWarnings("unused")
			AirConditioner airC = getContextEntity(json);
			
			System.out.println(json);
			
		} catch (Exception  e) {
			e.printStackTrace();
		}

	}
	
	private AirConditioner getContextEntity(String json) throws JSONException{
		AirConditioner airC = new AirConditioner();

		JSONObject jsonObj = new JSONObject(json);

		JSONArray contextResponses = jsonObj
				.getJSONArray("contextResponses");
		

		JSONObject contextElement = contextResponses.getJSONObject(0)
				.getJSONObject("contextElement");
		
		airC.setId(contextElement.getString("id"));

		JSONArray attributes = contextElement.getJSONArray("attributes");

		for (int i = 0; i < attributes.length(); i++) {

			JSONObject att = attributes.getJSONObject(i);
			
			if (att.getString("name").equalsIgnoreCase("Status")){
				airC.setOn(Boolean.parseBoolean(att.getString("value")));
			}else if (att.getString("name").equalsIgnoreCase("Temperatura")){
				airC.setTemperatura(Integer.parseInt(att.getString("value")));
			}
		}

		return airC;
	}

	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}

}
