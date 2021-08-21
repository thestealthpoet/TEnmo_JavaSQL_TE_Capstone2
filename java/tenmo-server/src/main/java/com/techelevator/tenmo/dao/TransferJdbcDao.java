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
    public void sendTransfer(Transfer transfer) {
        //Transfer transfer = null;
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), (SELECT account_id FROM accounts WHERE user_id = ?), ?);";


                jdbcTemplate.update(sql, transfer.getAccountFromId(), transfer.getAccountToId(), transfer.getTransferAmount());


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
