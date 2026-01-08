import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<SavingsAccount> accounts = new ArrayList<>();

        SavingsAccount account1 = new SavingsAccount("12345", "Alice Johnson", 1000.0, 5.0);
        SavingsAccount account2 = new SavingsAccount("67890", "Bob Smith", 500.0, 4.0);
        accounts.add(account1);
        accounts.add(account2);

        SavingsAccount currentAccount = null;

        JOptionPane.showMessageDialog(null, "Welcome to the Java Banking System!");


        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getAccountHolderName() + " (Account: " + accounts.get(i).getAccountNumber() + ")");
        }
        System.out.print("Select an account (1-" + accounts.size() + "): ");
        int accountChoice = scanner.nextInt();
        if (accountChoice >= 1 && accountChoice <= accounts.size()) {
            currentAccount = accounts.get(accountChoice - 1);
            System.out.print("Enter PIN (default: 1234): ");
            String pin = scanner.next();
            if (!pin.equals("1234")) {
                System.out.println("Invalid PIN. Exiting.");
                scanner.close();
                return; 
            }
        
            System.out.println("Logged in as " + currentAccount.getAccountHolderName());
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
                        System.out.println((i + 1) + ". " + availableAccounts.get(i).getAccountHolderName() + " (Account: " + availableAccounts.get(i).getAccountNumber() + ")");
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
                    System.out.println("Thank you for using the Java Banking System!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}