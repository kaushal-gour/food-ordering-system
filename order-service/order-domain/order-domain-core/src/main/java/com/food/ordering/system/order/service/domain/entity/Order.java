package com.food.ordering.system.order.service.domain.entity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.domain.valueobject.OrderStatus;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valuobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valuobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valuobject.TrackingId;

public class Order extends AggregateRoot<OrderId> {

	private final CustomerId customerId;
	private final RestaurantId restaurantId;
	private final StreetAddress deliveryAddress;
	private final Money price;
	private final List<OrderItem> orderItems;

	private TrackingId trackingId;
	private OrderStatus orderStatus;
	private List<String> failureMessages;

	public void initializeOrder() {
		setId(new OrderId(UUID.randomUUID()));
		trackingId = new TrackingId(UUID.randomUUID());
		orderStatus = OrderStatus.PENDING;
		setOrderItems();
	}

	public void validateOrder() {
		validateInitialOrder();
		validateTotalPrice();
		validateItemsPrice();
	}
	
	public void pay() {
		if(orderStatus != OrderStatus.PENDING) {
			throw new OrderDomainException("Order is not correct state for pay opertaion!");
		}
		
		orderStatus = OrderStatus.PAID;
	}
	
	public void approve() {
		if(orderStatus != OrderStatus.PAID) {
			throw new OrderDomainException("Order is not in correct state for approve!");
		}
		orderStatus = OrderStatus.APPROVED;
	}
	
	public void initCancel(List<String> failureMessages) {
		if(orderStatus != OrderStatus.PAID) {
			throw new OrderDomainException("Order is not correct sate of Canelling");
		}
		orderStatus = OrderStatus.CANCELLING;
		updateFailureMessages(failureMessages);
	}
	
	
	public void cancel(List<String> failureMessages) {
		if(orderStatus != OrderStatus.CANCELLING || orderStatus != OrderStatus.PENDING) {
			throw new OrderDomainException("Order is not in correct state of Cancelled");
		}
		orderStatus = OrderStatus.CANCELLED;
		updateFailureMessages(failureMessages);
	}
	
	private void updateFailureMessages(List<String> failureMessages) {
		if(this.failureMessages!=null && failureMessages!=null) {
			this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).collect(Collectors.toList()));
		}
		if(this.failureMessages == null) {
			this.failureMessages = failureMessages;
		}
	}


	private void validateInitialOrder() {
		if (orderStatus != null || getId() != null) {
			throw new OrderDomainException("Order is not correct state for initialization");
		}
	}

	private void validateTotalPrice() {
		if (price == null || !price.isGreatedThanZero()) {
			throw new OrderDomainException("Total price must be greater than Zero");
		}
	}

	private void validateItemsPrice() {
		Money orderItemsTotal = orderItems.stream().map(orderItem -> {
			validateItemPrice(orderItem);
			return orderItem.getSubTotal();
		}).reduce(Money.ZERO, Money::add);

		if (!price.equals(orderItemsTotal)) {
			throw new OrderDomainException("Total price: " + price.getAmount() + " is not equal to order items total "
					+ orderItemsTotal.getAmount() + " !");
		}
	}

	private void validateItemPrice(OrderItem orderItem) {
		if (!orderItem.isPriceValid()) {
			throw new OrderDomainException("Order item price " + orderItem.getPrice().getAmount()
					+ " is not valid for product " + orderItem.getProduct().getId().getValue());
		}
	}

	private void setOrderItems() {
		long itemId = 1;
		for (OrderItem orderItem : orderItems) {
			orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
		}

	}

	private Order(Builder builder) {
		super(builder.orderId);
		this.customerId = builder.customerId;
		this.restaurantId = builder.restaurantId;
		this.deliveryAddress = builder.deliveryAddress;
		this.price = builder.price;
		this.orderItems = builder.orderItems;
		this.trackingId = builder.trackingId;
		this.orderStatus = builder.orderStatus;
		this.failureMessages = builder.failureMessages;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private OrderId orderId;
		private CustomerId customerId;
		private RestaurantId restaurantId;
		private StreetAddress deliveryAddress;
		private Money price;
		private List<OrderItem> orderItems = Collections.emptyList();
		private TrackingId trackingId;
		private OrderStatus orderStatus;
		private List<String> failureMessages = Collections.emptyList();

		private Builder() {
		}

		public Builder withOrderId(OrderId orderId) {
			this.orderId = orderId;
			return this;
		}

		public Builder withCustomerId(CustomerId customerId) {
			this.customerId = customerId;
			return this;
		}

		public Builder withRestaurantId(RestaurantId restaurantId) {
			this.restaurantId = restaurantId;
			return this;
		}

		public Builder withDeliveryAddress(StreetAddress deliveryAddress) {
			this.deliveryAddress = deliveryAddress;
			return this;
		}

		public Builder withPrice(Money price) {
			this.price = price;
			return this;
		}

		public Builder withOrderItems(List<OrderItem> orderItems) {
			this.orderItems = orderItems;
			return this;
		}

		public Builder withTrackingId(TrackingId trackingId) {
			this.trackingId = trackingId;
			return this;
		}

		public Builder withOrderStatus(OrderStatus orderStatus) {
			this.orderStatus = orderStatus;
			return this;
		}

		public Builder withFailureMessages(List<String> failureMessages) {
			this.failureMessages = failureMessages;
			return this;
		}

		public Order build() {
			return new Order(this);
		}
	}

	public CustomerId getCustomerId() {
		return customerId;
	}

	public RestaurantId getRestaurantId() {
		return restaurantId;
	}

	public StreetAddress getDeliveryAddress() {
		return deliveryAddress;
	}

	public Money getPrice() {
		return price;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public TrackingId getTrackingId() {
		return trackingId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public List<String> getFailureMessages() {
		return failureMessages;
	}

}
