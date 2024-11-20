package com.org.pojo.stripe;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AsyncPaymentFailedData  extends StripeDataObj{

	private String id;
	private String status;
	private String paymentStatus;
	
}
