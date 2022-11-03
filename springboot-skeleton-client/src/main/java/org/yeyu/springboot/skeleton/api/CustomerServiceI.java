package org.yeyu.springboot.skeleton.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import org.yeyu.springboot.skeleton.dto.CustomerAddCmd;
import org.yeyu.springboot.skeleton.dto.CustomerListByNameQry;
import org.yeyu.springboot.skeleton.dto.data.CustomerDTO;

public interface CustomerServiceI {

    Response addCustomer(CustomerAddCmd customerAddCmd);

    MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry);
}
