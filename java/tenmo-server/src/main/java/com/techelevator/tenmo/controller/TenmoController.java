package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TenmoController {

    @Autowired
    AccountDao dao;
    @Autowired
    UserDao userDao;
    @Autowired
    TransferDao transferDao;

    @RequestMapping(path="/balance/{userId}", method= RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int userId) {
        BigDecimal balance = dao.getBalance(userId);
        return balance;
    }

    @RequestMapping(path="/users/userinfo", method = RequestMethod.GET)
    public List<User> listUsersByUsernameAndUserId() {

        return userDao.listByUsernameAndUserId();
    }

    @RequestMapping(path="/users/{username}", method = RequestMethod.GET)
    public User findByUserName(@PathVariable String username) {
        return userDao.findByUsername(username);
    }

    @RequestMapping(path="/users", method = RequestMethod.GET)
    public List<User> listAllUsers() {
        return userDao.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path="/transfers", method = RequestMethod.POST)
    public void sendTransfer(@RequestBody Transfer transfer) {
        transferDao.sendTransfer(transfer);

    }
    @RequestMapping(path="/users/balance", method = RequestMethod.PUT)
    public void updateBalanceAfterTransfer(@RequestBody Transfer transfer) {
        dao.updateBalanceAfterTransfer(transfer);
    }

    @RequestMapping(path="transfers/history", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory(int userId) {


        return null;
    }




}
