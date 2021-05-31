package com.techboard.orderservice.api.service;

import ch.qos.logback.core.CoreConstants;
import com.techboard.orderservice.api.common.PaymentDTO;
import com.techboard.orderservice.api.common.TransactionRequest;
import com.techboard.orderservice.api.common.TransactionResponse;
import com.techboard.orderservice.api.entity.Order;
import com.techboard.orderservice.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String PAYMENT_ENDPOINT_URL;


    public TransactionResponse saveOrder(TransactionRequest transactionRequest){
        System.out.println("new transactionRequest==>"+transactionRequest);

        String message="";
        Order order =transactionRequest.getOrder();
        PaymentDTO paymentDTO =transactionRequest.getPaymentDTO();
        paymentDTO.setOrderId(order.getId());
        paymentDTO.setAmount(order.getPrice());

        //rest API call
        //once integrated with service registry no need to pass the hardcoded URI,
        // we can directly give the payment Service, the service Name we register with Eureka
        //PaymentDTO paymentResponse=restTemplate.postForObject("http://localhost:9191/payment/doPayment",paymentDTO,PaymentDTO.class);

        //registering with Eureka
        System.out.println("PAYMENT_ENDPOINT_URL==>"+PAYMENT_ENDPOINT_URL);
        PaymentDTO paymentResponse=restTemplate.postForObject(PAYMENT_ENDPOINT_URL,paymentDTO,PaymentDTO.class);
        //PaymentDTO paymentResponse=restTemplate.postForObject("http://PAYMENT-SERVICE/payment/doPayment",paymentDTO,PaymentDTO.class);
        //once hystrix added , will make this fault tolerant, will customise the error message
        message=paymentResponse.getPaymentStatus().equals("success")?
                "payment processing successful and order placed":
                "there is a failure in Payment API, order added to cart";
        orderRepository.save(order);
        return new TransactionResponse(order,paymentResponse.getTransactionId(),
                paymentResponse.getAmount(),message);

    }
}
