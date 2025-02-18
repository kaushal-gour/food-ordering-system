package com.food.ordering.system.order.service.domain.ports.output.message.publishers.restaurentapproval;

import com.food.ordering.system.domain.event.publisher.DomianEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurentRequestMessagePublisher extends DomianEventPublisher<OrderPaidEvent>{

}
