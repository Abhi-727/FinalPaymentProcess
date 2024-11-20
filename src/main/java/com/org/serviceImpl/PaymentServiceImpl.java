package com.org.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.constant.ProviderEnum;
import com.org.dao.ITransactionDao;
import com.org.dto.InitiatePaymentReqDto;
import com.org.dto.InitiatePaymentRespDto;
import com.org.dto.TransactionDto;
import com.org.service.factory.PaymentProviderFactory;
import com.org.service.interfaces.IPaymentInitiateService;
import com.org.service.interfaces.ProviderService;
import com.org.service.interfaces.IPaymentInitiateService;

@Service
public class PaymentServiceImpl implements IPaymentInitiateService {

	@Autowired
	private PaymentProviderFactory pamentfactory;
	
	@Autowired
	private ITransactionDao txnDao;

	@Override
	public InitiatePaymentRespDto initiatePayment(InitiatePaymentReqDto req) {
		System.out.println("PaymentServiceImpl.initiatePayment() : | req : " + req);

		
		
		/*
		 * // todo make DB call using TransactionDAOimPL AND GET TransactionDto
		 * TransactionDto txnDto = new TransactionDto();
		 * txnDto.setTxnReference("TXN-REF-234"); txnDto.setProvider("STRIPE");
		 */
		
		TransactionDto txnDto =txnDao.getTransactionById(req.getId());

		ProviderEnum providerenum = ProviderEnum.getNameById(txnDto.getProvider()); // Gets Provider Enum Using Provider Name
		
		ProviderService providerhandler = pamentfactory.paymentProcess(providerenum);
		
		InitiatePaymentRespDto providerResponse = providerhandler.paymentProcess(txnDto, req);
		
		System.out.println();
		System.out.println("provider response : | " + providerResponse);
		System.out.println();

		return  providerResponse;
	}

}
