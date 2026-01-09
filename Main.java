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

    public String getPassword() {
        return password;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposit: +" + amount);
            applyInterest(); // automatically apply interest after deposit
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add("Withdrawal: -" + amount);
            applyInterest(); // automatically apply interest after withdrawal
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public void checkBalance() {
        applyInterest(); // automatically apply interest before showing balance
        System.out.println("Current balance: " + balance);
    }

    public void applyInterest() {
        double interest = balance * (interestRate / 100);
        balance += interest;
        transactions.add("Interest applied: +" + interest);
        System.out.println("Interest applied automatically: " + interest);
    }

    public void transfer(SavingsAccount receiver, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            receiver.balance += amount;
            transactions.add("Transferred " + amount + " to " + receiver.getAccountHolderName());
            receiver.transactions.add("Received " + amount + " from " + accountHolderName);
            applyInterest();       // apply interest to sender
            receiver.applyInterest(); // apply interest to receiver
            System.out.println("Transferred " + amount + " to " + receiver.getAccountHolderName());
        } else {
            System.out.println("Invalid transfer amount or insufficient funds.");
        }
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
            System.out.print("Enter interest rate (e.g., 5 for 5%): ");
            double interestRate = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Set a password for your account: ");
            String password = scanner.nextLine();
            String accountNumber = String.valueOf(10000 + accounts.size() + 1);
            currentAccount = new SavingsAccount(accountNumber, name, initialDeposit, interestRate, password);
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
                currentAccount.applyInterest(); // automatically apply interest on login
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
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
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
                case 4 -> {
                    System.out.println("Available accounts:");
                    for (SavingsAccount acc : accounts) {
                        if (!acc.equals(currentAccount)) {
                            System.out.println(acc.getAccountHolderName() + " (Account: " + acc.getAccountNumber() + ")");
                        }
                    }
                    System.out.print("Enter receiver account number: ");
                    String accNumber = scanner.next();
                    SavingsAccount receiver = null;
                    for (SavingsAccount acc : accounts) {
                        if (acc.getAccountNumber().equals(accNumber)) {
                            receiver = acc;
                            break;
                        }
                    }
                    if (receiver != null) {
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        currentAccount.transfer(receiver, transferAmount);
                    } else {
                        System.out.println("Receiver account not found.");
                    }
                }
                case 5 -> currentAccount.printTransactionHistory();
                case 6 -> System.out.println("Thank you for using the Java Banking System!");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 6);

        scanner.close(); // close scanner at the very end
    }
}