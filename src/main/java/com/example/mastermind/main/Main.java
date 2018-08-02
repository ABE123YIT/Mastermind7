package com.example.mastermind.main;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.mastermind.service.MastermindService;
import com.example.mastermind.utils.Utilitaires;

public class Main {
	
	public static final Logger logger = Logger.getLogger(MastermindService.class.getName());

	public static void main(String[] args) throws IOException{
		logger.log(Level.INFO, null, "DEBUT : " + new Timestamp(new Date().getTime()));
		Integer sizeValue = Utilitaires.getSize();
		if(null != sizeValue) {
			logger.log(Level.INFO, null, "Le code secret est : " + MastermindService.execute(sizeValue));
		}
		logger.log(Level.INFO, null, "FIN " + new Timestamp(new Date().getTime()));
	}

}
