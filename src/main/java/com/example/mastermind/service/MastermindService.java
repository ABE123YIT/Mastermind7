package com.example.mastermind.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.example.mastermind.utils.Utilitaires;
import org.springframework.stereotype.Service;



@Service
public class MastermindService {
	public static final Logger logger = Logger.getLogger(MastermindService.class.getName());

	private MastermindService() {
	}

	/**
	 * 
	 * @throws IOException
	 */
	public static String execute(Integer sizeValue) throws IOException {
		String result = "";
		List<String> valuesGuess = filterValues(sizeValue);
		String goodVal = "";
		String[] tab = Utilitaires.setTab(sizeValue);
		for (Integer j = 0; j < sizeValue; j++) {
			int i = 0;
			while (!valuesGuess.isEmpty()) {
				tab[j] = valuesGuess.get(i);
				result = Utilitaires.tabToString(tab);
				goodVal = Utilitaires.getTestResult(result);
				Integer index = j + 1;
				if (goodVal.equals(index.toString())) {
					valuesGuess.remove(i);
					break;
				}
				i++;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param sizeValue
	 * @return
	 * @throws IOException
	 */
	private static List<String> filterValues(Integer sizeValue) throws IOException {
		List<String> valuesGuess = new ArrayList<>();
		String chaine = "";
		for (String crt : Utilitaires.CHARS_AUTHORIZED) {
			chaine = Utilitaires.padding("", sizeValue.intValue(), crt);
			Integer good = Integer.parseInt(Utilitaires.getTestResult(chaine));
			if (good > 0) {
				for (int m = 0; m < good; m++) {
					valuesGuess.add(crt);
				}
			}
			if (sizeValue.equals(valuesGuess.size())) {
				break;
			}
		}
		return valuesGuess;
	}

}
