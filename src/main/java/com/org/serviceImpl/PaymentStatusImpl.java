package com.org.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.org.constant.ErrorCodeEnum;
import com.org.constant.TransactionStausEnum;
import com.org.dto.TransactionDto;
import com.org.exceptionHandling.ProcessServiceException;
import com.org.service.PaymentService;
import com.org.service.factory.PaymentStatusFactory;
import com.org.service.interfaces.PaymentStatusHandler;

@Component
public class PaymentStatusImpl implements PaymentService {

	@Autowired
	private PaymentStatusFactory statusfactory;

	@Override
	public TransactionDto paymentStatus(TransactionDto payment) {

		System.out.println("PaymentStatusImpl.paymentStatus()");
		
		System.out.println("txnStatus being processed: " + payment.getTxnStatus());

		TransactionStausEnum statusenum = TransactionStausEnum.getByName(payment.getTxnStatus());

		// System.out.println("Transaction enum id : " + statusenum.getId() +
		// "Transaction enum : " + statusenum.getName());

		System.out.println("statusenum : " + statusenum);

		if (statusenum == null) {
			// handle error here,and not process remaining code.

			System.out.println("NULL statusEnum for txnStatus:" + payment.getTxnStatus());

			throw new ProcessServiceException(ErrorCodeEnum.INVALID_TXN_STATUS.getErrorCode(),
					ErrorCodeEnum.INVALID_TXN_STATUS.getErrorMessage(),
					HttpStatus.BAD_REQUEST);

		}

		PaymentStatusHandler statushandler = statusfactory.getStatusHandler(statusenum);
		
		System.out.println();
		
		System.out.println("statushandler | " + statushandler);
		System.out.println();
		System.out.println("status |  " + statusenum );
		

		if (statushandler == null) {
			System.out.println("NULL statushandler for statusenum :" + statusenum);

			throw new ProcessServiceException(ErrorCodeEnum.TXN_STATUS_HANDLER_NOT_CONFIGURED.getErrorCode(),
					ErrorCodeEnum.TXN_STATUS_HANDLER_NOT_CONFIGURED.getErrorMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// CreateStatusHandler handler=new CreateStatusHandler();

   		TransactionDto processhandler = statushandler.processStatus(payment);
		return processhandler;
	}

}
