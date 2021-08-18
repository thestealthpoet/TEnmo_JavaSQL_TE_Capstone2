package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TenmoController {

    @Autowired
    AccountDAO dao;
    @Autowired
    UserDao userDao;

    @RequestMapping(path="/balance/{userId}", method= RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int userId) {
        BigDecimal balance = dao.getBalance(userId);
        return balance;
    }

    @RequestMapping(path="/users", method = RequestMethod.GET)
    public List<String> listUsers () {

        return userDao.listByUsername();
    }




}
