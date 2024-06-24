package com.springMicroservices.InventoryService.controller;

import com.springMicroservices.InventoryService.dto.InventoryResponse;
import com.springMicroservices.InventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /*By using @PathVariable
    localhost:8082/api/inventory/iphone-13,iphone13-red
    By using @RequestParam
    localhost:8082/api/inventory?skuCode=iphone-13&skuCode=iphone13-red
    @GetMapping("{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode){
        return inventoryService.isInStock(skuCode);
    }*/

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);

    }
}