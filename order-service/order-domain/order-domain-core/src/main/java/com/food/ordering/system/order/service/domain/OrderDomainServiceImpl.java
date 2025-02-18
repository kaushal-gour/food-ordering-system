package com.food.ordering.system.order.service.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
	
	Logger log = LoggerFactory.getLogger(OrderDomainServiceImpl.class);

	@Override
	public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
		validateRestaurent(restaurant);
		setOrderProductInformation(order, restaurant);
		order.validateOrder();
		order.initializeOrder();
		log.info("Order with id: {} is initialized", order.getId().getValue());
		return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
	}

	@Override
	public OrderPaidEvent payOrder(Order order) {
		order.pay();
		log.info("Order with id: {} is paid", order.getId().getValue());
		return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
	}

	@Override
	public void approveOrder(Order order) {
		order.approve();
		log.info("order is approved with id {}", order.getId().getValue());
	}

	@Override
	public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
		order.initCancel(failureMessages);
		log.info("order payemnt is cancelling for order id {}", order.getId().getValue());
		return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
	}

	@Override
	public void cancelOrder(Order order, List<String> failureMessages) {
		order.cancel(failureMessages);
		log.info("order with id {} is cancelled", order.getId().getValue());
	}
	
	private void validateRestaurent(Restaurant restaurant) {
		if(!restaurant.isActive()) {
			throw new OrderDomainException("Restaurent with id :" + restaurant.getId().getValue()+ "is not active currently!");
		}
	}
	
	private void setOrderProductInformation(Order order, Restaurant restaurant) {
		order.getOrderItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurentProduct -> {
			Product currentProduct = orderItem.getProduct();
			if(currentProduct.equals(restaurentProduct)) {
				currentProduct.updateWithConfirmedNameAndPrice(restaurentProduct.getName(), restaurentProduct.getPrice());
			}
		}));
	}

}
