package com.example.mastermind.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utilitaires {

	public static final String[] CHARS_AUTHORIZED = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C",
			"D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z" };
//	public static final String[] CHARS_AUTHORIZED = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	public static final String URL_TEST = "http://172.16.37.129/api/test";
	public static final String URL_START = "http://172.16.37.129/api/start";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_GET = "GET";
	public static final String TOKEN = "token";
	public static final String NAME = "name";
	public static final String RESULT = "result";
	public static final String GOOD = "good";
	public static final String SIZE = "size";
	public static final String ACC_OPEN = "{\"";
	public static final String ACC_CLOSE = "\"}";
	public static final String CONST1 = "\",\"";
	public static final String CONST2 = "\":\"";
	public static final String UTF8 = "UTF-8";
	public static final String APP_JSON = "application/json";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ACCEPT = "Accept";
	public static final String NAME_VALUE = "Mastermind7";
	public static final String TOKEN_VALUE = "tokenmm7";

	private Utilitaires() {
	}

	/**
	 * send to api
	 * 
	 * @param url
	 * @param mtd
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> send(String url, String mtd, String msg) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(mtd);
		if (!METHOD_GET.equalsIgnoreCase(mtd)) {
			con.setDoOutput(true);
		}
		con.setRequestProperty(CONTENT_TYPE, APP_JSON);
		con.setRequestProperty(ACCEPT, APP_JSON);

		OutputStreamWriter out = null;
		if (!METHOD_GET.equalsIgnoreCase(mtd)) {
			out = new OutputStreamWriter(con.getOutputStream(), UTF8);
			out.write(msg);
			out.flush();
			out.close();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), UTF8));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		Map<String, Object> linkedHashMapFromString = getMap(response.toString());
		in.close();
		con.disconnect();
		return linkedHashMapFromString;
	}

	/**
	 * get map
	 * 
	 * @param value
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String value) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(value, LinkedHashMap.class);
	}

	/**
	 * build a json message
	 * 
	 * @param token
	 * @param result
	 * @param propName
	 * @return
	 */
	public static String buildMsg(String token, String result, String propName) {
		StringBuilder build = new StringBuilder();
		build.append(ACC_OPEN);
		build.append(TOKEN);
		build.append(CONST2);
		build.append(token).append(CONST1);
		build.append(propName).append(CONST2).append(result);
		build.append(ACC_CLOSE);
		return build.toString();
	}

	/**
	 * convert tab to string
	 * 
	 * @param tab
	 * @return
	 */
	public static String tabToString(String[] tab) {
		StringBuilder result = new StringBuilder();
		for (String str : tab) {
			result.append(str);
		}
		return result.toString();
	}

	/**
	 * convert tab of int to string
	 * 
	 * @param tab
	 * @return
	 */
	public static String getString(int[] tab) {
		StringBuilder result = new StringBuilder();
		for (int i : tab) {
			result.append(i);
		}
		return result.toString();
	}

	/**
	 * set a tab
	 * 
	 * @param sizeValue
	 * @return
	 */
	public static String[] setTab(Integer sizeValue) {
		String[] tab = new String[sizeValue];
		for (Integer k = 0; k < sizeValue; k++) {
			tab[k] = "_";
		}
		return tab;
	}

	/**
	 * adding a specified number of specified caracter to a specified string
	 * 
	 * @param str
	 * @param nbr
	 * @param crt
	 * @return
	 */
	public static String padding(String str, int nbr, String crt) {
		StringBuilder sb = new StringBuilder(str);
		int sbLength = sb.length();
		if (nbr > 0 && nbr > sbLength) {
			for (int i = 0; i < (nbr - sbLength); i++) {
				sb.append(crt);
			}
		}
		return sb.toString();
	}

	/**
	 * get good values of a code
	 * 
	 * @param result
	 * @return
	 * @throws IOException
	 */
	public static String getTestResult(String result) throws IOException {
		Map<String, Object> mapReponse = send(URL_TEST, METHOD_POST, buildMsg(TOKEN_VALUE, result, RESULT));
		Integer val = (null != mapReponse && null != mapReponse.get(GOOD)) ? (Integer) mapReponse.get(GOOD) : 0;
		return val.toString();
	}

	/**
	 * get the size of the secret code
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Integer getSize() throws IOException {
		Map<String, Object> mapReponseStart = send(URL_START, METHOD_POST, buildMsg(TOKEN_VALUE, NAME_VALUE, NAME));
		if (null != mapReponseStart.get(SIZE)) {
			return (Integer) mapReponseStart.get(Utilitaires.SIZE);
		}
		return null;
	}

}
