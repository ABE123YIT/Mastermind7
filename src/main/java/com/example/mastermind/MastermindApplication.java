package com.example.mastermind;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.mastermind.main.Main;

@SpringBootApplication
public class MastermindApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MastermindApplication.class, args);
		Main.main(args);
	}
}
