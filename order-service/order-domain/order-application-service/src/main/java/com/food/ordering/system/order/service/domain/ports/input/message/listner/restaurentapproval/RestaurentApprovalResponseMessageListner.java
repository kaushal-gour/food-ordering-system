package com.food.ordering.system.order.service.domain.ports.input.message.listner.restaurentapproval;

import com.food.ordering.system.order.service.domain.dto.message.RetaurentApprovalResponse;

public interface RestaurentApprovalResponseMessageListner {
	
	void orderApproved(RetaurentApprovalResponse response);
	
	void orderRejectted(RetaurentApprovalResponse response);

}
