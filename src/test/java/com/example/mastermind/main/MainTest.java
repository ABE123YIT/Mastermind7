package com.example.mastermind.main;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Test;

import com.example.mastermind.service.MastermindService;
import com.example.mastermind.utils.Utilitaires;

public class MainTest {
	
	@Test
	public void mainTest() throws IOException {
//		Integer sizeValue = Utilitaires.getSize();
		String result = MastermindService.execute(null);
		assertNotEquals(result, "");
	}
	
	

}
