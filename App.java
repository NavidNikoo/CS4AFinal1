import java.util.*;
import java.text.*;

interface SavingsAccount {
    // Constants for interest rate and account limits.
    final double rate = 0.04, limit = 10000, limit1 = 200;

    // Abstract methods for depositing and withdrawing money.
    void deposit(double n, Date d);
    void withdraw(double n, Date d);
}

class Customer implements SavingsAccount {

    String username, password, name, address, phone;
    double balance;

    // List to store transaction history.
    ArrayList<String> transactions;

    Customer(String username, String password, String name, String address, String phone, double balance, Date date) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.balance = balance;

        // Initializing the transactions list and adding the initial deposit transaction.
        transactions = new ArrayList<String>(5);
        addTransaction(String.format("Initial deposit - " + NumberFormat.getCurrencyInstance().format(balance) + " as on " + "%1$tD" + " at " + "%1$tT.", date));
    }

    void update(Date date) {
        // If balance is greater than or equal to the limit, apply interest.
        if (balance >= 10000) {
            balance += rate * balance;
        } else if(balance <= 200){
            // If balance is less than the limit, apply a penalty.
            balance -= (int) (balance / 100.0);
        }

        // Record the account update transaction.
        addTransaction(String.format("Account updated. Balance - " + NumberFormat.getCurrencyInstance().format(balance) + " as on " + "%1$tD" + " at " + "%1$tT.", date));
    }

    @Override
    public void deposit(double amount, Date date) {

        balance += amount;
        addTransaction(String.format(NumberFormat.getCurrencyInstance().format(amount) + " credited to your account. Balance - " + NumberFormat.getCurrencyInstance().format(balance) + " as on " + "%1$tD" + " at " + "%1$tT.", date));         // Recording the deposit transaction.

    }

    @Override
    public void withdraw(double amount, Date date) {

        if (amount > (balance - 200)) { //if, the withdrawal amount is more than the available balance minus the minimum required balance (200)
            System.out.println("Insufficient balance!"); // then, the balance is insufficient for the withdrawal, print a message and exit the method
            return;
        }

        //else, deduct the amount from the balance.
        balance -= amount;
        
        // Record the withdrawal transact ion with formatted date and time.
        addTransaction(String.format(NumberFormat.getCurrencyInstance().format(amount) + " debited from your account. Balance - " + NumberFormat.getCurrencyInstance().format(balance) + " as on " + "%1$tD" + " at " + "%1$tT.", date));
    }

    // Private method to add a transaction message to the transactions list.
    private void addTransaction(String message) {

        transactions.add(0, message);
        // If the list size exceeds 5, remove the oldest transaction to maintain a list of the last 5 transactions.
        if (transactions.size() > 5) {
            transactions.remove(5);
            transactions.trimToSize();
        }
    }
}

class Bank {
    // A map to store customer information, with the customer's username as the key
    Map<String, Customer> customerMap;

    // Constructor of the Bank class
    Bank() {
        // Initializing the customerMap as a new HashMap
        customerMap = new HashMap<String, Customer>();
    }

    
    public static void main(String[] args) { // Main method 

        
        Scanner sc = new Scanner(System.in);
        Customer customer;
        String username, password;
        double amount;

        // Creating a new instance of Bank
        Bank bank = new Bank();
        // Variable to store the user's menu choice
        int choice;

        // Label for the outer while loop for menu navigation
        outer: while (true) {
            // Displaying the main menu options
            System.out.println("\n-------------------");
            System.out.println("BANK OF 4A");
            System.out.println("-------------------\n");
            System.out.println("1. Register account.");
            System.out.println("2. Login.");
            System.out.println("3. Update accounts.");
            System.out.println("4. Exit.");
            System.out.print("\nEnter your choice: ");
            // Reading the user's choice
            choice = sc.nextInt();
            sc.nextLine();

            // Switch statement to handle different menu options
            switch (choice) {
                case 1:
                    // Register account option
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter address: ");
                    String address = sc.nextLine();
                    System.out.print("Enter contact number: ");
                    String phone = sc.nextLine();
                    System.out.println("Set username: ");
                    username = sc.next();
                    // Checking if the username already exists in the customerMap
                    while (bank.customerMap.containsKey(username)) {
                        System.out.println("Username already exists. Set again: ");
                        username = sc.next();
                    }
                    System.out.println("Set a password (minimum 8 chars; minimum 1 digit, 1 lowercase, 1 uppercase, 1 special character[!@#$%^&*_]): ");
                    password = sc.next();
                    sc.nextLine();
                    // Validating the password format
                    while (!password.matches(("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_]).{8,}"))) {
                        System.out.println("Invalid password condition. Set again: ");
                        password = sc.next();
                    }
                    System.out.print("Enter initial deposit: ");
                    sc.nextLine();
                    // Validating the deposit amount
                    while (!sc.hasNextDouble()) {
                        System.out.println("Invalid amount. Enter again: ");
                        sc.nextLine();
                    }
                    amount = sc.nextDouble();
                    sc.nextLine();
                    // Creating a new Customer object and adding it to the customerMap
                    customer = new Customer(username, password, name, address, phone, amount, new Date());
                    bank.customerMap.put(username, customer);
                    break;

				case 2:
                // Prompting the user to enter their username
                  System.out.println("Enter username: ");
                  username = sc.next();
                  sc.nextLine();  // Consuming the leftover newline character

                  // Prompting the user to enter their password
                  System.out.println("Enter password: ");
                 password = sc.next();
                  sc.nextLine();  // Consuming the leftover newline character

                  // Checking if the entered username exists in the customerMap
                  if (bank.customerMap.containsKey(username)) {
                   // Retrieving the customer object associated with the username
                   customer = bank.customerMap.get(username);

               
                   if (customer.password.equals(password)){//if, the entered password matches the customer's password
                //Sub-Menu
                while (true) {
                //Displaying options for the logged-in user
                System.out.println("\n-------------------");
                System.out.println("W E L C O M E");
                System.out.println("-------------------\n");
                System.out.println("1. Deposit.");
                System.out.println("2. Transfer.");
                System.out.println("3. Last 5 transactions.");
                System.out.println("4. User information.");
                System.out.println("5. Log out.");
                System.out.print("\nEnter your choice: ");
                
                // Reading the user's choice for the sub-menu
                choice = sc.nextInt();
				sc.nextLine(); //gets rid of leftover newline character

            switch(choice) {
             case 1:            // Deposit
                System.out.print("Enter amount: ");

        while (!sc.hasNextDouble()) {
            System.out.println("Invalid amount. Enter again: ");
            sc.nextLine(); // Clearing the invalid input
        }

        amount = sc.nextDouble(); // Reading the valid deposit amount
        sc.nextLine();
        customer.deposit(amount, new Date()); // Performing the deposit operation
        break;

        case 2:

        System.out.print("Enter payee username: ");
        username = sc.next(); // Reading the payee's username
        sc.nextLine(); // Consuming the leftover newline character
        System.out.println("Enter amount: ");
        // Validating that the entered amount is a double

        while (!sc.hasNextDouble()) {
            System.out.println("Invalid amount. Enter again: ");
            sc.nextLine(); // Clearing the invalid input
        }

        amount = sc.nextDouble(); // Reading the transfer amount
        sc.nextLine(); // Consuming the leftover newline character

        if (amount > 300000) { 
            System.out.println("Transfer limit exceeded. Contact bank manager.");
            break; 
        }

        if (bank.customerMap.containsKey(username)) {
            Customer payee = bank.customerMap.get(username); // Retrieving the payee's account
            payee.deposit(amount, new Date()); // Depositing the amount to the payee's account
            customer.withdraw(amount, new Date()); // Withdrawing the amount from the customer's account
        } else {
            System.out.println("Username doesn't exist."); // Informing if the payee's username is not found
        }
        break;

         case 3:

        // Option for displaying the last 5 transactions
        for (String transaction : customer.transactions) {
            System.out.println(transaction); // Printing each transaction
        }
        break;
        
	case 4:
	     System.out.println("Accountholder name : " +customer.name);
		 System.out.println("Accountholder address : " +customer.address);
		 System.out.println("Accountholder contact : " +customer.phone);
		  break;
	case 5:
		continue outer;
		default:
		System.out.println("Wrong choice !");
        }
    }
}
			else{
			System.out.println("Wrong username/password!");}
					}
					else
					{
						System.out.println("Wrong username/password!");
					}
					break;

	case 3:
		System.out.println("Enter username : ");
		username = sc.next();
		if(bank.customerMap.containsKey(username)){
		bank.customerMap.get(username).update(new Date());
		}
		else{
		    System.out.println("Username doesn't exist!");
		}
		break;

		case 4:
			System.out.println("\nThank you for choosing Bank Of 4A!"); 
			System.exit(1);
			break;
			default:
				System.out.println("Wrong choice !");
			}
		}
	}
}

