import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SavingsAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private double interestRate;
    private String password;
    private List<String> transactions;

    public SavingsAccount(String accountNumber, String accountHolderName, double balance, double interestRate, String password) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.interestRate = interestRate;
        this.password = password;
        this.transactions = new ArrayList<>();
        applyInterest(); // Apply interest automatically when account is created
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposit: +" + amount);
            applyInterest();
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add("Withdrawal: -" + amount);
            applyInterest();
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public void checkBalance() {
        applyInterest();
        System.out.println("Current balance: " + balance);
    }

    public void applyInterest() {
        double interest = balance * (interestRate / 100);
        balance += interest;
        transactions.add("Interest applied: +" + interest);
        System.out.println("Interest applied automatically: " + interest);
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for " + accountHolderName + ":");
        for (String t : transactions) {
            System.out.println(t);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<SavingsAccount> accounts = new ArrayList<>();
        // Default accounts with default interest rates
        accounts.add(new SavingsAccount("12345", "Alice Johnson", 1000.0, 5.0, "1234"));
        accounts.add(new SavingsAccount("67890", "Bob Smith", 500.0, 4.0, "1234"));

        SavingsAccount currentAccount = null;

        System.out.println("Welcome to our Java Banking System!");
        System.out.println("Do you want to:");
        System.out.println("1. Log in to an existing account");
        System.out.println("2. Create a new account");
        System.out.print("Enter choice (1-2): ");
        int initialChoice = scanner.nextInt();
        scanner.nextLine();

        if (initialChoice == 2) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter initial deposit: ");
            double initialDeposit = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Set a password for your account: ");
            String password = scanner.nextLine();
            String accountNumber = String.valueOf(10000 + accounts.size() + 1);
            // Use a default interest rate of 5% for new accounts
            currentAccount = new SavingsAccount(accountNumber, name, initialDeposit, 5.0, password);
            accounts.add(currentAccount);
            System.out.println("Account created successfully!");
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Logged in as " + name);
        } else if (initialChoice == 1) {
            System.out.println("Available accounts:");
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println((i + 1) + ". " + accounts.get(i).getAccountHolderName()
                        + " (Account: " + accounts.get(i).getAccountNumber() + ")");
            }
            System.out.print("Select an account (1-" + accounts.size() + "): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice > 0 && choice <= accounts.size()) {
                currentAccount = accounts.get(choice - 1);
                currentAccount.applyInterest();
                System.out.println("Logged in as " + currentAccount.getAccountHolderName());
            } else {
                System.out.println("Invalid selection. Exiting.");
                scanner.close();
                return;
            }
        }

        int option;
        do {
            System.out.println("\nSelect an option:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> currentAccount.checkBalance();
                case 2 -> {
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    currentAccount.deposit(depositAmount);
                }
                case 3 -> {
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    currentAccount.withdraw(withdrawAmount);
                }
                case 4 -> currentAccount.printTransactionHistory();
                case 5 -> System.out.println("Thank you for using the Java Banking System!");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 5);

        scanner.close(); // close scanner
    }
}