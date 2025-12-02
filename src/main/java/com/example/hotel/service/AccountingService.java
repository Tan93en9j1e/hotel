package com.example.hotel.service;// AccountingService.java


import com.example.hotel.model.Account;
import com.example.hotel.model.AccountCategory;
import com.example.hotel.model.Voucher;
import com.example.hotel.repository.AccountRepository;
import com.example.hotel.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AccountingService {

    @Autowired private AccountRepository accountRepo;
    @Autowired private VoucherRepository voucherRepo;

    public void saveVoucher(Voucher voucher) {
        // 简单校验：借贷相等
        if (voucher.getDebitAmount().compareTo(voucher.getCreditAmount()) != 0) {
            throw new IllegalArgumentException("借贷金额必须相等！");
        }
        voucherRepo.save(voucher);
        // 实际生产中可异步更新余额，此处简化
    }

    // 计算某科目截至某日的余额
    public BigDecimal calculateBalance(Long accountId, LocalDate date) {
        Account account = accountRepo.findById(accountId).orElseThrow();
        BigDecimal debitTotal = voucherRepo.sumDebitAmount(accountId, date);
        BigDecimal creditTotal = voucherRepo.sumCreditAmount(accountId, date);

        if (account.getCategory() == AccountCategory.ASSET ||
                account.getCategory() == AccountCategory.EXPENSE) {
            return debitTotal.subtract(creditTotal);
        } else {
            return creditTotal.subtract(debitTotal);
        }
    }

    public List<Account> getAllDetailAccounts() {
        return accountRepo.findByDetailTrueOrderByCode();
    }

    public List<Voucher> getLedger(Long accountId) {
        return voucherRepo.findByDebitAccount_IdOrCreditAccount_IdOrderByDate(accountId, accountId);
    }
}