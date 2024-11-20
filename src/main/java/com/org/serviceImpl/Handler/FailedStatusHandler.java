package com.org.serviceImpl.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.dao.ITransactionDao;
import com.org.dao.TxnStatusLog;
import com.org.dto.TransactionDto;
import com.org.dto.TransactionLogDto;
import com.org.service.interfaces.PaymentStatusHandler;

@Component
public class FailedStatusHandler extends PaymentStatusHandler{

	@Autowired
	private ITransactionDao txnDao;
	
	@Autowired
	private TxnStatusLog txnLogDao;
	
	@Override
	public TransactionDto processStatus(TransactionDto txnDTO) {
		System.out.println("FailedStatusHandler.processStatus()|| "
				+ "payment:" + txnDTO);
		
		TransactionDto beforeTxn = txnDao.getTransactionById(txnDTO.getId());
		
		boolean isUpdate = txnDao.updateTxnStatus(txnDTO);
		
		System.out.println("REceived below response from DAO"
				+ "|isUpdate:" + isUpdate);
		
		TransactionLogDto logDto = new TransactionLogDto();
		logDto.setTransactionId(txnDTO.getId());
		logDto.setTxnFromStatus(beforeTxn.getTxnStatus());
		logDto.setTxnToStatus(txnDTO.getTxnStatus());
		
		int insertCount = txnLogDao.createTransactionLog(logDto);
		
		if(insertCount == 1) {
			System.out.println("Txn Log Created|logDto:" + logDto);
		} else {
			System.out.println("FAILED to insert Log in DB|logDto:" + logDto);
		}
		
		return txnDTO;
	}

}
