package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
@Component
public class AccountJdbcDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;

    public AccountJdbcDAO(DataSource ds) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getBalance(String user) {
        Account account = new Account();


        return account;
    }
}
