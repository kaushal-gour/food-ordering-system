package com.food.ordering.system.order.service.domain.dto.message;

import java.time.Instant;
import java.util.List;

import com.food.ordering.system.domain.valueobject.OrderApplovalStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RetaurentApprovalResponse {
	
	private String id;
	private String sagaId;
	private String orderId;
	private String restaurentId;
	private Instant createdAt;
	private OrderApplovalStatus orderApprovalStatus;
	private List<String> failureMessages;

}
