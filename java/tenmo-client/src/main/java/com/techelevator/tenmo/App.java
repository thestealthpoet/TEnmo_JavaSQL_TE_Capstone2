package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountServices;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

	private static ConsoleService console;
	private static AuthenticatedUser currentUser;
	private static AuthenticationService authenticationService;
	Scanner input = new Scanner(System.in);

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	


    private RestTemplate restTemplate;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.restTemplate = new RestTemplate();
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	public static void mainMenu() {
		while(true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private static void viewCurrentBalance() {
		AccountServices accountServices = new AccountServices(API_BASE_URL, currentUser);
		System.out.println("Current TE Bucks balance: " + accountServices.getBalance() + " TE.X");
	}


	//This is now encapsulated within the AccountServices class
	/*private void viewCurrentBalance() {
		//System.out.println(currentUser.getToken());
		BigDecimal balance = new BigDecimal(0);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity((httpHeaders));

		balance = restTemplate.exchange(API_BASE_URL + "balance/" + currentUser.getUser().getId(), HttpMethod.GET, entity, BigDecimal.class).getBody();
		System.out.println(balance.toString() + " TE Bucks");

	}*/

	private static void viewTransferHistory() {
		// TODO Auto-generated method stub
		
	}

	private static void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private static void sendBucks() {
		TransferService transferService = new TransferService(API_BASE_URL, currentUser);
		transferService.transferFunds();


		/*System.out.print("How many TE Bucks would you like to send?>>>");
		BigDecimal transferAmount = new BigDecimal(0);
		transferAmount = input.nextBigDecimal();*/



		/*List<String> userList = new ArrayList<>();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity((httpHeaders));

		userList = restTemplate.exchange("http://localhost:8080/users", HttpMethod.GET, entity, List.class).getBody();
		System.out.println("****Available users to transfer TE bucks:****" + "\n");
		for (String username : userList){
			System.out.println(username);

		}
		System.out.println("\n");
		boolean shouldLoop = true;
		while (shouldLoop) {
		System.out.print("Please enter the username you wish to transfer TE bucks to: ");
		String username = input.nextLine();

			for (String u : userList) {
				if (u.equals(username)) {
					shouldLoop = false;
					System.out.println("You got a real user.");
					break;
				}
			}

		}
		System.out.println("Hopefully you find something");*/


			}


	//}

	private static void requestBucks() {
		// TODO Auto-generated method stub
		
	}
	
	private static void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private static void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private static UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
