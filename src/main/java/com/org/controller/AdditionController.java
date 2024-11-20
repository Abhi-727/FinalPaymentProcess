package com.org.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AdditionController {

	@PostMapping("/test")
	public ResponseEntity<?> TestingLogs(@RequestBody String name) {
		
		
		String testlog = "Testing logs";
		System.out.println("testlogs : " + testlog);
		log.info("testlogs : " + testlog);
		
		int payment =125000;
		int pay = 1000000;
		
		int totalpayment =payment+pay;
		System.out.println("total payment : " + totalpayment);
		
		log.info("totalpayment : " + totalpayment);
		
		log.debug("This is debug level logging");
		log.trace("This is trace level logging");
		log.info("This is info level logging");
		log.warn("This is warn level logging");
		log.error("This is error level logging");
		
		
		return new ResponseEntity< >(testlog,HttpStatus.CREATED);
		
	}
}
