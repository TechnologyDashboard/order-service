package com.techboard.orderservice.api.common;

import com.techboard.orderservice.api.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionResponse {

    private Order order;
    //private PaymentDTO paymentDTO;
    private String transactionID;
    private double amount;
    private String message;
}
