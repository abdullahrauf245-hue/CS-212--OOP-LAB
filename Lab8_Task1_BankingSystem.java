/* Lab 8 Task 1: Banking System - Hierarchical Inheritance
UML:
BankAccount (base)
├── SavingsAccount (interestRate, calculateInterest)
└── CurrentAccount (overdraftLimit)
*/

class BankAccount {
    protected String accountNumber;
    protected String holderName;
    protected double balance;
    
    public BankAccount(String accNo, String name, double bal) {
     accountNumber = accNo;
        holderName = name;
        balance = bal;
    }
    
    public void deposit(double amount) {
        balance += amount;
   }
    
    public void withdraw(double amount) {
        if (amount <= balance)
            balance -= amount;
        else
            System.out.println("Insufficient balance");
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String toString() {
        return holderName + " (" + accountNumber + "): $" + balance;
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;
    
    public SavingsAccount(String accNo, String name, double bal, double rate) {
        super(accNo, name, bal);
        interestRate = rate;
    }
    
    public void calculateInterest() {
        balance += balance * interestRate;
    }
    
    public String toString() {
        return super.toString() + " [Savings " + interestRate*100 + "%]";
    }
}

class CurrentAccount extends BankAccount {
    private double overdraftLimit;
    
    public CurrentAccount(String accNo, String name, double bal, double limit) {
        super(accNo, name, bal);
        overdraftLimit = limit;
    }
    
    public void withdraw(double amount) {
        if (amount <= balance + overdraftLimit)
            balance -= amount;
        else
            System.out.println("Overdraft limit exceeded");
    }
    
    public String toString() {
        return super.toString() + " [Current]";
    }
}

public class Lab8_Task1_BankingSystem {
    public static void main(String[] args) {
        BankAccount[] accounts = {
            new SavingsAccount("SAV001", "Alice", 1000, 0.05),
            new SavingsAccount("SAV002", "Bob", 2000, 0.04),
            new CurrentAccount("CUR001", "Charlie", 1500, 500)
        };
        
        // Deposits and withdrawals
        accounts[0].deposit(500);
        accounts[1].deposit(300);
        accounts[2].withdraw(200);
        
        // Calculate interest for savings only (polymorphism)
        for (BankAccount acc : accounts) {
            if (acc instanceof SavingsAccount) {
                ((SavingsAccount)acc).calculateInterest();
            }
        }
        
        // Find highest balance and total
        double total = 0;
        BankAccount highest = accounts[0];
        for (BankAccount acc : accounts) {
            System.out.println(acc);
            total += acc.getBalance();
            if (acc.getBalance() > highest.getBalance())
                highest = acc;
        }
        
        System.out.println("\nHighest balance: " + highest);
        System.out.println("Total bank balance: $" + total);
    }
}
