package com.org.service.factory.recon;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.org.dao.ITransactionDao;
import com.org.dto.TransactionDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReconService {
	@Autowired
	private ReconAsyncTransaction reconTxn;
	
	
	/*
	 * @Autowired private ITransactionDao reconTxndao;
	 * 
	 * 
	 * //runs every 15 minutes
	 * 
	 * @Scheduled(cron = "0 0/1 * * * ?") public void performTask() {
	 * log.info("Task executed at " );
	 * 
	 * List<TransactionDto> txnforRecon =
	 * reconTxndao.fetchTransactionsForReconcilation();
	 * 
	 * log.info("About to process recon for txnforRecon.size : " +txnforRecon.size()
	 * );
	 * 
	 * //List<String> items =
	 * Arrays.asList("Item1","Item2","Item3","Item4","Item5");
	 * 
	 * for(TransactionDto item: txnforRecon) {
	 * //log.debug("submiting task for async execution | item:" + item);
	 * log.trace("submiting task for async execution | item:" + item);
	 * reconTxn.processItem(item); } }
	 */
}












