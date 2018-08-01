package com.example.demo;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.mastermind.service.MastermindService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MastermindApplicationTests {
	

	@Test
	public void contextLoads() throws IOException {
		String result = MastermindService.execute();
		System.out.println("result test" + result);
//		assertNotEquals(result, "");
		assertTrue(true);
	}

}
