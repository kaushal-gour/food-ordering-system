package com.food.ordering.system.domain.event.publisher;

import com.food.ordering.system.domain.event.DomianEvent;

public interface DomianEventPublisher<T> {
	
	void publish(T domainEvent);

}
