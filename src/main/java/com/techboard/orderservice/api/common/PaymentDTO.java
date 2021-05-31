package com.techboard.orderservice.api.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PaymentDTO {

    private int paymentId;
    private String transactionId;
    private String paymentStatus;

    private int orderId;
    private double amount;


}
