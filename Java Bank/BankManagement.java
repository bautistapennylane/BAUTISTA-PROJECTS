import java.nio.file.Files; 
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

// Abstract class representing a Bank Account
abstract class BankAccount {
    protected Path accountFilePath;

    public BankAccount(String filePath) {
        this.accountFilePath = Paths.get(filePath);
        initializeAccountFile();
    }

    // Abstract methods for deposit and withdrawal
    public abstract void deposit(double amount) throws IOException;
    public abstract void withdraw(double amount) throws IOException;

    // Method to get the current balance
    public double getBalance() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(accountFilePath)) {
            String content = reader.readLine();
            return Double.parseDouble(content.replace("Your balance is: ", "").trim());
        }
    }

    // Method to write balance to the file
    protected void updateBalance(double newBalance) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(accountFilePath, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("Your balance is: " + newBalance);
        }
    }

    // Method to initialize the account file with a default balance of 0.0
    private void initializeAccountFile() {
        try {
            if (!Files.exists(accountFilePath)) {
                try (BufferedWriter writer = Files.newBufferedWriter(accountFilePath, StandardOpenOption.CREATE)) {
                    writer.write("Your balance is: 0.0");
                }
            }
        } catch (IOException e) {
            System.err.println("Error initializing account file: " + e.getMessage());
        }
    }
}

// Concrete implementation of BankAccount
class ConcreteBankAccount extends BankAccount {

    public ConcreteBankAccount(String filePath) {
        super(filePath);
    }

    @Override
    public void deposit(double amount) throws IOException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        double currentBalance = getBalance();
        updateBalance(currentBalance + amount);
        System.out.println("Deposited: " + amount + ". New Balance: " + getBalance());
    }

    @Override
    public void withdraw(double amount) throws IOException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        double currentBalance = getBalance();
        if (amount > currentBalance) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        updateBalance(currentBalance - amount);
        System.out.println("Withdrew: " + amount + ". New Balance: " + getBalance());
    }
}

// Main class
public class BankManagement {
    public static void main(String[] args) {
        String filePath = "BankAccount.txt";
        BankAccount account = new ConcreteBankAccount(filePath);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Java Bank");
            System.out.println("************");
            System.out.println("[1] Withdraw");
            System.out.println("[2] Deposit");
            System.out.println("[3] Balance Inquiry");
            System.out.println("[4] Exit");
            System.out.println("************");
            System.out.print("Choose an option:");
			
            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        account.withdraw(withdrawAmount);
                        break;
                    case 2:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        break;
                    case 3:
                        double currentBalance = account.getBalance();
                        System.out.println("Your current balance is: " + currentBalance);
                        break;
                    case 4:
                        System.out.println("Exiting Java Bank!!!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (IOException e) {
                System.err.println("File operation error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }
}
