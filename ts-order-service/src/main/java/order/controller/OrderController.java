package order.controller;

import order.domain.*;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    private RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Order Service ] !";
    }

    @RequestMapping(path = "/createNewOrders", method = RequestMethod.POST)
    public CreateOrderResult createNewOrder(@RequestBody CreateOrderInfo coi){
        VerifyResult tokenResult = verifySsoLogin(coi.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[OrderService][VerifyLogin] Success");
            return orderService.create(coi.getOrder());
        }else{
            System.out.println("[OrderService][VerifyLogin] Fail");
            CreateOrderResult cor = new CreateOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }
    }

    @RequestMapping(path = "/alterOrder", method = RequestMethod.POST)
    public Order alterOrder(@RequestBody OrderAlterInfo oai){
        return orderService.alterOrder(oai);
    }

    @RequestMapping(path = "/queryOrders", method = RequestMethod.POST)
    public ArrayList<Order> queryOrders(@RequestBody QueryInfo qi){
        VerifyResult tokenResult = verifySsoLogin(qi.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[OrderService][VerifyLogin] Success");
            return orderService.queryOrders(qi);
        }else{
            System.out.println("[OrderService][VerifyLogin] Fail");
            return new ArrayList<Order>();
        }
    }

    @RequestMapping(path = "/saveOrderInfo", method = RequestMethod.PUT)
    public ChangeOrderResult saveOrderInfo(@RequestBody ChangeOrderInfo orderInfo){
        VerifyResult tokenResult = verifySsoLogin(orderInfo.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[OrderService][VerifyLogin] Success");
            return orderService.saveChanges(orderInfo.getOrder());
        }else{
            System.out.println("[OrderService][VerifyLogin] Fail");
            ChangeOrderResult cor = new ChangeOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }
    }

    @RequestMapping(path="/cancelOrder", method = RequestMethod.POST)
    public CancelOrderResult cancelOrder(@RequestBody CancelOrderInfo coi){
        VerifyResult tokenResult = verifySsoLogin(coi.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[OrderService][VerifyLogin] Success");
            return orderService.cancelOrder(coi);
        }else{
            System.out.println("[OrderService][VerifyLogin] Fail");
            CancelOrderResult cor = new CancelOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }
    }

    private VerifyResult verifySsoLogin(String loginToken){
        restTemplate = new RestTemplate();
        System.out.println("[OrderService][VerifyLogin] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }

}
