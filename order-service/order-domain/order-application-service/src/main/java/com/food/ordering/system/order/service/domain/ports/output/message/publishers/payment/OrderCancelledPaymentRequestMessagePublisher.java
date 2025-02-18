package com.food.ordering.system.order.service.domain.ports.output.message.publishers.payment;

import com.food.ordering.system.domain.event.publisher.DomianEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomianEventPublisher<OrderCancelledEvent> {

}
