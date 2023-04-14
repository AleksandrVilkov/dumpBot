package com.dumpBot;

import com.dumpBot.storage.entity.Car;
import com.dumpBot.storage.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class DumpBotApplicationTests {
	@Autowired
	CarRepository carRepository;
	@Test
	@Transactional
	void contextLoads() {
		Optional<Car> employeesOptional = carRepository.findById(1);
		System.out.println(employeesOptional);
	}

}
