package com.food.ordering.system.order.service.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import com.food.ordering.system.domain.event.publisher.DomianEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApplicationDomianEventPublisher implements ApplicationEventPublisherAware, DomianEventPublisher<OrderCreatedEvent> {
	
	Logger log = LoggerFactory.getLogger(ApplicationDomianEventPublisher.class);
	
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(OrderCreatedEvent domainEvent) {
		this.applicationEventPublisher.publishEvent(domainEvent);
		log.info("OrderCreated Event published successfully!");
	}
	
	

}
