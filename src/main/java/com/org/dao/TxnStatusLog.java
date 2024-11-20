package com.org.dao;

import com.org.dto.TransactionLogDto;

public interface TxnStatusLog {

	public int createTransactionLog(TransactionLogDto transactionLog);
}
