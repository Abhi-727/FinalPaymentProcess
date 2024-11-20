package com.org.serviceImpl.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.dao.ITransactionDao;
import com.org.dao.TxnStatusLog;
import com.org.dto.TransactionDto;
import com.org.dto.TransactionLogDto;
import com.org.service.interfaces.PaymentStatusHandler;

@Component
public class CreateStatusHandler extends PaymentStatusHandler {

	private static final String TXN_FROM_STATUS_DURING_CREATE_TXN = "-";
	@Autowired
	private ITransactionDao txndao;

	@Autowired
	private TxnStatusLog txnLogDao;

	@Override
	public TransactionDto processStatus(TransactionDto payment) {
		
		System.out.println();
		System.out.println();

		System.out.println("CreateStatusHandler.processStatus()" + payment);

		TransactionDto txncreated = txndao.createTransaction(payment);
		System.out.println("Transaction created from cstatusHandler : " + txncreated);

		TransactionLogDto logDto = new TransactionLogDto();
		logDto.setTransactionId(txncreated.getId());
		logDto.setTxnFromStatus(TXN_FROM_STATUS_DURING_CREATE_TXN);
		logDto.setTxnToStatus(txncreated.getTxnStatus());

		int insertcount = txnLogDao.createTransactionLog(logDto);

		if (insertcount == 1) {
			System.out.println("Transaction log created :| logDto : " + logDto);
			System.out.println( );
		} else {
			System.out.println("Failed to insert log in DB| logDto : " + logDto);
		}
		return txncreated;
	}

}
