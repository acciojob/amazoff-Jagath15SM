package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    OrderService orderService;


    // 1. add Order into database
    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody() Order order){
        String result = orderService.addOrder(order);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // 2. Add a Delivery Partner
    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable("partnerId") String partnerId){
        String result = orderService.addPartner(partnerId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // 3. add order-partner pair
    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> addOrderPartnerPair(@RequestParam("order") String orderId, @RequestParam("partner") String partnerId){
        String result = orderService.addOrderPartnerPair(orderId, partnerId);
        //This is basically assigning that order to that partnerId
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // 4. Get Order by orderId
    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") String orderId){
        Order order= orderService.getOrderById(orderId);
        //order should be returned with an orderId.

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // 5. Get DeliveryPartner by partnerId
    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable("partnerId") String partnerId){
        DeliveryPartner deliveryPartner = orderService.getPartnerById(partnerId);
        //deliveryPartner should contain the value given by partnerId
        return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
    }

    // 6. Get number of orders
    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable("partnerId") String partnerId){
        Integer orderCount = orderService.getOrderCountByPartnerId(partnerId);
        //orderCount should denote the orders given by a partner-id
        return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
    }

    // 7. get List of OrderID by partnerID
    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable("partnerId") String partnerId){
        List<String> orders = orderService.getOrdersByPartnerId(partnerId);
        //orders should contain a list of orders by PartnerId
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    // 8. Get all orderIds
    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> getAllOrders(){
        List<String> orders = orderService.getAllOrders();
        //Get all orders
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    // 9. Get count of unassigned orders
    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        Integer countOfOrders = orderService.getCountOfUnassignedOrders();
        //Count of orders that have not been assigned to any DeliveryPartner
        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    // 10. Get number of undelivered orders
    @GetMapping("/get-count-of-orders-left-after-given-time/{time}/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(@PathVariable("time") String time, @PathVariable("partnerId") String partnerId){
        Integer countOfOrders = orderService.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
        //countOfOrders that are left after a particular time of a DeliveryPartner
        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    // 11. Get last delivery time
    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(@PathVariable("partnerId") String partnerId){
        String time = orderService.getLastDeliveryTimeByPartnerId(partnerId);
        //Return the time when that partnerId will deliver his last delivery order.
        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }

    // 12. Delete partnerId and delete anything associated with it.
    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable("partnerId") String partnerId){
        String result = orderService.deletePartnerById(partnerId);
        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // 13. Delete orders associated orderId
    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable("orderId") String orderId){
        String result = orderService.deleteOrderById(orderId);
        //Delete an order and also
        // remove it from the assigned order of that partnerId
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
