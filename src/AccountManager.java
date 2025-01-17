import java.util.ArrayList;
import java.util.List;

public class AccountManager {

    private final List<Account> accounts = new ArrayList<>();

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account getAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public void displayAccounts() {
        for (Account account : accounts) {
            System.out.println("Numer rachunku: " + account.getAccountNumber());
            System.out.println("Numer karty: " + account.getCardNumber());
            System.out.println("Numer PIN: " + account.getCardPin());
            System.out.println("Saldo: " + account.getBalance() + " " + account.getCurrency());
            System.out.println("-------------------------");
        }
    }
}