import java.util.Scanner;

// Account class to represent bank accounts
class Account {
    private int accNumber;
    private double balance;
    private String transactionHistory;
    private int pin;

    public Account(int accNumber, double balance, int pin) {
        this.accNumber = accNumber;
        this.balance = balance;
        this.transactionHistory = "";
        this.pin = pin;
    }

    public int getAccNumber() {
        return accNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory += "Deposit: " + amount + "\n";
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory += "Withdrawal: " + amount + "\n";
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setTransactionHistory(String transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public String getTransactionHistory() {
        return transactionHistory;
    }

    public boolean checkPin(int pin) {
        return this.pin == pin;
    }
}

// ATM class to perform transactions
class ATM {
    private Account[] accounts;

    public ATM(Account[] accounts) {
        this.accounts = accounts;
    }

    public void depositToAccount(int accNumber, double amount) {
        for (Account account : accounts) {
            if (account != null && account.getAccNumber() == accNumber) {
                account.deposit(amount);
                System.out.println("Deposit successful");
                return;
            }
        }
        System.out.println("Account not found");
    }

    public void withdrawFromAccount(int accNumber, double amount, int pin) {
        for (Account account : accounts) {
            if (account != null && account.getAccNumber() == accNumber && account.checkPin(pin)) {
              if(account.getBalance()>=amount){  account.withdraw(amount);
                System.out.println("Withdrawal successful");
                return;
			  }
			  else{
			  System.out.println("Insufficient Balance");
			  return;
			  }
            }
        }
        System.out.println("Account not found or incorrect PIN");
    }

    public void checkBalance(int accNumber, int pin) {
        for (Account account : accounts) {
            if (account != null && account.getAccNumber() == accNumber && account.checkPin(pin)) {
                System.out.println("Account balance: " + account.getBalance());
                return;
            }
        }
        System.out.println("Account not found or incorrect PIN");
    }

    public void transferFunds(int fromAccNumber, int toAccNumber, double amount, int pin) {
        for (Account account : accounts) {
            if (account != null && account.getAccNumber() == fromAccNumber && account.checkPin(pin)) {
                if (account.getBalance() >= amount) {
                    account.withdraw(amount);
                    System.out.println("Transfer successful");
                    for (Account toAccount : accounts) {
                        if (toAccount != null && toAccount.getAccNumber() == toAccNumber) {
                            toAccount.deposit(amount);
                            return;
                        }
                    }
                    System.out.println("Destination account not found");
                } else {
                    System.out.println("Insufficient funds");
                }
                return;
            }
        }
        System.out.println("Account not found or incorrect PIN");
    }

    public void printTransactionHistory(int accNumber, int pin) {
        for (Account account : accounts) {
            if (account != null && account.getAccNumber() == accNumber && account.checkPin(pin)) {
                System.out.println("Transaction history: " + account.getTransactionHistory());
                return;
            }
        }
        System.out.println("Account not found or incorrect PIN");
    }
}

// Main class to demonstrate the ATM system
class Main {
    public static void main(String[] args) {
        Account[] accounts = new Account[10]; // Array to store accounts

        // Initialize accounts
        accounts[0] = new Account(101, 5000, 1234);
        accounts[1] = new Account(102, 3000, 5678);
        // ...

        ATM atm = new ATM(accounts);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the ATM");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check balance");
            System.out.println("4. Transfer funds");
            System.out.println("5. Print transaction history");
            System.out.println("6. Change PIN");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    int accNumber = scanner.nextInt();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    atm.depositToAccount(accNumber, depositAmount);
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    accNumber = scanner.nextInt();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    System.out.print("Enter PIN: ");
                    int pin = scanner.nextInt();
                    atm.withdrawFromAccount(accNumber, withdrawalAmount, pin);
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    accNumber = scanner.nextInt();
                    System.out.print("Enter PIN: ");
                    pin = scanner.nextInt();
                    atm.checkBalance(accNumber, pin);
                    break;
                case 4:
                    System.out.print("Enter account number to transfer from: ");
                    int fromAccNumber = scanner.nextInt();
                    System.out.print("Enter account number to transfer to: ");
                    int toAccNumber = scanner.nextInt();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    System.out.print("Enter PIN: ");
                    pin = scanner.nextInt();
                    atm.transferFunds(fromAccNumber, toAccNumber, transferAmount, pin);
                    break;
                case 5:
                    System.out.print("Enter account number: ");
                    accNumber = scanner.nextInt();
                    System.out.print("Enter PIN: ");
                    pin = scanner.nextInt();
                    atm.printTransactionHistory(accNumber, pin);
                    break;
                case 6:
                    System.out.print("Enter account number: ");
                    accNumber = scanner.nextInt();
                    System.out.print("Enter current PIN: ");
                    int currentPin = scanner.nextInt();
                    for (Account account : accounts) {
                        if (account != null && account.getAccNumber() == accNumber && account.checkPin(currentPin)) {
                            System.out.print("Enter new PIN: ");
                            int newPin = scanner.nextInt();
                            account.setPin(newPin);
                            System.out.println("PIN changed successfully");
                            return;
                        }
                    }
                    System.out.println("Account not found or incorrect PIN");
                    break;
                case 7:
                    System.out.println("Thank you for using the ATM");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}