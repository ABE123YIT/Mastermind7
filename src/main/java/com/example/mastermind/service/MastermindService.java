package com.example.mastermind.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MastermindService {


	public static final String URL_SERVER = "http://172.16.37.129/api/test";
	public static final String TOKEN = "token";
	public static final String RESULT = "result";
	public static final String NAME = "name";

	public static void execute() throws IOException {
		String token = "tokenmm7";
		String result = "00000";
		Map<String, Object> mapReponse = sendWithMsgBody(URL_SERVER, "POST", buildInfo(token, result));
		if (null != mapReponse && !mapReponse.isEmpty()) {
			for (String key : mapReponse.keySet()) {
				System.out.println(key + "" + mapReponse.get(key));
			}
		}
	}

	public static Map<String, Object> sendWithMsgBody(String url, String methode, String msgCorps) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(methode);
		if (!"GET".equalsIgnoreCase(methode)) {
			con.setDoOutput(true);
		}
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		OutputStreamWriter out = null;
		if (!"GET".equalsIgnoreCase(methode)) {
			out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			out.write(msgCorps);
			out.flush();
			out.close();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		Map<String, Object> linkedHashMapFromString = getLinkedHashMapFromString(response.toString());
		in.close();
		con.disconnect();
		return linkedHashMapFromString;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getLinkedHashMapFromString(String value) throws JsonParseException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(value, LinkedHashMap.class);
	}

	private static String buildInfo(String token, String result) {
		StringBuilder build = new StringBuilder();
		build.append("{\"");
		build.append(TOKEN);
		build.append("\": \"");
		build.append(token).append("\",\"");
		build.append(RESULT).append("\":\"").append(result);
		build.append("\"}");
		return build.toString();
	}


}
