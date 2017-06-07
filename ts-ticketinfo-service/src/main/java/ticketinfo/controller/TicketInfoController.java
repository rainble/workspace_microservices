package ticketinfo.controller;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ticketinfo.domain.QueryForTravel;
import ticketinfo.domain.ResultForTravel;
import ticketinfo.service.TicketInfoService;

@RestController
public class TicketInfoController {

    @Autowired
    TicketInfoService service;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/ticketinfo/queryForTravel", method = RequestMethod.POST)
    public ResultForTravel queryForTravel(@RequestBody QueryForTravel info){
        return service.queryForTravel(info);
    }
}
