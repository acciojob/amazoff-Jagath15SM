package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    // 1. add Order into database
    public String addOrder(Order order){
        String result = orderRepository.addOrder(order);
        return result;
    }

    // 2. Add a Delivery Partner
    public String addPartner(String partnerId){
        String result = orderRepository.addPartner(partnerId);
        return result;
    }

    // 3. add order-partner pair
    public String addOrderPartnerPair(String orderId, String partnerId){
        String result = orderRepository.addOrderPartnerPair(orderId, partnerId);
        return result;
    }

    // 4. Get Order by orderId
    public Order getOrderById(String orderId){
        Order order= orderRepository.getOrderById(orderId);
        //order should be returned with an orderId.
        return order;
    }

    // 5. Get DeliveryPartner by partnerId
    public DeliveryPartner getPartnerById(String partnerId){
        DeliveryPartner deliveryPartner = orderRepository.getPartnerById(partnerId);
        //deliveryPartner should contain the value given by partnerId
        return deliveryPartner;
    }

    // 6. Get number of orders
    public Integer getOrderCountByPartnerId(String partnerId){
        Integer orderCount = orderRepository.getOrderCountByPartnerId(partnerId);
        //orderCount should denote the orders given by a partner-id
        return orderCount;
    }

    // 7. get List of OrderID by partnerID
    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> orders = orderRepository.getOrdersByPartnerId(partnerId);
        return orders;
    }

    // 8. Get all orderIds
    public List<String> getAllOrders(){
        List<String> orders = orderRepository.getAllOrders();
        //Get all orders
        return orders;
    }

    // 9. Get count of unassigned orders
    public Integer getCountOfUnassignedOrders(){
        Integer countOfOrders = orderRepository.getCountOfUnassignedOrders();
        //Count of orders that have not been assigned to any DeliveryPartner
        return countOfOrders;
    }

    // 10. Get number of undelivered orders
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }


    // 11. Get last delivery time
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    // 12. Delete partnerId and delete anything associated with it.
    public String deletePartnerById(String partnerId){
        String result = orderRepository.deletePartnerById(partnerId);
        return result;
    }

    // 13. Delete orders associated orderId
    public String deleteOrderById(String orderId){
        String result = orderRepository.deleteOrderById(orderId);
        return result;
    }

}
