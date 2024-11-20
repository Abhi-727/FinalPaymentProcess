package com.org.service.stripeevent;

import com.org.pojo.stripe.StripeEvent;

public interface IStripeNotificationService {

	void processNotification(StripeEvent event);
}
