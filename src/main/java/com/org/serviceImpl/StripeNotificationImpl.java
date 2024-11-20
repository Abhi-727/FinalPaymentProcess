package com.org.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.org.constant.TransactionStausEnum;
import com.org.dao.ITransactionDao;
import com.org.dto.TransactionDto;
import com.org.entity.TransactionEntity;
import com.org.pojo.stripe.AsyncPaymentFailedData;
import com.org.pojo.stripe.AsyncPaymentSuccess;
import com.org.pojo.stripe.SessionCompleted;
import com.org.pojo.stripe.StripeEvent;
import com.org.service.PaymentService;
import com.org.service.stripeevent.IStripeNotificationService;

@Service
public class StripeNotificationImpl implements IStripeNotificationService {

	private static final String PAYMENT_STATUS_PAID = "paid";
	private static final String STATUS_COMPLETE = "complete";
	private static final String CHECKOUT_SESSION_ASYNC_PAYMENT_FAILED = "checkout.session.async_payment_failed";
	private static final String CHECKOUT_SESSION_ASYNC_PAYMENT_SUCCESS = "checkout.session.sync_payment_success";
	private static final String CHECKOUT_SESSION_COMPLETED = "checkout.session.completed";

	@Autowired
	private Gson gson;

	@Autowired
	private PaymentService service;

	@Autowired
	private ITransactionDao txnDao;

	@Override
	public void processNotification(StripeEvent event) {

		System.out.println();
		System.out.println("Event = " + event.getType());
		System.out.println("Event = "+event);
		System.out.println();
		System.out.println();

		if (CHECKOUT_SESSION_ASYNC_PAYMENT_FAILED.equals(event.getType())) {
			System.out.println("Update failed processing");

			AsyncPaymentFailedData gsonEvent = gson.fromJson(event.getData().getObject(), AsyncPaymentFailedData.class);
			System.out.println("Got failed Response : " + gsonEvent);

			System.out.println(CHECKOUT_SESSION_ASYNC_PAYMENT_FAILED + " received");
			System.out.println("eventData:" + gsonEvent);

			// fetches the transaction details from the database using a reference ID
			TransactionDto txn = txnDao.getTransactionByProviderReference(gsonEvent.getId());

			// update status
			txn.setTxnStatus(TransactionStausEnum.FAIL.getName());

			service.paymentStatus(txn);

			System.out.println("Updated txn as FAILED");
			return;

		}

		if (CHECKOUT_SESSION_ASYNC_PAYMENT_SUCCESS.equals(event.getType())) {

			System.out.println("Update Succes processing");

			AsyncPaymentSuccess successEvent = gson.fromJson(event.getData().getObject(), AsyncPaymentSuccess.class);
			System.out.println("Got Async succes event : " + successEvent);

			System.out.println(CHECKOUT_SESSION_ASYNC_PAYMENT_SUCCESS + "received");

			TransactionDto txnInfo = txnDao.getTransactionByProviderReference(successEvent.getId());
			// update status
			txnInfo.setTxnStatus(TransactionStausEnum.SUCCESS.getName());

			service.paymentStatus(txnInfo);

			System.out.println("Updated txn as Success");
			return;

		}

		if (CHECKOUT_SESSION_COMPLETED.equals(event.getType())) {
			
			System.out.println();
			System.out.println("Event = "+event);
			System.out.println();
			
			System.out.println();
			System.out.println(event.getData().getObject());
			System.out.println();
			System.out.println();

			SessionCompleted eventComplete = gson.fromJson(event.getData().getObject(), SessionCompleted.class);
			System.out.println("Got session complete event : " + eventComplete);

			if (eventComplete.getStatus().equals(STATUS_COMPLETE)
					&& eventComplete.getPayment_status().equals(PAYMENT_STATUS_PAID)) {
				System.out.println();
				System.out.println("--- SUCCESSFULLY PROCESSED ---");
				System.out.println();
				System.out.println();

				TransactionDto txnInfo = txnDao.getTransactionByProviderReference(eventComplete.getId());
				
				System.out.println( );
				System.out.println("txnInfo before success : txnInfo | " + txnInfo);
				
				txnInfo.setTxnStatus(TransactionStausEnum.SUCCESS.getName());
				
				System.out.println( );
				
				System.out.println("UpdatedStatus : " + txnInfo.getTxnStatus());
				
				System.out.println("txnInfo After success : txnInfo | " + txnInfo);
				
				//TransactionDto updatetxn = txnInfo;
				
				//System.out.println("AbhiSri");

				service.paymentStatus(txnInfo);
				//service.paymentStatus(updatetxn);
				
				

				System.out.println("Updated txn as SUCCESS");

			}
			return;

		}

		System.out.println("Received eventType:" + event.getType());

	}
}
