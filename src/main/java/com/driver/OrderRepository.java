package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    HashMap<String, Order> orderDB = new HashMap<>(); // orderID, Order
    HashMap<String, DeliveryPartner> deliveryPartners = new HashMap<>(); // partnerId, DeliverPartner
    HashMap<String, List<String>> mapPartnerOrders = new HashMap<>(); // partnerID, list of orderID
    HashMap<String, String> order_partner = new HashMap<>(); // orderId, partnerId

    // 1. add Order into database
    public String addOrder(Order order){
        String id = order.getId();
        orderDB.put(id, order);
        return "New order added successfully";
    }

    // 2. Add a Delivery Partner
    public String addPartner(String partnerId){
        deliveryPartners.put(partnerId, new DeliveryPartner(partnerId));
        return "New delivery partner added successfully";
    }

    // 3. add order-partner pair
    public String addOrderPartnerPair(String orderId, String partnerId){
        if(mapPartnerOrders.containsKey(partnerId)){
            mapPartnerOrders.get(partnerId).add(orderId);
        }
        else{
            mapPartnerOrders.put(partnerId, new ArrayList<String>());
            mapPartnerOrders.get(partnerId).add(orderId);
        }
        int numberOfOrders = deliveryPartners.get(partnerId).getNumberOfOrders();
        deliveryPartners.get(partnerId).setNumberOfOrders(++numberOfOrders);
        order_partner.put(orderId, partnerId);
        //This is basically assigning that order to that partnerId
        return "New order-partner pair added successfully";
    }

    // 4. Get Order by orderId
    public Order getOrderById(String orderId){
        Order order= this.orderDB.get(orderId);
        //order should be returned with an orderId.
        return order;
    }

    // 5. Get DeliveryPartner by partnerId
    public DeliveryPartner getPartnerById(String partnerId){
        DeliveryPartner deliveryPartner = deliveryPartners.get(partnerId);
        //deliveryPartner should contain the value given by partnerId
        return deliveryPartner;
    }

    // 6. Get number of orders
    public Integer getOrderCountByPartnerId(String partnerId){
        Integer orderCount = deliveryPartners.get(partnerId).getNumberOfOrders();
        //orderCount should denote the orders given by a partner-id
        return orderCount;
    }

    // 7. get List of OrderID by partnerID
    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> orders = mapPartnerOrders.get(partnerId);
        //orders should contain a list of orders by PartnerId
        return orders;
    }

    // 8. Get all orderIds
    public List<String> getAllOrders(){
        List<String> orders = new ArrayList<>(orderDB.keySet());
        //Get all orders
        return orders;
    }

    // 9. Get count of unassigned orders
    public Integer getCountOfUnassignedOrders(){
        Integer countOfOrders = 0;
        for(String s : orderDB.keySet()){
            if(!mapPartnerOrders.containsKey(s)){
                countOfOrders++;
            }
        }
        //Count of orders that have not been assigned to any DeliveryPartner
        return countOfOrders;
    }

    // 10. Get number of undelivered orders
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        int currentTime = stringToInteger(time);
        Integer countOfOrders = 0;
        for(String s : mapPartnerOrders.get(partnerId)){
            if(currentTime < stringToInteger(s)){
                countOfOrders++;
            }
        }
        return countOfOrders;
    }

    private int stringToInteger(String time){
        int h = Integer.parseInt(time.substring(0,2));
        int m = Integer.parseInt(time.substring(3));
        int currentTime = h * 60 + m;
        return currentTime;
    }

    // 11. Get last delivery time
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        List<String> timeString = mapPartnerOrders.get(partnerId);
        List<Integer> timeInt = new ArrayList<>();
        for(String t : timeString){
            timeInt.add(stringToInteger(t));
        }
        Collections.sort(timeInt);
        String time = toTime(timeInt.get(timeInt.size() - 1));
        //Return the time when that partnerId will deliver his last delivery order.
        return time;
    }

    private String toTime(int num){
        int h = num / 60;
        int m = num % 60;
        String result = "";
        if(h >= 10){
            result += String.valueOf(h);
        }
        else{
            result += "0" + h;
        }
        result += ":";
        if(m >= 10){
            result += m;
        }
        else{
            result += "0" + m;
        }
        return result;
    }

    // 12. Delete partnerId and delete anything associated with it.
    public String deletePartnerById(String partnerId){
        for(Map.Entry<String, String> entry : order_partner.entrySet()){
            if(entry.getValue().equals(partnerId)){
                order_partner.remove(entry.getKey());
            }
        }
        mapPartnerOrders.remove(partnerId);
        deliveryPartners.remove(partnerId);
        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        return partnerId + " removed successfully";
    }

    // 13. Delete orders associated orderId
    public String deleteOrderById(String orderId){
        if(order_partner.containsKey(orderId)){
            int index = mapPartnerOrders.get(order_partner.get(orderId)).indexOf(orderId);
            mapPartnerOrders.get(order_partner.get(orderId)).remove(index);
            order_partner.remove(orderId);
        }
        orderDB.remove(orderId);
        //Delete an order and also
        // remove it from the assigned order of that partnerId
        return orderId + " removed successfully";
    }


}
