package org.yeyu.springboot.skeleton.domain.customer.gateway;

import org.yeyu.springboot.skeleton.domain.customer.Credit;

//Assume that the credit info is in another distributed Service
public interface CreditGateway {
    Credit getCredit(String customerId);
}
