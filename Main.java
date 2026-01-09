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
            applyInterest();
            receiver.applyInterest();
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
            int accountChoice = scanner.nextInt();
            scanner.nextLine();
            if (accountChoice >= 1 && accountChoice <= accounts.size()) {
                currentAccount = accounts.get(accountChoice - 1);
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                if (!currentAccount.getPassword().equals(password)) {
                    System.out.println("Invalid password. Exiting.");
                    scanner.close();
                    return;
                }
                System.out.println("Logged in as " + currentAccount.getAccountHolderName());
            } else {
                System.out.println("Invalid choice. Exiting.");
                scanner.close();
                return;
            }
        } else {
            System.out.println("Invalid choice. Exiting.");
            scanner.close();
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    currentAccount.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    currentAccount.withdraw(withdrawAmount);
                    break;
                case 3:
                    currentAccount.checkBalance();
                    break;
                case 4:
                    System.out.println("Available accounts for transfer:");
                    for (int i = 0; i < accounts.size(); i++) {
                        if (!accounts.get(i).equals(currentAccount)) {
                            System.out.println((i + 1) + ". " + accounts.get(i).getAccountHolderName()
                                    + " (Account: " + accounts.get(i).getAccountNumber() + ")");
                        }
                    }
                    System.out.print("Select account to transfer to: ");
                    int targetIndex = scanner.nextInt() - 1;
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    if (targetIndex >= 0 && targetIndex < accounts.size() && !accounts.get(targetIndex).equals(currentAccount)) {
                        currentAccount.transfer(accounts.get(targetIndex), transferAmount);
                    } else {
                        System.out.println("Invalid target account.");
                    }
                    break;
                case 5:
                    currentAccount.printTransactionHistory();
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using our banking system!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}