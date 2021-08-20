package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    BigDecimal getBalance(int userId);
    BigDecimal addTransferAmount(BigDecimal transferAmount, int userId);
    BigDecimal subtractFromBalance(BigDecimal transferAmount, int userId);



}
