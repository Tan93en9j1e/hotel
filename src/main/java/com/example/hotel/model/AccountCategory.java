package com.example.hotel.model;

public enum AccountCategory {
    ASSET("资产类"),
    LIABILITY("负债类"),
    EQUITY("所有者权益类"),
    INCOME("收入类"),
    EXPENSE("费用类");

    private final String label;
    AccountCategory(String label) { this.label = label; }
    public String getLabel() { return label; }
}
