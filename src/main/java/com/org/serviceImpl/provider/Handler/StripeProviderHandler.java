package com.org.serviceImpl.provider.Handler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.org.constant.ErrorCodeEnum;
import com.org.constant.TransactionStausEnum;
import com.org.dto.InitiatePaymentReqDto;
import com.org.dto.InitiatePaymentRespDto;
import com.org.dto.TransactionDto;
import com.org.exceptionHandling.ProcessServiceException;
import com.org.http.HttpRequest;
import com.org.http.HttpServiceEngine;
import com.org.service.interfaces.ProviderService;
import com.org.serviceImpl.PaymentStatusImpl;
import com.org.stripeprovider.CreatePaymentReq;
import com.org.stripeprovider.CreatePaymentResp;
import com.org.stripeprovider.ErrorResponsHandle;

@Service
public class StripeProviderHandler implements ProviderService {

	@Autowired
	private HttpServiceEngine httpserviceEngine;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PaymentStatusImpl status;

	@Autowired
	private Gson gson;

	@Value("${stripeprovider.payments.createpayment}")
	private String stripePaymentUrl;

	@Override
	public InitiatePaymentRespDto paymentProcess(TransactionDto dto, InitiatePaymentReqDto reqpayment) {
		
		System.out.println();
		System.out.println("StripeProviderHandler.paymentProcess()" + " |dto : " +
											dto + " | reqpayment : " + reqpayment);
		
		System.out.println();

		// HTTP CALL
		// TODO finalise the request to pass to stripeprovider

		//methods
		HttpRequest httpReq = prepareHttpReq(dto , reqpayment);
		
		
		//update status 
		dto.setTxnStatus(TransactionStausEnum.INITIATED.getName());
		status.paymentStatus(dto);
		System.out.println("update status as txnstatus : " + dto.getTxnStatus());
		System.out.println();
		System.out.println();

		ResponseEntity<String> response = httpserviceEngine.makeHttpRequest(httpReq);
		System.out.println();
		System.out.println("response from HTTP SERVICE : " + response);

		//methods
		InitiatePaymentRespDto dtoAfterResponse = processStatusResponse(dto, response);
		System.out.println("successfully processed response | dtoAfterResponse : " + dtoAfterResponse);
		
		
		dto.setProviderReference(dtoAfterResponse.getId());
		
		//update status
				dto.setTxnStatus(TransactionStausEnum.PENDING.getName());
				status.paymentStatus(dto);
				System.out.println("update status as txnstatus : " + dto.getTxnStatus());
				
				
		
		return dtoAfterResponse;

	}

	private InitiatePaymentRespDto processStatusResponse(TransactionDto dto, ResponseEntity<String> response) {
		
		if (HttpStatus.CREATED == response.getStatusCode()) {

			CreatePaymentResp responseObj = gson.fromJson(response.getBody(), CreatePaymentResp.class);
			System.out.println();
			System.out.println();
			System.out.println("Getting response obj : " + responseObj);

			InitiatePaymentRespDto resDto = mapper.map(responseObj, InitiatePaymentRespDto.class);
			resDto.setTxnReference(dto.getTxnReference());
			System.out.println();
			System.out.println("response dto : " + resDto);
			System.out.println();
			return resDto;
		} 
		
		//todo did not get 201,so everything is failure
		
		if(response.getStatusCode().is4xxClientError() || 
				response.getStatusCode().is5xxServerError()) {
			System.out.println("Received 4xx or 5xx erroe | errorResponse : " +response.getStatusCode());
		ErrorResponsHandle errorResponsStripe =	gson.fromJson(response.getBody(), ErrorResponsHandle.class);
		
		throw new ProcessServiceException(errorResponsStripe.getErrorCode(),
												errorResponsStripe.getErrorMessage(),
												HttpStatus.valueOf(response.getStatusCode().value()));
		
		}
		// return "From StripeProviderHandler " ;
		System.out.println("Got exception peocessing httpcall to provider service: " );
		
		throw new ProcessServiceException(ErrorCodeEnum.GENERIC_ERROR.getErrorCode(),
				ErrorCodeEnum.GENERIC_ERROR.getErrorMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private HttpRequest prepareHttpReq(TransactionDto dto , InitiatePaymentReqDto reqpayment) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		CreatePaymentReq paymentreq = mapper.map(reqpayment, CreatePaymentReq.class);
		paymentreq.setTxnRef(dto.getTxnReference());

		System.out.println("Request for stripe provider: | payment req : " + paymentreq);

		// prepare httpreq for passing to httpservice enginee
		HttpRequest httpReq = new HttpRequest();
		httpReq.setUrl(stripePaymentUrl);
		httpReq.setMethod(HttpMethod.POST);
		httpReq.setRequest(paymentreq);
		httpReq.setHttpHeaders(httpHeaders);
		return httpReq;
	}

}
