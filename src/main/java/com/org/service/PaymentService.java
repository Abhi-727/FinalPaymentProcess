package com.org.service;

import com.org.dto.TransactionDto;

public interface PaymentService {

	public TransactionDto paymentStatus(TransactionDto payment);
}
