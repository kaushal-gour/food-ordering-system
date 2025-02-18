package com.food.ordering.system.order.service.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publishers.payment.OrderCreatedPaymentRequestMessagePublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderCreatedEventApplicationListner {
	
	Logger log = LoggerFactory.getLogger(OrderCreatedEventApplicationListner.class);
	
	private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

	public OrderCreatedEventApplicationListner(
			OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
		super();
		this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
	}
	
	@TransactionalEventListener
	void process(OrderCreatedEvent orderCreatedEvent) {
		
		
	}
	
}
