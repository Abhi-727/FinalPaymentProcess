package com.org.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.dto.InitiatePaymentReqDto;
import com.org.dto.InitiatePaymentRespDto;
import com.org.dto.TransactionDto;
import com.org.pojo.InitiatePaymentReq;
import com.org.pojo.InitiatePaymentResp;
import com.org.pojo.Transaction;
import com.org.pojo.createdTransactionRespon;
import com.org.service.PaymentService;
import com.org.service.interfaces.IPaymentInitiateService;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

	private PaymentService service;

	private ModelMapper mapper;

	private IPaymentInitiateService paymentservice;

	// constructor injection
	public PaymentController(ModelMapper mapper, PaymentService service, IPaymentInitiateService paymentservice) {
		this.mapper = mapper;
		this.service = service;
		this.paymentservice = paymentservice;
	}

	@PostMapping("/pay")
	public ResponseEntity<createdTransactionRespon> createpayment(@RequestBody Transaction transaction) {

		System.out.println("****payment process started***********");
		System.out.println(" :" + transaction);

		// using mapper
		TransactionDto txnDto = mapper.map(transaction, TransactionDto.class);

		System.out.println("Transaction after convertingdto :" + txnDto);

		TransactionDto returnObj = service.paymentStatus(txnDto);

		System.out.println("Paymentcontroller : " + returnObj);

		// create obj for,store response id,status into it.
		createdTransactionRespon response = new createdTransactionRespon();
		response.setId(returnObj.getId());
		response.setTxnStatus(returnObj.getTxnStatus());

		System.out.println("Response Entity : " + response);
		
		System.out.println();

		ResponseEntity<createdTransactionRespon> responseEntity = new ResponseEntity<createdTransactionRespon>(response,
				HttpStatus.CREATED);
		
		System.out.println();
		System.out.println();
		System.out.println();

		return responseEntity;

	}

	@PostMapping("/initiatepayment/{id}")
	public ResponseEntity<InitiatePaymentResp> initiatePayment(@PathVariable("id") int id,
			@RequestBody InitiatePaymentReq paymentreq) {

		System.out.println();
		System.out.println("PaymentController.initiatePayment() : | id :" + id + "| InitiatePaymentReq :" + paymentreq);
		System.out.println();
		System.out.println();
		InitiatePaymentReqDto reqDto = mapper.map(paymentreq, InitiatePaymentReqDto.class); // here we didn't get id,so
																							// explicitly giving id
		reqDto.setId(id);

		System.out.println("converted to dto  : |" + reqDto);

		InitiatePaymentRespDto serviceRes = paymentservice.initiatePayment(reqDto);
		System.out.println("service response :|" + serviceRes);
		
		InitiatePaymentResp reponse =   mapper.map(serviceRes, InitiatePaymentResp.class);
		System.out.println("final response : "+reponse );

		return new ResponseEntity<>(reponse, HttpStatus.OK);
	}

}
