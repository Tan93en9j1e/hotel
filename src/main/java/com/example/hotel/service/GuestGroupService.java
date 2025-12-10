// com.example.hotel.service.GuestGroupService.java
package com.example.hotel.service;

import com.example.hotel.model.GuestGroup;
import com.example.hotel.repository.GuestGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestGroupService {

    @Autowired
    private GuestGroupRepository groupRepository;

    public List<GuestGroup> getAllGroups() {
        return groupRepository.findAll();
    }

    public Optional<GuestGroup> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public GuestGroup saveGroup(GuestGroup group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}