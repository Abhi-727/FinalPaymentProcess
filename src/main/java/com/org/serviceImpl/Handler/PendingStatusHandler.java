package com.org.serviceImpl.Handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.dao.ITransactionDao;
import com.org.dao.TxnStatusLog;
import com.org.dto.TransactionDto;
import com.org.dto.TransactionLogDto;
import com.org.service.interfaces.PaymentStatusHandler;

@Component
public class PendingStatusHandler extends PaymentStatusHandler  {

	
	@Autowired
	private ITransactionDao txndao;

	@Autowired
	private TxnStatusLog txnLogDao;



	@Override
	public TransactionDto processStatus(TransactionDto payment) {
		System.out.println();
		System.out.println("PendingStatusHandler.processStatus() : "+ payment);		
		TransactionDto txnId = txndao.getTransactionById(payment.getId());

		//update incoming req
		boolean statusUpdate = txndao.updateTxnStatus(payment);
		System.out.println("Transaction created from statusHandler : " + statusUpdate);

		TransactionLogDto logDto = new TransactionLogDto();
		logDto.setTransactionId(payment.getId());
		logDto.setTxnFromStatus(txnId.getTxnStatus());
		logDto.setTxnToStatus(payment.getTxnStatus());

		int insertcount = txnLogDao.createTransactionLog(logDto);

		if(insertcount == 1) {
			System.out.println("Transaction log created :| logDto : " +logDto );
		}else {
			System.out.println("Failed to insert log in DB| logDto : " + logDto);
		}
		return  payment ;
	}


}
