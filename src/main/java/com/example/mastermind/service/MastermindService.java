package com.example.mastermind.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MastermindService {

	public static final String URL_SERVER = "http://172.16.37.129/api/test";
	public static final String TOKEN = "token";
	public static final String RESULT = "result";
	public static final String NAME = "name";
	public static final String GOOD = "good";
	public static final String SUCCESS_VALUE = "5";

	public static void execute() throws IOException {
		String token = "tokenmm7";
		String goodVal = "";
		String crt1 = "_";
		String crt2 = "_";
		String crt3 = "_";
		String crt4 = "_";
		String crt5 = "_";
		String rsl1 = "";
		String rsl2 = "";
		String rsl3 = "";
		String rsl4 = "";
		String wordKey = "";
		System.out.println(new Timestamp(new Date().getTime()));
		while (!goodVal.equalsIgnoreCase(SUCCESS_VALUE)) {
			// boucle char 1
			for (Integer i = 0; i < 10; i++) {
				crt1 = i.toString();
				wordKey = crt1 + crt2 + crt3 + crt4 + crt5;
				Map<String, Object> mapReponse = sendWithMsgBody(URL_SERVER, "POST", buildInfo(token, wordKey));
				if (null != mapReponse && !mapReponse.isEmpty()) {
					Integer val = (Integer) mapReponse.get(GOOD);
					goodVal = val.toString();
					if (goodVal.equals("1")) {
						rsl1 = crt1;
						break;
					}
				}
			}
			// boucle char 2
			for (Integer i = 0; i < 10; i++) {
				crt2 = i.toString();
				wordKey = rsl1 + crt2 + crt3 + crt4 + crt5;
				Map<String, Object> mapReponse = sendWithMsgBody(URL_SERVER, "POST", buildInfo(token, wordKey));
				if (null != mapReponse && !mapReponse.isEmpty()) {
					Integer val = (Integer) mapReponse.get(GOOD);
					goodVal = val.toString();
					if (goodVal.equals("2")) {
						rsl2 = crt2;
						break;
					}
				}
			}
			// boucle char 3
			for (Integer i = 0; i < 10; i++) {
				crt3 = i.toString();
				wordKey = rsl1 + rsl2 + crt3 + crt4 + crt5;
				Map<String, Object> mapReponse = sendWithMsgBody(URL_SERVER, "POST", buildInfo(token, wordKey));
				if (null != mapReponse && !mapReponse.isEmpty()) {
					Integer val = (Integer) mapReponse.get(GOOD);
					goodVal = val.toString();
					if (goodVal.equals("3")) {
						rsl3 = crt3;
						break;
					}
				}
			}
			// boucle char 4
			for (Integer i = 0; i < 10; i++) {
				crt4 = i.toString();
				wordKey = rsl1 + rsl2 + rsl3 + crt4 + crt5;
				Map<String, Object> mapReponse = sendWithMsgBody(URL_SERVER, "POST", buildInfo(token, wordKey));
				if (null != mapReponse && !mapReponse.isEmpty()) {
					Integer val = (Integer) mapReponse.get(GOOD);
					goodVal = val.toString();
					if (goodVal.equals("4")) {
						rsl4 = crt4;
						break;
					}
				}
			}
			// boucle char 5
			for (Integer i = 0; i < 10; i++) {
				crt5 = i.toString();
				wordKey = rsl1 + rsl2 + rsl3 + rsl4 + crt5;
				Map<String, Object> mapReponse = sendWithMsgBody(URL_SERVER, "POST", buildInfo(token, wordKey));
				if (null != mapReponse && !mapReponse.isEmpty()) {
					Integer val = (Integer) mapReponse.get(GOOD);
					goodVal = val.toString();
					if (goodVal.equals(SUCCESS_VALUE)) {
						break;
					}
				}
			}

		}
		System.out.println(new Timestamp(new Date().getTime()));
		System.out.println(wordKey);

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
