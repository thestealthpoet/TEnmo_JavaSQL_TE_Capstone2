package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@Component
public class TransferJdbcDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;
    private AccountJdbcDao accountJdbcDao;

    public TransferJdbcDao(JdbcTemplate jdbcTemplate, AccountJdbcDao accountJdbcDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountJdbcDao = accountJdbcDao;
    }

    @Override
    public void sendTransfer(int accountFromId, int accountToId, BigDecimal transferAmount) {
        //Transfer transfer = null;
        String sql = "DELIMITER $$ CREATE TRIGGER update_accounts_after_transfer AFTER INSERT" +
                "ON transfers" +
                "FOR EACH ROW BEGIN" +
                "UPDATE accounts" +
                "SET balance = " + (accountJdbcDao.getBalance(accountFromId).subtract(transferAmount)) +
                "WHERE account_id = " + accountFromId +
                "UPDATE accounts" +
                "SET balance = " + (accountJdbcDao.getBalance(accountToId).add(transferAmount)) +
                "WHERE account_id = " + accountToId +
                "END $$" +
                "DELIMITER;" +
                "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), (SELECT account_id FROM accounts WHERE user_id = ?), ?;";

                jdbcTemplate.update(sql, accountFromId, accountToId, transferAmount);


    }

    @Override
    public List<Transfer> getTransferHistory(int userId) {
        return null;
    }

    @Override
    public Transfer getTransferDetails(int userId) {
        return null;
    }
}
