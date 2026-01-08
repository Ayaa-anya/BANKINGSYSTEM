import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<SavingsAccount> accounts = new ArrayList<>();

        // Existing accounts with default password
        SavingsAccount account1 = new SavingsAccount("12345", "Alice Johnson", 1000.0, 5.0, "1234");
        SavingsAccount account2 = new SavingsAccount("67890", "Bob Smith", 500.0, 4.0, "1234");
        accounts.add(account1);
        accounts.add(account2);

        SavingsAccount currentAccount = null;

        System.out.println("Welcome to our Java Banking System!");

        // Option to create a new account or log in
        System.out.println("Do you want to:");
        System.out.println("1. Log in to an existing account");
        System.out.println("2. Create a new account");
        System.out.print("Enter choice (1-2): ");
        int initialChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (initialChoice == 2) {
            // Create a new account
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter initial deposit: ");
            double initialDeposit = scanner.nextDouble();
            System.out.print("Enter interest rate (e.g., 5 for 5%): ");
            double interestRate = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            System.out.print("Set a password for your account: ");
            String password = scanner.nextLine();

            // Simple account number generation
            String accountNumber = String.valueOf(10000 + accounts.size() + 1);

            // Create the new account and add to the list
            SavingsAccount newAccount = new SavingsAccount(accountNumber, name, initialDeposit, interestRate, password);
            accounts.add(newAccount);
            currentAccount = newAccount;

            System.out.println("Account created successfully!");
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Logged in as " + name);

        } else if (initialChoice == 1) {
            // Existing login
            System.out.println("Available accounts:");
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println((i + 1) + ". " + accounts.get(i).getAccountHolderName()
                        + " (Account: " + accounts.get(i).getAccountNumber() + ")");
            }
            System.out.print("Select an account (1-" + accounts.size() + "): ");
            int accountChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

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

        // Main menu (unchanged)
        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Apply Interest");
            System.out.println("5. Transfer Money");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

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
                    currentAccount.applyInterest();
                    break;
                case 5:
                    List<SavingsAccount> availableAccounts = new ArrayList<>();
                    for (SavingsAccount acc : accounts) {
                        if (!acc.getAccountNumber().equals(currentAccount.getAccountNumber())) {
                            availableAccounts.add(acc);
                        }
                    }
                    if (availableAccounts.isEmpty()) {
                        System.out.println("No other accounts available for transfer.");
                        break;
                    }
                    System.out.println("Available accounts to transfer to:");
                    for (int i = 0; i < availableAccounts.size(); i++) {
                        System.out.println((i + 1) + ". " + availableAccounts.get(i).getAccountHolderName()
                                + " (Account: " + availableAccounts.get(i).getAccountNumber() + ")");
                    }
                    System.out.print("Select account to transfer to (1-" + availableAccounts.size() + "): ");
                    int transferChoice = scanner.nextInt();
                    if (transferChoice >= 1 && transferChoice <= availableAccounts.size()) {
                        SavingsAccount targetAccount = availableAccounts.get(transferChoice - 1);
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        if (transferAmount > 0 && transferAmount <= currentAccount.getBalance()) {
                            currentAccount.withdraw(transferAmount);
                            targetAccount.deposit(transferAmount);
                            System.out.println("Transfer successful.");
                        } else {
                            System.out.println("Invalid transfer amount or insufficient funds.");
                        }
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                case 6:
                    currentAccount.displayTransactionHistory();
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using our Java Banking System!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
