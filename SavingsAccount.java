import java.util.ArrayList;
import java.util.List;

public class SavingsAccount extends BankAccount {
    private double interestRate;
    private String password;  // Added password field
    private List<String> transactionHistory; // Added transaction history

    // Updated constructor to include password
    public SavingsAccount(String accountNumber, String accountHolderName, double initialBalance, double interestRate, String password) {
        super(accountNumber, accountHolderName, initialBalance);
        this.interestRate = interestRate;
        this.password = password;
        this.transactionHistory = new ArrayList<>();
    }

    // Getter and setter for interest rate
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Deposit method with transaction history
    public void deposit(double amount) {
        if (amount > 0) {
            setBalance(getBalance() + amount);
            transactionHistory.add("Deposited: $" + amount);
            System.out.println("Deposited $" + amount + " successfully.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Withdraw method with transaction history
    public void withdraw(double amount) {
        if (amount > 0 && amount <= getBalance()) {
            setBalance(getBalance() - amount);
            transactionHistory.add("Withdrew: $" + amount);
            System.out.println("Withdrew $" + amount + " successfully.");
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    // Check balance
    public void checkBalance() {
        System.out.println("Current balance: $" + getBalance());
    }

    // Apply interest (kept your original logic but adds transaction history)
    public void applyInterest() {
        double interest = getBalance() * (interestRate / 100);
        deposit(interest); // deposit method will also record transaction
        System.out.println("Interest applied: $" + interest + " at " + interestRate + "% rate.");
    }

    // Display transaction history
    public void displayTransactionHistory() {
        System.out.println("Transaction History for " + getAccountHolderName() + ":");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}
