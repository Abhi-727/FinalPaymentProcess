package com.org.dao;

import java.util.List;

import com.org.dto.TransactionDto;

public interface ITransactionDao {

	public TransactionDto createTransaction(TransactionDto txnDTO);
	
	public  TransactionDto getTransactionById(int transactionId);
	
	boolean updateTxnStatus(TransactionDto transaction);
	
	TransactionDto getTransactionByProviderReference(String id);
	
	List<TransactionDto> fetchTransactionsForReconcilation();
	
}
