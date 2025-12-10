// src/main/java/com/example/hotelmanagement/dto/GroupForm.java

package com.example.hotel.dto;

import com.example.hotel.model.*;

import java.util.ArrayList;
import java.util.List;

public class GroupForm {
    private GuestGroup group = new GuestGroup();
    private List<Guest> members = new ArrayList<>();

    public GroupForm() {
        // 初始化5个空成员，方便前端显示
        for (int i = 0; i < 5; i++) {
            members.add(new Guest());
        }
    }

    public GuestGroup getGroup() {
        return group;
    }

    public void setGroup(GuestGroup group) {
        this.group = group;
    }

    public List<Guest> getMembers() {
        return members;
    }

    public void setMembers(List<Guest> members) {
        this.members = members;
    }
}