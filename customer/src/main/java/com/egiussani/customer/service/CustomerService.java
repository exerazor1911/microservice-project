package com.egiussani.customer.service;

import com.egiussani.amqp.config.RabbitMQMessageProducer;
import com.egiussani.clients.fraud.FraudClient;
import com.egiussani.clients.fraud.dto.FraudCheckResponse;
import com.egiussani.clients.notification.dto.NotificationRequest;
import com.egiussani.customer.dto.CustomerRegistrationRequest;
import com.egiussani.customer.model.Customer;
import com.egiussani.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        // todo: check if email is valid
        // todo: check if email not taken

        customerRepository.saveAndFlush(customer);
        //todo: check if fraudster
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) throw new IllegalStateException("fraudster");

        NotificationRequest request = NotificationRequest.builder()
                .toCustomerId(customer.getId())
                .toCustomerName(customer.getEmail())
                .message(String.format("Hi %s, welcome to egiussani", customer.getFirstName()))
                .build();

        rabbitMQMessageProducer.publish(request, "internal.exchange", "internal.notification.routing-key");
    }
}
