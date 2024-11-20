package com.org.service.interfaces;

import com.org.dto.InitiatePaymentRespDto;
import com.org.dto.InitiatePaymentReqDto;
import com.org.dto.TransactionDto;

public interface ProviderService {

	public InitiatePaymentRespDto paymentProcess(TransactionDto dto,InitiatePaymentReqDto reqpayment) ;
}
