package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    void sendTransfer(int accountFromId, int accountToId, BigDecimal transferAmount);

    List<Transfer> getTransferHistory(int userId);

    Transfer getTransferDetails(int userId);
}
