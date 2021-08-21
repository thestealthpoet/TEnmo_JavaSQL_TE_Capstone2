package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class AccountJdbcDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public AccountJdbcDao(DataSource ds) {
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
    public void updateBalanceAfterTransfer(Transfer transfer) {
        BigDecimal fromBalance = new BigDecimal(0);
        BigDecimal toBalance = new BigDecimal(0);
        BigDecimal transferAmount = transfer.getTransferAmount();

        String sqlFrom = "SELECT balance FROM accounts WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlFrom, transfer.getAccountFromId());
        if (result.next()) {
            fromBalance = result.getBigDecimal("balance");
        }
        String sqlTo = "SELECT balance FROM accounts WHERE user_id = ?;";
        SqlRowSet result2 = jdbcTemplate.queryForRowSet(sqlTo, transfer.getAccountToId());
        if (result2.next()) {
            toBalance = result2.getBigDecimal("balance");
        }

        //fromBalance = fromBalance.subtract(transferAmount);
        //toBalance = toBalance.add(transferAmount);

        String updateFromBalance = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
        jdbcTemplate.update(updateFromBalance, fromBalance.subtract(transferAmount), transfer.getAccountFromId());

        String updateToBalance = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
        jdbcTemplate.update(updateToBalance, toBalance.add(transferAmount), transfer.getAccountToId());

    }


}


