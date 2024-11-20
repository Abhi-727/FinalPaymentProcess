package com.org.dao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.org.dto.TransactionLogDto;
import com.org.entity.TransactionLogEntity;

@Repository
public class TxnStatusLogDaoImpl implements TxnStatusLog {
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public int createTransactionLog(TransactionLogDto transactionLog) {
		System.out.println( );
		System.out.println("*****namedParameterJdbcTemplate:" + namedParameterJdbcTemplate);
		
		System.out.println( );
		System.out.println("TransactionLogDaoImpl.createTransaction()"
				+ "| received txnDTO:" + transactionLog);
		
		TransactionLogEntity txnEntity = mapper.map(
				transactionLog, TransactionLogEntity.class);
		System.out.println( );
		System.out.println("Converted Entity:" + txnEntity);
		System.out.println( );

		String sql = "INSERT INTO `Transaction_Log` (transactionId, txnFromStatus, txnToStatus) " +
				"VALUES (:transactionId, :txnFromStatus, :txnToStatus)";
		
		System.out.println("Inserting txnLog query| sql:" );
		
		System.out.println( );
		
		int insertedRowCount = namedParameterJdbcTemplate.update(
				sql,
				new BeanPropertySqlParameterSource(txnEntity));
		
		if(insertedRowCount == 1) {
			System.out.println( );
			System.out.println("Successfully stored Txn Log in DB| txnEntity:" + txnEntity);
			System.out.println( );
		} else {
			System.out.println("FAILED to store Txn Log in DB| txnEntity:" + txnEntity);
		}
		return insertedRowCount;
		
	
	}

}
