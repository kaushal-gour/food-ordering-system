package com.food.ordering.system.order.service.domain;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderCreateHelper {
	
	Logger log = LoggerFactory.getLogger(OrderCreateHelper.class);
	
	private final OrderDomainService orderDomainService;
	
	private final OrderRepository orderRepository;
	
	private final CustomerRepository customerRepository;
	
	private final RestaurentRepository restaurentRepository;
	
	private final OrderDataMapper orderDataMapper;

	public OrderCreateHelper(OrderDomainService orderDomainService, OrderRepository orderRepository,
			CustomerRepository customerRepository, RestaurentRepository restaurentRepository,
			OrderDataMapper orderDataMapper) {
		super();
		this.orderDomainService = orderDomainService;
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.restaurentRepository = restaurentRepository;
		this.orderDataMapper = orderDataMapper;
	}
	
	@Transactional
	public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
		checkCustomer(createOrderCommand.getCustomerId());
		Restaurant restaurant = checkRestaurent(createOrderCommand);
		Order order =  orderDataMapper.createOrderCommandToOrder(createOrderCommand);
		OrderCreatedEvent orderCreatedEvent =  orderDomainService.validateAndInitiateOrder(order, restaurant);
		saveOrder(order);
		log.info("Order created with id {}", order.getId());
		return orderCreatedEvent;
	}
	
	private void checkCustomer(UUID customerId) {
		Optional<Customer> customer = customerRepository.findCustomer(customerId);
		if(customer.isEmpty()) {
			log.warn("Customer not found with customerId {}", customerId);
			throw new OrderDomainException("Customer not found with customerId: "+ customerId);
		}
		
	}
	
	private Restaurant checkRestaurent(CreateOrderCommand createOrderCommand) {
		Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurent(createOrderCommand);
		Optional<Restaurant> retauOptional = restaurentRepository.findResstaurentInformation(restaurant);
		if(retauOptional.isEmpty()) {
			log.warn("Restaurent not foud for restaurentID {}", createOrderCommand.getRestaurentId());
			throw new OrderDomainException("Restaurent not foud for restaurentID :"+ createOrderCommand.getRestaurentId());
		}
		return retauOptional.get();
	}
	
	private Order saveOrder(Order order) {
		Order orderResult =  orderRepository.save(order);
		if(orderResult == null) {
			log.error("Could not saved order object!");
			throw new OrderDomainException("Could not saved order object!");
		}
		log.info("order saved successfullt with id {}", orderResult.getId().getValue());
		return orderResult;
	}


}
