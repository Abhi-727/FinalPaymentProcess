package com.org.pojo.stripe;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionCompleted extends StripeDataObj {

	private String id;
	private String status;
	private String payment_status;
}
