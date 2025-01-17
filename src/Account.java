public class Account {
    private final String accountNumber;
    private final String cardNumber;
    private String cardPin;
    private final String currency;
    private double balance;
    private final String ownerName;
    private final String ownerSurname;
    private final String login;
    private final String password;
    private final String email;
    
    public Account(String accountNumber, String cardNumber,String cardPin, String currency, double balance, String ownerName, String ownerSurname, String login, String password, String email) {
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.cardPin = cardPin;
        this.currency = currency;
        this.balance = balance;
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.login = login;
        this.password = password;
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardPin() {
        return cardPin;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
        }
    }

    @Override
    public String toString() {
        return "Użytkownik: " + ownerName + " " + ownerSurname + ", Numer rachunku: " + accountNumber + ", Saldo: " + balance + " " + currency;
    }

    public void setCardPin(String cardPin) {
        this.cardPin = cardPin;
    }
}