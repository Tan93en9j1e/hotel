package com.example.hotel.model;

import java.util.Arrays;
import java.util.List;

public enum ServiceCategory {
    LOCATION("location", "地理位置"),
    LAYOUT("layout", "楼层布局"),
    ROOM("room", "客房实景"),
    DINING("dining", "餐厅实景"),
    KTV("ktv", "KTV包房"),
    MEETING("meeting", "会议室"),
    DISH("dish", "特色菜肴");

    private final String code;
    private final String label;

    ServiceCategory(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() { return code; }
    public String getLabel() { return label; }

    public static List<String> getAllCodes() {
        return Arrays.stream(values()).map(ServiceCategory::getCode).toList();
    }

    public static String getLabelByCode(String code) {
        return Arrays.stream(values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .map(ServiceCategory::getLabel)
                .orElse(code);
    }
}
