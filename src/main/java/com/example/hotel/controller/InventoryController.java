package com.example.hotel.controller;// InventoryController.java

import com.example.hotel.model.Item;
import com.example.hotel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired private InventoryService inventoryService;

    @GetMapping("")
    public String itemList(Model model) {
        model.addAttribute("items", inventoryService.getAllItems());
        return "inventory/item-list";
    }

    @GetMapping("/in")
    public String stockInForm(Model model) {
        model.addAttribute("item", new Item());
        return "inventory/stock-in-form";
    }

    @PostMapping("/in")
    public String doStockIn(@RequestParam String code,
                            @RequestParam int quantity,
                            @RequestParam String remark) {
        inventoryService.stockIn(code, quantity, remark);
        return "redirect:/inventory";
    }

    @GetMapping("/out")
    public String stockOutForm(Model model) {
        model.addAttribute("item", new Item());
        return "inventory/stock-out-form";
    }

    @PostMapping("/out")
    public String doStockOut(@RequestParam String code,
                             @RequestParam int quantity,
                             @RequestParam String remark) {
        inventoryService.stockOut(code, quantity, remark);
        return "redirect:/inventory";
    }

    @GetMapping("/report/{itemId}")
    public String stockReport(@PathVariable Long itemId, Model model) {
        Item item = inventoryService.getAllItems().stream()
                .filter(i -> i.getId().equals(itemId)).findFirst().orElse(null);
        model.addAttribute("item", item);
        model.addAttribute("history", inventoryService.getStockHistory(itemId));
        return "inventory/stock-report";
    }

    @GetMapping("/stock-index")
    public String stockIndex() {
        return "stock-index";
    }
}