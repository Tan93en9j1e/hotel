package com.example.hotel.controller;

import com.example.hotel.model.DiningTable;
import com.example.hotel.model.OrderItem;
import com.example.hotel.service.DiningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/dining")
public class DiningController {

    @Autowired
    private DiningService diningService;

    // 餐位管理
    @GetMapping("/tables")
    public String listTables(Model model) {
        model.addAttribute("tables", diningService.getAllTables());
        return "dining/table-list";
    }

    @GetMapping("/tables/new")
    public String newTableForm(Model model) {
        model.addAttribute("table", new DiningTable());
        return "dining/table-form";
    }

    @PostMapping("/tables")
    public String createTable(@ModelAttribute DiningTable table) {
        diningService.saveTable(table);
        return "redirect:/dining/tables";
    }

    @GetMapping("/tables/edit/{number}")
    public String editTableForm(@PathVariable String number, Model model) {
        model.addAttribute("table", diningService.getAllTables().stream()
                .filter(t -> t.getTableNumber().equals(number)).findFirst().orElse(new DiningTable()));
        return "dining/table-form";
    }

    @PostMapping("/tables/update/{number}")
    public String updateTable(@PathVariable String number, @ModelAttribute DiningTable table) {
        table.setTableNumber(number);
        diningService.saveTable(table);
        return "redirect:/dining/tables";
    }

    @GetMapping("/tables/delete/{number}")
    public String deleteTable(@PathVariable String number) {
        diningService.deleteTable(number);
        return "redirect:/dining/tables";
    }

    // 点单
    @GetMapping("/order/{tableNumber}")
    public String orderForm(@PathVariable String tableNumber, Model model) {
        model.addAttribute("tableNumber", tableNumber);
        model.addAttribute("orderItem", new OrderItem());
        return "dining/order-form";
    }

    @PostMapping("/order")
    public String placeOrder(@ModelAttribute OrderItem item) {
        diningService.addOrderItem(item);
        return "redirect:/dining/bill/" + item.getTableNumber();
    }

    // 查看账单 & 结账
    @GetMapping("/bill/{tableNumber}")
    public String viewBill(@PathVariable String tableNumber, Model model) {
        List<OrderItem> items = diningService.getUnbilledItems(tableNumber);
        BigDecimal total = diningService.calculateTotal(tableNumber);
        model.addAttribute("tableNumber", tableNumber);
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        return "dining-bill";
    }

    @GetMapping("/settle/{tableNumber}")
    public String settleBill(@PathVariable String tableNumber) {
        diningService.settleBill(tableNumber);
        return "redirect:/dining/tables";
    }
}