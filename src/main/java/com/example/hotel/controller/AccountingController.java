// AccountingController.java
package com.example.hotel.controller;


import com.example.hotel.model.Account;
import com.example.hotel.model.AccountCategory;
import com.example.hotel.model.Voucher;
import com.example.hotel.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/accounting")
public class AccountingController {

    @Autowired private AccountingService accountingService;

    // 科目列表
    @GetMapping("/accounts")
    public String accountList(Model model) {
        model.addAttribute("accounts", accountingService.getAllDetailAccounts());
        model.addAttribute("categories", Arrays.asList(AccountCategory.values()));
        return "accounting/account-list";
    }

    // 凭证录入页
    @GetMapping("/voucher/new")
    public String newVoucherForm(Model model) {
        model.addAttribute("voucher", new Voucher());
        model.addAttribute("accounts", accountingService.getAllDetailAccounts());
        return "accounting/voucher-form";
    }

    // 保存凭证
    @PostMapping("/voucher/save")
    public String saveVoucher(@ModelAttribute Voucher voucher) {
        accountingService.saveVoucher(voucher);
        return "redirect:/accounting/ledger/" + voucher.getDebitAccount().getId();
    }

    // 明细账
    @GetMapping("/ledger/{accountId}")
    public String ledger(@PathVariable Long accountId, Model model) {
        Account account = accountingService.getAllDetailAccounts().stream()
                .filter(a -> a.getId().equals(accountId)).findFirst().orElse(null);
        model.addAttribute("account", account);
        model.addAttribute("entries", accountingService.getLedger(accountId));
        return "accounting/ledger";
    }

    // 财务报表
    @GetMapping("/reports")
    public String reports(Model model) {
        LocalDate today = LocalDate.now();

        // 示例：计算货币资金（假设 1001 是现金）
        BigDecimal cash = accountingService.calculateBalance(1L, today); // 假设 ID=1 是现金

        // 示例：营业收入（假设 6001 是主营业务收入）
        BigDecimal revenue = accountingService.calculateBalance(2L, today); // ID=2

        model.addAttribute("cash", cash);
        model.addAttribute("revenue", revenue);
        return "accounting/financial-reports";
    }

    @GetMapping("/finance-index")
    public String financeIndex() {
        return "finance-index";
    }
}