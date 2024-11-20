package com.org.service.interfaces;

import com.org.dto.TransactionDto;

public abstract class PaymentStatusHandler {

	public  abstract TransactionDto processStatus(TransactionDto payment);
	
}
