package org.yeyu.springboot.skeleton.customer.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import org.yeyu.springboot.skeleton.dto.CustomerListByNameQry;
import org.yeyu.springboot.skeleton.dto.data.CustomerDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class CustomerListByNameQryExe{
    public MultiResponse<CustomerDTO> execute(CustomerListByNameQry cmd) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("Frank");
        customerDTOList.add(customerDTO);
        return MultiResponse.of(customerDTOList);
    }
}
