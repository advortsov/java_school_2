package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dto.OrderDTO;
import com.tsystems.javaschool.services.interfaces.AdminManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * RESTful controller
 */

@RestController
@RequestMapping("/ws")
public class RESTController {

    @Autowired
    private AdminManager adminManager;

    @RequestMapping(value = "/proceed", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<OrderDTO>
    getOrdersPerPeriod(@RequestParam(value = "start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                       @RequestParam(value = "end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<OrderDTO> ordersDTOPerPeriod = adminManager.getOrdersDTOPerPeriod(startDate, endDate);
        return ordersDTOPerPeriod;
    }

}
