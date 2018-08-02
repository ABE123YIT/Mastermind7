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
		List<Integer> valuesGuess = filterValues(sizeValue);
		String goodVal = "";
		String[] tab = Utilitaires.setTab(sizeValue);
		for (Integer j = 0; j < sizeValue; j++) {
			int i = 0;
			while (!valuesGuess.isEmpty()) {
				tab[j] = valuesGuess.get(i).toString();
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
	private static List<Integer> filterValues(Integer sizeValue) throws IOException {
		List<Integer> valuesGuess = new ArrayList<>();
		String chaine = "";
		for (int i = 0; i < 10; i++) {
			chaine = Utilitaires.padding("", sizeValue.intValue(), i);
			Integer good = Integer.parseInt(Utilitaires.getTestResult(chaine));
			if (good > 0) {
				for (int m = 0; m < good; m++) {
					valuesGuess.add(i);
				}
			}
			if (sizeValue.equals(valuesGuess.size())) {
				break;
			}
		}
		return valuesGuess;
	}

}
