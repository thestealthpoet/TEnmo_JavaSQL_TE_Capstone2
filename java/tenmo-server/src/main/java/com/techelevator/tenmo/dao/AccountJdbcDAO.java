package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class AccountJdbcDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;

    public AccountJdbcDAO(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public BigDecimal getBalance(int userId) {
        BigDecimal balance = null;
        String sql = "SELECT balance FROM accounts WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        if (result.next()) {
            balance = result.getBigDecimal("balance");
        }

        return balance;
    }

    @Override
    public BigDecimal addTransferAmount(BigDecimal transferAmount, int userId) {
        return null;
    }

    @Override
    public BigDecimal subtractFromBalance(BigDecimal transferAmount, int userId) {
        return null;
    }
}
