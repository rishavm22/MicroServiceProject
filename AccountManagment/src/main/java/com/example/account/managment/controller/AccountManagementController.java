package com.example.account.managment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountManagementController {

    @RequestMapping("/status/check")
    public String statusCheck() {
        return "AccountManagement is running";
    }
}
