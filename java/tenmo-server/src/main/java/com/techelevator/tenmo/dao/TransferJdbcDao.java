package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransferJdbcDao implements TransferDao {

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
        List<Transfer> transferHistory = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount\n" +
                "FROM transfers\n" +
                "JOIN accounts ON transfers.account_from = accounts.account_id\n" +
                "JOIN users ON accounts.user_id = users.user_id\n" +
                "WHERE users.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transferHistory.add(transfer);
        }
            return transferHistory;
    }



    /*public List<Transfer> getTransferHistory(int userId) {
        List<Transfer> transferHistory = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_desc, transfer_status_desc, account_from, " +
                "account_to, amount " +
                "FROM transfers " +
                "JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id " +
                "JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id " +
                "JOIN accounts ON transfers.account_from = accounts.account_id " +
                "JOIN users ON accounts.user_id = users.user_id " +
                "WHERE account_from = ? OR account_to = ? ORDER BY transfer_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);

        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transferHistory.add(transfer);

        }
        return transferHistory;
    }*/

    @Override
    public Transfer getTransferDetails(int transferId) {
        Transfer fetchedTransfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount\n" +
                "FROM transfers\n" +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        while(results.next()) {
            fetchedTransfer = mapRowToTransfer(results);
        }
        return fetchedTransfer;
    }


    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferTypeId(results.getInt("transfer_type_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_id"));
        transfer.setAccountFromId(results.getInt("account_from"));
        transfer.setAccountToId(results.getInt("account_to"));
        transfer.setTransferAmount(results.getBigDecimal("amount"));
        /*transfer.setUserFrom(results.getString("username"));
        transfer.setUserTo(results.getString("username"));*/

        return transfer;
    }
}
