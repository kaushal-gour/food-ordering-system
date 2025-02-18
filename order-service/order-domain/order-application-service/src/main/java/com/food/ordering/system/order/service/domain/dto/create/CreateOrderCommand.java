package com.food.ordering.system.order.service.domain.dto.create;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@Data
@AllArgsConstructor
public class CreateOrderCommand {
	@NotNull
	private  UUID customerId;
	@NotNull
	private UUID restaurentId;
	@NotNull
	private BigDecimal price;
	@NotNull
	private List<OrderItem> orderItems;
	@NotNull
	private OrderAddress address;
	
	
	
	public UUID getCustomerId() {
		return customerId;
	}
	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
	public UUID getRestaurentId() {
		return restaurentId;
	}
	public void setRestaurentId(UUID restaurentId) {
		this.restaurentId = restaurentId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public OrderAddress getAddress() {
		return address;
	}
	public void setAddress(OrderAddress address) {
		this.address = address;
	}
	
	
	
	

}
