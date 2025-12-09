// src/main/java/com/example/hotel/controller/InquiryController.java
package com.example.hotel.controller;

import com.example.hotel.model.ServiceCategory;
import com.example.hotel.model.ServiceGuideItem;
import com.example.hotel.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @GetMapping
    public String guideHome(Model model) {
        List<String> categories = inquiryService.getCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("labels", inquiryService);
        return "inquiry/guide";
    }

    @GetMapping("/{category}")
    public String viewCategory(@PathVariable String category, Model model) {
        if (!ServiceCategory.getAllCodes().contains(category)) {
            // 返回 404 页面或重定向
            return "error/404";
        }
        List<ServiceGuideItem> items = inquiryService.getItemsByCategory(category);
        model.addAttribute("category", category);
        model.addAttribute("label", inquiryService.getCategoryLabel(category));
        model.addAttribute("items", items);
        return "inquiry/item-detail";
    }
}