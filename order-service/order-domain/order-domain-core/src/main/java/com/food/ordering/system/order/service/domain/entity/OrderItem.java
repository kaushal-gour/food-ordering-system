package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.valuobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {

	private OrderId orderId;
	private final Product product;
	private final int qty;
	private final Money price;
	private final Money subTotal;

	public void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
		this.orderId = orderId;
		super.setId(orderItemId);
	}

	private OrderItem(Builder builder) {
		super(builder.id);
		this.product = builder.product;
		this.qty = builder.qty;
		this.price = builder.price;
		this.subTotal = builder.subTotal;
	}

	public boolean isPriceValid() {
		return price.isGreatedThanZero() && price.equals(product.getPrice()) && price.multiply(qty).equals(subTotal);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private OrderItemId id;
		private Product product;
		private int qty;
		private Money price;
		private Money subTotal;

		private Builder() {
		}

		public Builder withOrderItemId(OrderItemId id) {
			this.id = id;
			return this;
		}

		public Builder withProduct(Product product) {
			this.product = product;
			return this;
		}

		public Builder withQty(int qty) {
			this.qty = qty;
			return this;
		}

		public Builder withPrice(Money price) {
			this.price = price;
			return this;
		}

		public Builder withSubTotal(Money subTotal) {
			this.subTotal = subTotal;
			return this;
		}

		public OrderItem build() {
			return new OrderItem(this);
		}
	}

	public OrderId getOrderId() {
		return orderId;
	}

	public Product getProduct() {
		return product;
	}

	public int getQty() {
		return qty;
	}

	public Money getPrice() {
		return price;
	}

	public Money getSubTotal() {
		return subTotal;
	}

}
