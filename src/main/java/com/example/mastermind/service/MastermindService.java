package com.example.mastermind.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MastermindService {

	public static final String TEST_URL_SERVER = "http://172.16.37.129/api/test";
	public static final String START_URL_SERVER = "http://172.16.37.129/api/start";
	public static final String TOKEN = "token";
	public static final String NAME = "name";
	public static final String RESULT = "result";
	public static final String GOOD = "good";
	public static final String SIZE = "size";
	public static final String NAME_VALUE = "Mastermind7";
	public static final String TOKEN_VALUE = "tokenmm7";

	public static void execute() throws IOException {
		// creation start
		Map<String, Object> mapReponseStart = sendWithMsgBody(START_URL_SERVER, "POST",
				buildStart(TOKEN_VALUE, NAME_VALUE));
		if (null != mapReponseStart && !mapReponseStart.isEmpty()) {
			System.out.println(new Timestamp(new Date().getTime()));
			Integer sizeValue = (Integer) mapReponseStart.get(SIZE);
			if (null == sizeValue) {
				sizeValue = 5;
			}
			List<Integer> valuesGuess = filterValues(sizeValue);
			String goodVal = "";
			String wordKey = "";
			String[] tab = new String[sizeValue];
			for (Integer k = 0; k < sizeValue; k++) {
				tab[k] = "_";
			}
			while (!goodVal.equalsIgnoreCase(sizeValue.toString())) {
				for (Integer j = 0; j < sizeValue; j++) {
					int i = 0;
					while (!valuesGuess.isEmpty()) {
						tab[j] = valuesGuess.get(i).toString();
						wordKey = castTab(tab);
						// if ("12345".equalsIgnoreCase(wordKey)) {
						// System.out.println(new Timestamp(new Date().getTime()));
						// System.out.println(wordKey);
						// }
						Map<String, Object> mapReponse = sendWithMsgBody(TEST_URL_SERVER, "POST",
								buildInfo(TOKEN_VALUE, wordKey));
						if (null != mapReponse && !mapReponse.isEmpty()) {
							Integer val = (Integer) mapReponse.get(GOOD);
							goodVal = val.toString();
							Integer index = j + 1;
							if (goodVal.equals(index.toString())) {
								valuesGuess.remove(i);
								break;
							}
						}
						i++;
					}

				}
			}
			System.out.println(wordKey);
		}
		System.out.println(new Timestamp(new Date().getTime()));

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

	private static String buildStart(String token, String name) {
		StringBuilder build = new StringBuilder();
		build.append("{\"");
		build.append(TOKEN);
		build.append("\": \"");
		build.append(token).append("\",\"");
		build.append(SIZE);
		build.append("\": \"");
		build.append("5").append("\",\"");
		build.append(NAME).append("\":\"").append(name);
		build.append("\"}");
		return build.toString();
	}

	private static String castTab(String[] tab) {
		String result = "";
		for (String str : tab) {
			result = result + str;
		}
		return result;
	}

	private static List<Integer> filterValues(Integer sizeValue) throws IOException {
		List<Integer> valuesGuess = new ArrayList<>();
		String sMyGuess;
		int[] myGuess = new int[sizeValue];
		for (int i = 0; i < 10; i++) {
			for (Integer k = 0; k < sizeValue; k++) {
				myGuess[k] = i;
			}
			sMyGuess = getString(myGuess);
			Map<String, Object> mapReponse = sendWithMsgBody(TEST_URL_SERVER, "POST", buildInfo(TOKEN_VALUE, sMyGuess));
			if (null != mapReponse && !mapReponse.isEmpty()) {
				Integer good = (Integer) mapReponse.get(GOOD);
				if (good > 0) {
					for (int m = 0; m < good; m++) {
						valuesGuess.add(i);
					}

				}
			}
		}
		return valuesGuess;
	}

	private static String getString(int[] Guess) {
		String res = "";
		for (int i : Guess) {
			res += i;
		}
		return res;
	}

}
