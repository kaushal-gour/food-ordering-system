package com.food.ordering.system.order.service.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.message.publishers.payment.OrderCreatedPaymentRequestMessagePublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderCreateCommandHandler {
	
	Logger log = LoggerFactory.getLogger(OrderCreateCommandHandler.class);
	
	private final OrderCreateHelper orderCreateHelper;
	
	private final OrderDataMapper orderDataMapper;
	
	private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;
	

	public OrderCreateCommandHandler(Logger log, OrderCreateHelper orderCreateHelper, OrderDataMapper orderDataMapper,
			OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
		super();
		this.orderCreateHelper = orderCreateHelper;
		this.orderDataMapper = orderDataMapper;
		this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
	}


		
	public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
		OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
		log.info("Order created with id {}", orderCreatedEvent.getOrder().getId());
		orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
		return orderDataMapper.orderTocreateOrderResponse(orderCreatedEvent.getOrder());
	}
	
	

}
