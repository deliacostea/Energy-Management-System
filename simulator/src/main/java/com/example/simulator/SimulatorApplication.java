package com.example.simulator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulatorApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(SmartMeterSimulator simulator) {
		return args -> {
			simulator.simulateMeterData();
		};
	}
}
