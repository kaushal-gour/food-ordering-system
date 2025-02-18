package com.food.ordering.system.order.service.domain.dto.create;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.food.ordering.system.domain.valueobject.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderResponse {
	
	@NotNull
	private UUID orderTrackingId;
	@NotNull
	private OrderStatus orderStatus;
	@NotNull
	private String message;
	
	
	public CreateOrderResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CreateOrderResponse(@NotNull UUID orderTrackingId, @NotNull OrderStatus orderStatus,
			@NotNull String message) {
		super();
		this.orderTrackingId = orderTrackingId;
		this.orderStatus = orderStatus;
		this.message = message;
	}

	public UUID getOrderTrackingId() {
		return orderTrackingId;
	}
	public void setOrderTrackingId(UUID orderTrackingId) {
		this.orderTrackingId = orderTrackingId;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
