package com.org.service.factory.recon;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.org.dto.TransactionDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ReconAsyncTransaction {
	
	

	@Async
	public void processItem(TransactionDto item) {
		log.info("Processing item : " + item + " - " + Thread.currentThread().getName());
		
		item.getProvider();
	}
}
