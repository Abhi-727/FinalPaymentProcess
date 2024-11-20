package com.org.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.org.constant.TransactionStausEnum;
import com.org.service.interfaces.PaymentStatusHandler;
import com.org.serviceImpl.Handler.CreateStatusHandler;
import com.org.serviceImpl.Handler.FailedStatusHandler;
import com.org.serviceImpl.Handler.InititateStatusHandler;
import com.org.serviceImpl.Handler.PendingStatusHandler;
import com.org.serviceImpl.Handler.SuccessStatusHandler;

@Component
public class PaymentStatusFactory {

	/*
	 * private static final int CREATED = 1; private static final int INITIATED = 2;
	 */
	@Autowired
	private ApplicationContext ctxt;

	public PaymentStatusHandler getStatusHandler(TransactionStausEnum statusenum) {

		switch (statusenum) {
		case CREATED:
			System.out.println("created status for createstatushandler :" + statusenum);
			CreateStatusHandler createhandler = ctxt.getBean(CreateStatusHandler.class);

			System.out.println("got bean from Applicationcontext ");
			return createhandler;

		case INITIATED:

			return ctxt.getBean(InititateStatusHandler.class);

		case PENDING:

			return ctxt.getBean(PendingStatusHandler.class);

		case SUCCESS:
			return ctxt.getBean(SuccessStatusHandler.class);

		case FAIL:
			return ctxt.getBean(FailedStatusHandler.class);

		default:
			System.out.println("unable to find status ");
			return null;

		}

	}
}
