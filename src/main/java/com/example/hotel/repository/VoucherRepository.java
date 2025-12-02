package com.example.hotel.repository;
;
import com.example.hotel.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findByDebitAccount_IdOrCreditAccount_IdOrderByDate(Long accountId, Long accountId2);

    @Query("SELECT COALESCE(SUM(v.debitAmount), 0) FROM Voucher v WHERE v.debitAccount.id = :accountId AND v.date <= :date")
    BigDecimal sumDebitAmount(Long accountId, LocalDate date);

    @Query("SELECT COALESCE(SUM(v.creditAmount), 0) FROM Voucher v WHERE v.creditAccount.id = :accountId AND v.date <= :date")
    BigDecimal sumCreditAmount(Long accountId, LocalDate date);
}