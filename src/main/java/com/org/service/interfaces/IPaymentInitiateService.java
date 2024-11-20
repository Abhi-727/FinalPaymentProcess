package com.org.service.interfaces;

import com.org.dto.InitiatePaymentReqDto;
import com.org.dto.InitiatePaymentRespDto;

public interface IPaymentInitiateService {

	public InitiatePaymentRespDto initiatePayment(InitiatePaymentReqDto req);
}
