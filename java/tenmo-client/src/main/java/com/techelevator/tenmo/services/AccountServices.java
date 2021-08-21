
package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountServices {
    private String baseUrl;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountServices(String baseUrl, AuthenticatedUser currentUser) {
        this.baseUrl = baseUrl;
        this.currentUser = currentUser;

    }

    public BigDecimal getBalance() {
        BigDecimal balance = new BigDecimal(0);
        balance = restTemplate.exchange(baseUrl + "balance/" + currentUser.getUser().getId(), HttpMethod.GET, makeAuthEntity(), BigDecimal.class).getBody();

        return balance;
    }

    public void updateBalance(Transfer transfer) {
        //System.out.println(transfer.toString());
        //HttpEntity<Transfer> transferEntity = new HttpEntity<>(transfer, makeAuthEntity().getHeaders());
       // try {
            restTemplate.exchange(baseUrl + "users/balance/", HttpMethod.PUT, makeTransferEntity(transfer), Transfer.class).getBody();
        //} catch (Exception e) {

        //}
    }


    private HttpEntity makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<Transfer> transferHttpEntity = new HttpEntity<>(transfer, headers);
        return transferHttpEntity;
    }
}


