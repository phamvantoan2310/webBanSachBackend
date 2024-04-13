package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.roleRepository;
import com.phamvantoan.webBanSachBackend.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class roleServiceImpl implements roleService{
    private roleRepository rolerepository;
    @Autowired
    public roleServiceImpl(roleRepository rolerepository){
        this.rolerepository = rolerepository;
    }
    @Override
    public Role findByRoleID(int roleID) {
        return this.rolerepository.findByRoleID(roleID);
    }
}
