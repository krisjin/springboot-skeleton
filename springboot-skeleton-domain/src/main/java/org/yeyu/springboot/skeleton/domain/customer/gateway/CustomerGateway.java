package org.yeyu.springboot.skeleton.domain.customer.gateway;

import org.yeyu.springboot.skeleton.domain.customer.Customer;

public interface CustomerGateway {
    Customer getByById(String customerId);
}
