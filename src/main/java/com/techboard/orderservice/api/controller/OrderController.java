package com.techboard.orderservice.api.controller;

import com.techboard.orderservice.api.common.PaymentDTO;
import com.techboard.orderservice.api.common.TransactionRequest;
import com.techboard.orderservice.api.common.TransactionResponse;
import com.techboard.orderservice.api.entity.Order;
import com.techboard.orderservice.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest transactionRequest){

        return orderService.saveOrder(transactionRequest);

    }

}
