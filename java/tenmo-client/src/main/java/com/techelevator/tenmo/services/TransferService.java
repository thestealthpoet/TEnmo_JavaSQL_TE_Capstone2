package com.techelevator.tenmo.services;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;
import com.techelevator.tenmo.App;

public class TransferService {
    private String baseUrl;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();
    private ConsoleService consoleService;
    private AuthenticationService authenticationService;


    public TransferService(String baseUrl, AuthenticatedUser currentUser) {
        this.baseUrl = baseUrl;
        this.currentUser = currentUser;
        this.restTemplate = restTemplate;
        this.consoleService = consoleService;
    }

    public User getUserFromChoiceForTransfer() {
        User recipient = null;
        ConsoleService consoleService = new ConsoleService(System.in, System.out);
        User[] users = null;
        System.out.println("Which user would you like to send TE Bucks to?>>>");
        users = restTemplate.exchange(baseUrl + "users/userinfo", HttpMethod.GET,
                makeAuthEntity(), User[].class).getBody();
        String[] userNames = new String[users.length];
        for (int i = 0; i < users.length; i++) {
            String name = users[i].getUsername();
            userNames[i] = name;
        }
        String choice = (String) consoleService.getChoiceFromOptions(userNames);
        recipient = restTemplate.exchange(baseUrl + "users/" + choice, HttpMethod.GET, makeAuthEntity(), User.class).getBody();

        return recipient;
    }

    public void transferFunds() {
        AccountServices accountServices = new AccountServices(baseUrl, currentUser);
        Scanner userInput = new Scanner(System.in);
        User recipient = getUserFromChoiceForTransfer();
        String choice = null;

        BigDecimal currentBalance = accountServices.getBalance();
        System.out.println("Your current balance is: " + currentBalance + " TE Bucks.");
        System.out.println("How many TE bucks would you like to send?>>>");
        String transferAmountString = userInput.nextLine();
        BigDecimal transferAmount = new BigDecimal(transferAmountString);
        BigDecimal bdZero = new BigDecimal(0);

        if (transferAmount.compareTo(currentBalance) == 1) {
            System.out.println("Insufficient funds.  Please check your balance.");
            transferFunds();
        } else if (transferAmount.compareTo(bdZero) == 0){
            System.out.println("You can't transfer 0 TE bucks.  Please try again.");
            transferFunds();
        } else {
            System.out.println("Please confirm transfer:");
            System.out.println("------------------------------------");
            System.out.println("Transfer: " + transferAmount + " TE.X");
            System.out.println("To: " + recipient.getUsername());
            System.out.print("(Y/N)>>>");
            choice = userInput.nextLine();
        }
        if (choice.toLowerCase().equals("n")) {
            System.out.println("Cancelling transfer. . . ");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("What?");

            }
            System.out.println("Returning to main menu. . .");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("What?");

            }
            App.mainMenu();


        } else {
            Transfer transfer = new Transfer(2, 2, currentUser.getUser().getId(), recipient.getId(), transferAmount);

            HttpEntity<Transfer> transferEntity = new HttpEntity<>(transfer, makeAuthEntity().getHeaders());

            //try {
                Transfer newTransfer = restTemplate.exchange(baseUrl + "transfers", HttpMethod.POST, transferEntity, Transfer.class).getBody();
            //} catch (Exception e) {
            //    System.out.println("Something went wrong.  Try again.");
            //    App.mainMenu();
           // }
            System.out.println("Successful Transfer!");
        }
    }


    private HttpEntity makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }

}
