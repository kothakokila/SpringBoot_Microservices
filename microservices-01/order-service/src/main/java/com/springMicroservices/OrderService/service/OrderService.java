package com.springMicroservices.OrderService.service;

import com.springMicroservices.OrderService.dto.InventoryResponse;
import com.springMicroservices.OrderService.dto.OrderLineItemsDTO;
import com.springMicroservices.OrderService.dto.OrderRequest;
import com.springMicroservices.OrderService.model.Order;
import com.springMicroservices.OrderService.model.OrderLineItems;
import com.springMicroservices.OrderService.repositoty.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest){
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems=orderRequest.getOrderLineItemsDTOList()
                .stream()
                .map(orderLineItemsDTO ->
                        mapToDTO(orderLineItemsDTO))
                .toList();

        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes=order.getOrderLineItemsList().stream()
                .map(orderLineItem -> orderLineItem.getSkuCode())
                .toList();
        // call inventory service and place order if the product is present
        //bydefault web client makes asynchronous request
        //store this webclient response in local variable which is result of tye boolean
       //Boolean result= webClient.get()
        InventoryResponse[] invtResArray= webClientBuilder.build().get()
                .uri("http://InventoryService/api/inventory",
                        //uriBuilder will create the list of skuCodes and search for our skuCode
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                               // no more boolean, we get a list of Inventory Response
                               // .bodyToMono(Boolean.class)
               .bodyToMono(InventoryResponse[].class)
                                        .block();
        // checks whether all the elements placed is in stock or not
        //even if one of them is not stock it will throw false
        boolean allProdsInStock=Arrays.stream(invtResArray)
                .allMatch(inventoryResponse -> inventoryResponse.isInStock());
       if(allProdsInStock){
        orderRepository.save(order);
        return "Order placed Successfully!!";
       }else{
           throw new IllegalArgumentException("Product is out of stock, check again later");
       }

    }

    private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
        return orderLineItems;
    }
}
