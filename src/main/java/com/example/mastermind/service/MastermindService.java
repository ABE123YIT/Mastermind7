package com.example.mastermind.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.example.mastermind.utils.Utilitaires;

@Service
public class MastermindService {
	public static Logger logger = Logger.getLogger(MastermindService.class.getName());

	private MastermindService() {
	}

	/**
	 * 
	 * @throws IOException
	 */
	public static void execute() throws IOException {
		logger.log(Level.INFO, null, "DEBUT : " + new Timestamp(new Date().getTime()));
		Integer sizeValue = Utilitaires.getSize();
		if (null != sizeValue) {
			List<Integer> valuesGuess = filterValues(sizeValue);
			String goodVal = "";
			String result = "";
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
			logger.log(Level.INFO, null, "Le code secret est : " + result);
		}
		logger.log(Level.INFO, null, "FIN " + new Timestamp(new Date().getTime()));
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
