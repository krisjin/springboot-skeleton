package org.yeyu.springboot.skeleton.customer;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.catchlog.CatchAndLog;
import org.yeyu.springboot.skeleton.api.CustomerServiceI;
import org.yeyu.springboot.skeleton.dto.CustomerAddCmd;
import org.yeyu.springboot.skeleton.dto.CustomerListByNameQry;
import org.yeyu.springboot.skeleton.dto.data.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.yeyu.springboot.skeleton.customer.executor.CustomerAddCmdExe;
import org.yeyu.springboot.skeleton.customer.executor.query.CustomerListByNameQryExe;

import javax.annotation.Resource;


@Service
@CatchAndLog
public class CustomerServiceImpl implements CustomerServiceI {

    @Resource
    private CustomerAddCmdExe customerAddCmdExe;

    @Resource
    private CustomerListByNameQryExe customerListByNameQryExe;

    public Response addCustomer(CustomerAddCmd customerAddCmd) {
        return customerAddCmdExe.execute(customerAddCmd);
    }

    @Override
    public MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry) {
        return customerListByNameQryExe.execute(customerListByNameQry);
    }

}