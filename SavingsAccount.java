import java.util.ArrayList;
import java.util.List;

public class SavingsAccount {
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