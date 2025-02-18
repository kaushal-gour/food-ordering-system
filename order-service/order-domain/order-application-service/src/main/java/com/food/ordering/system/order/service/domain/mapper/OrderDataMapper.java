package com.food.ordering.system.order.service.domain.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valuobject.StreetAddress;

@Component
public class OrderDataMapper {
	
	public Restaurant createOrderCommandToRestaurent(CreateOrderCommand createOrderCommand) {
		
		return Restaurant.builder()
				.restaurantId(new RestaurantId(createOrderCommand.getRestaurentId()))
				.products(createOrderCommand.getOrderItems().stream().map(orderItem -> new Product(new ProductId(orderItem.getProductId())))
				.collect(Collectors.toList()))
				.build();
	}
	
	public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
		return Order.builder()
				.withCustomerId(new CustomerId(createOrderCommand.getCustomerId()))
				.withRestaurantId(new RestaurantId(createOrderCommand.getRestaurentId()))
				.withDeliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
				.withPrice(new Money(createOrderCommand.getPrice()))
				.withOrderItems(orderItemsToOrderItemEntities(createOrderCommand.getOrderItems()))
				.build();
	}
	
	private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
		return new StreetAddress(UUID.randomUUID(), orderAddress.getStreet(), orderAddress.getPostalCode(), orderAddress.getCity());
	}
	
	private List<OrderItem> orderItemsToOrderItemEntities(List<com.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems) {
		return orderItems.stream().map(orderItem -> OrderItem.builder()
				.withProduct(new Product(new ProductId(orderItem.getProductId())))
				.withPrice(new Money(orderItem.getPrice()))
				.withQty(orderItem.getQuantity())
				.withSubTotal(new Money(orderItem.getSubTotal()))
				.build()).collect(Collectors.toList());
	}
	
	public CreateOrderResponse orderTocreateOrderResponse(Order order) {
		CreateOrderResponse createOrderResponse = new CreateOrderResponse();
		createOrderResponse.setOrderTrackingId(order.getTrackingId().getValue());
		createOrderResponse.setOrderStatus(order.getOrderStatus());
		return createOrderResponse;
	}

}
