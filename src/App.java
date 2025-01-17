// Wykorzystanie bibliotek Java do stworzenia aplikacji bankowej
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    // Mapa przechowująca kursy wymiany walut
    private static final Map<String, Double> exchangeRates = new HashMap<>();
    
    // Dodanie kursów wymiany walut
    static {
        exchangeRates.put("PLN-USD", 0.25);
        exchangeRates.put("USD-PLN", 4.00);
        exchangeRates.put("PLN-EUR", 0.22);
        exchangeRates.put("EUR-PLN", 4.50);
        exchangeRates.put("PLN-CHF", 0.24);
        exchangeRates.put("CHF-PLN", 4.20);
        exchangeRates.put("PLN-GBP", 0.19);
        exchangeRates.put("GBP-PLN", 5.30);
        exchangeRates.put("USD-EUR", 0.88);
        exchangeRates.put("EUR-USD", 1.14);
        exchangeRates.put("USD-CHF", 0.96);
        exchangeRates.put("CHF-USD", 1.04);
        exchangeRates.put("USD-GBP", 0.76);
        exchangeRates.put("GBP-USD", 1.32);
        exchangeRates.put("EUR-CHF", 1.09);
        exchangeRates.put("CHF-EUR", 0.92);
        exchangeRates.put("EUR-GBP", 0.85);
        exchangeRates.put("GBP-EUR", 1.18);
        exchangeRates.put("CHF-GBP", 0.82);
        exchangeRates.put("GBP-CHF", 1.22);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateFormat.setLenient(false);

            // Deklaracja zmiennych przechowujących dane użytkownika
            String Username = "";
            String Surname = "";
            String Login = "";
            String Email = "";
            String Address;
            String Birth;
            String Pesel;
            String Password = "";
            @SuppressWarnings("unused")
            String AccountNumber;
            String cardNumber = "";
            String cardPin = "";
            String LoginAdmin = "admin";
            String PasswordAdmin = "admin";

            // Inicjalizacja menedżera rachunków
            AccountManager accountManager = new AccountManager();

            // Metoda do czyszczenia konsoli
            Runnable clearConsole = () -> {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            };

            clearConsole.run();

            // Przywitanie użytkownika
            System.out.println("Witaj w banku PB Bank!"); 
            while (true) {
                // Menu główne aplikacji
                System.out.println("Wybierz opcję: 1 - Obsługa bankomatów, 2 - bankowość online, 3 - Wyjście, 4 - Obsługa banku");
                String AppOption = scanner.nextLine();
    
                switch (AppOption) {
                    // Obsługa bankomatów
                    case "1" -> {
                        clearConsole.run();
                        while (true) {
                            System.out.print("Włóż kartę podając numer karty: ");
                            String recipientCardNumber = scanner.nextLine();
                            
                            System.out.print("Wprowadź PIN: ");
                            String recipientCardPIN = scanner.nextLine();

                            Account loggedInAccount = null;
                            for (Account account : accountManager.getAccounts()) {
                                if (account.getCardNumber().equals(recipientCardNumber) && account.getCardPin().equals(recipientCardPIN)) {
                                    loggedInAccount = account;
                                    break;
                                }
                            }

                            if (loggedInAccount != null) {
                                clearConsole.run();
                                System.out.println("Zalogowano pomyślnie.");
                                OUTER_1:
                                while (true) {
                                    System.out.println("Wybierz opcję: 1 - Wpłać, 2 - Wypłać, 3 - Zmień PIN, 4 - Wysuń kartę");
                                    String optionServices = scanner.nextLine();
                                    // Wpłata
                                    switch (optionServices) {
                                        case "1" -> {
                                            clearConsole.run();
                                            while (true) {
                                                System.out.print("Podaj kwotę do wpłacenia: ");
                                                double payment = scanner.nextDouble();
                                                scanner.nextLine();
                                                if (payment >= 10 && payment % 10 == 0) {
                                                    String depositCurrency = loggedInAccount.getCurrency();
                                                    double convertedAmount = convertCurrency(payment, depositCurrency, loggedInAccount.getCurrency());
                                                    loggedInAccount.deposit(convertedAmount);

                                                    clearConsole.run();
                                                    System.out.println("Wpłacono: " + payment + " " + depositCurrency + " (przeliczone na " + convertedAmount + " " + loggedInAccount.getCurrency() + ")");
                                                    break;
                                                } else {
                                                    clearConsole.run();
                                                    System.out.println("Kwota musi być wielokrotnością liczby 10 i nie mniejsza niż 10 zł. Spróbuj ponownie.");
                                                }
                                            }
                                        }
                                        // Wypłata
                                        case "2" -> {
                                            clearConsole.run();
                                            while (true) {
                                                System.out.print("Podaj kwotę do wypłacenia (minimum 10 zł): ");
                                                double paycheck = scanner.nextDouble();
                                                scanner.nextLine();
                                                if (paycheck >= 10 && paycheck % 10 == 0) {
                                                    if (loggedInAccount.getBalance() >= paycheck) {
                                                        loggedInAccount.withdraw(paycheck);
                                                        clearConsole.run();
                                                        System.out.println("Wypłacono: " + paycheck + " " + loggedInAccount.getCurrency());
                                                        break;
                                                    } else {
                                                        clearConsole.run();
                                                        System.out.println("Niewystarczające środki na koncie.");
                                                    }
                                                } else {
                                                    clearConsole.run();
                                                    System.out.println("Kwota musi być wielokrotnością liczby 10 i nie mniejsza niż 10 zł. Spróbuj ponownie.");
                                                }
                                            }
                                        }
                                        // Zmiana PINu
                                        case "3" -> {
                                            clearConsole.run();
                                            while (true) { 
                                                System.out.print("Wprowadź nowy PIN (4 cyfry): ");
                                                String newPin = scanner.nextLine();
                                                if (newPin.matches("\\d{4}") && !newPin.equals(cardPin)) {
                                                    clearConsole.run();
                                                    System.out.println("PIN został zmieniony poprawnie.");
                                                    cardPin = newPin;
                                                    for (Account account : accountManager.getAccounts()) {
                                                        if (account.getCardNumber().equals(cardNumber)) {
                                                            account.setCardPin(cardPin);
                                                            break;
                                                        }
                                                    }
                                                    break;
                                                } else if (newPin.equals(cardPin)) {
                                                    clearConsole.run();
                                                    System.out.println("Nowy PIN nie może być taki sam jak stary. Spróbuj ponownie.");
                                                } else {
                                                    clearConsole.run();
                                                    System.out.println("PIN musi składać się z 4 cyfr. Spróbuj ponownie.");  
                                                }
                                            }
                                        }
                                        // Wysunięcie karty
                                        case "4" -> {
                                            clearConsole.run();
                                            break OUTER_1;
                                        }
                                        default -> System.out.println("Niepoprawna opcja. Spróbuj ponownie.");
                                    }
                                } break;
                            } else {
                                System.out.println("Niepoprawny numer karty lub PIN. Spróbuj ponownie.");
                            }
                        }
                    }
                    // Bankowość online
                    case "2" -> {
                        OUTER:
                        while (true) {
                            clearConsole.run();
                            System.out.println("Aby skorzystać z bankowości online, zaloguj się lub zarejestuj.");
                            System.out.println("Wybierz opcję: 1 - Rejestracja, 2 - Logowanie, 3 - Wyjście");
                            String LoginOption = scanner.nextLine();
    
                        switch (LoginOption) {
                            // Rejestracja
                            case "1" -> {
                                clearConsole.run();
                                System.out.println("Wypełnij formularz, aby w pełni korzystać z naszej aplikacji.");
    
                                // Wprowadzenie imienia użytkownika
                                while (true) {
                                    System.out.print("Podaj imię: ");
                                    Username = scanner.nextLine();
    
                                    if (Username.matches("[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźżA-ZĄĆĘŁŃÓŚŹŻ]*")) {
                                        break;
                                    } else if (Username.isEmpty()) {
                                        clearConsole.run();
                                        System.out.println("Proszę wpisać swoje imię.");
                                    } else {
                                        clearConsole.run();
                                        System.out.println("Imię musi zaczynać się od dużej litery i może zawierać tylko litery. Spróbuj ponownie.");
                                    }
                                }
                                clearConsole.run();
    
                                // Wprowadzenie nazwiska użytkownika
                                while (true) {
                                    System.out.print("Podaj nazwisko: ");
                                    Surname = scanner.nextLine();

                                    if (Surname.matches("[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźżA-ZĄĆĘŁŃÓŚŹŻ]*")) {
                                        break;
                                    } else if (Surname.isEmpty()) {
                                        clearConsole.run();
                                        System.out.println("Proszę wpisać swoje nazwisko.");
                                    } else {
                                        clearConsole.run();
                                        System.out.println("Nazwisko musi zaczynać się od dużej litery i może zawierać tylko litery. Spróbuj ponownie.");
                                    }
                                }
                                clearConsole.run();

                                // Wprowadzenie loginu użytkownika
                                while (true) {
                                    System.out.print("Podaj login: ");
                                    Login = scanner.nextLine();
                                    if (Login.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$")) {
                                        break;
                                    } else {
                                        clearConsole.run();
                                        System.out.println("Login musi zawierać co najmniej jedną literę i jedną cyfrę. Spróbuj ponownie.");
                                    }
                                }
                                clearConsole.run();

                                // Wprowadzenie adresu email użytkownika
                                while (true) {
                                    System.out.print("Podaj adres email: ");
                                    Email = scanner.nextLine();
                                    if (isValidEmail(Email)) {
                                        break;
                                    } else {
                                        clearConsole.run();
                                        System.out.println("Niepoprawny adres email. Spróbuj ponownie.");
                                    }
                                }

                                clearConsole.run();

                                // Wprowadzenie adresu użytkownika
                                while (true) {
                                    System.out.print("Podaj adres: ");
                                    Address = scanner.nextLine();
                                    if (Address.matches("^[A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]+\\s\\d+$")) {
                                        break;
                                    } else {
                                        clearConsole.run();
                                        System.out.println("Adres musi być w formacie 'Ulica Numer'. Spróbuj ponownie.");
                                    }
                                }
                                clearConsole.run();

                                // Wprowadzenie daty urodzenia użytkownika
                                while (true) {
                                    System.out.print("Podaj swoją datę urodzenia (dd-MM-yyyy): ");
                                    Birth = scanner.nextLine();
                                    try {
                                        LocalDate birthDate = LocalDate.parse(Birth, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                                        LocalDate currentDate = LocalDate.now();
                                        int age = Period.between(birthDate, currentDate).getYears();
                                        if (age >= 13 && age <= 120) {
                                            break;
                                        } else {
                                            clearConsole.run();
                                            System.out.println("Wiek musi być pomiędzy 13 a 120 lat. Spróbuj ponownie.");
                                        }
                                    } catch (DateTimeParseException e) {
                                        clearConsole.run();
                                        System.out.println("Niepoprawny format daty. Spróbuj ponownie wpisując (dd-MM-yyyy).");
                                    }
                                }
                                clearConsole.run();

                                // Wprowadzenie numeru PESEL użytkownika
                                while (true) {
                                    System.out.print("Podaj swój numer PESEL (11 cyfr): ");
                                    Pesel = scanner.nextLine();
                                    if (Pesel.matches("\\d{11}")) {
                                        break;
                                    } else {
                                        clearConsole.run();
                                        System.out.println("Numer PESEL musi składać się z 11 cyfr. Spróbuj ponownie.");
                                    }
                                }
                                clearConsole.run();

                                // Wprowadzenie hasła użytkownika
                                while (true) {
                                    System.out.print("Ustaw swoje hasło: ");
                                    Password = scanner.nextLine();
                                    if (isValidPassword(Password)) {
                                        break;
                                    } else {
                                        clearConsole.run();
                                        System.out.println("Hasło musi mieć minimum 8 znaków, zawierać dużą i małą literę oraz chociaż jedną cyfrę. Spróbuj ponownie.");
                                    }
                                }
                                clearConsole.run();

                                // Utworzenie numeru konta bankowego, numeru karty i numeru PIN
                                String accountNumber = generateAccountNumber();
                                cardNumber = generateCardNumber(); 
                                cardPin = generateCardPin();
                                accountManager.addAccount(new Account(accountNumber, cardNumber, cardPin,"PLN", 0, Username, Surname, Login, Password, Email));

                                System.out.println("Dziękujemy za rejestrację. Możesz teraz zalogować się do systemu.");
                            }
                            // Logowanie
                            case "2" -> {
                                clearConsole.run();
                                System.out.println("Zaloguj się do aplikacji");
                                System.out.print("Wprowadź swój Login lub adres e-mail: ");
                                String loginOrEmail = scanner.nextLine();
                                
                                System.out.print("Wprowadź hasło: ");
                                String password = scanner.nextLine();

                                Account loggedInAccount = null;
                                for (Account account : accountManager.getAccounts()) {
                                    if ((account.getLogin().equals(loginOrEmail) || account.getEmail().equals(loginOrEmail)) && account.getPassword().equals(password)) {
                                        loggedInAccount = account;
                                        break;
                                    }
                                }

                                if (loggedInAccount != null) {
                                    clearConsole.run();
                                    System.out.println("Logowanie zakończone sukcesem.");
                                    
                                    while (true) { 
                                        // Menu bankowości online
                                        System.out.println("Wybierz opcję: 1 - Przelew, 2 - Dodaj rachunek, 3 - Wyświetl moje rachunki, 4 - Ustawienia, 5 - Wyloguj");
                                        String UserOption = scanner.nextLine();
                                        OUTER_3:
                                        switch (UserOption) {
                                            // Przelew
                                            case "1" -> {
                                                clearConsole.run();
                                                String recipientName;
                                                while (true) {
                                                    System.out.print("Podaj nazwę odbiorcy (maksymalnie 30 znaków): ");
                                                    recipientName = scanner.nextLine();
                                                    if (recipientName.length() <= 30 && !recipientName.isEmpty()) {
                                                        break;
                                                    } else {
                                                        System.out.println("Nazwa odbiorcy nie może przekraczać 30 znaków. Spróbuj ponownie.");
                                                    }
                                                }

                                                String fromAccountNumber;
                                                while (true) {
                                                    System.out.print("Podaj numer rachunku, z którego chcesz przelać pieniądze (26 cyfr): ");
                                                    fromAccountNumber = scanner.nextLine();
                                                    if (fromAccountNumber.length() <=30 && !fromAccountNumber.isEmpty()) {
                                                        break;
                                                    } else {
                                                        System.out.println("Numer rachunku musi składać się z Numeru lban oraz 26 cyfr. Spróbuj ponownie.");
                                                    }
                                                }

                                                String toAccountNumber;
                                                while (true) {
                                                    System.out.print("Podaj numer rachunku, na który chcesz przelać pieniądze (26 cyfr): ");
                                                    toAccountNumber = scanner.nextLine();
                                                    if (toAccountNumber.length() <=30 && !toAccountNumber.isEmpty()) {
                                                        break;
                                                    } else {
                                                        System.out.println("Numer rachunku musi składać się z Numeru lban oraz 26 cyfr. Spróbuj ponownie.");
                                                    }
                                                }

                                                double amount;
                                                while (true) {
                                                    System.out.print("Podaj kwotę do przelania: ");
                                                    if (scanner.hasNextDouble()) {
                                                        amount = scanner.nextDouble();
                                                        scanner.nextLine();
                                                        if (amount > 0) {
                                                            break;
                                                        } else {
                                                            System.out.println("Kwota musi być większa od zera. Spróbuj ponownie.");
                                                        }
                                                    } else {
                                                        System.out.println("Podaj poprawną kwotę. Spróbuj ponownie.");
                                                        scanner.nextLine();
                                                    }
                                                }

                                                System.out.print("Podaj adres odbiorcy (opcjonalnie): ");
                                                @SuppressWarnings("unused")
                                                String recipientAddress = scanner.nextLine();
        
                                                Account sourceAccount = accountManager.getAccountByNumber(fromAccountNumber);
                                                Account targetAccount = accountManager.getAccountByNumber(toAccountNumber);

                                                if (sourceAccount != null && targetAccount != null) {
                                                    if (sourceAccount.getBalance() >= amount) {
                                                        double convertedAmount = convertCurrency(amount, sourceAccount.getCurrency(), targetAccount.getCurrency());
                                                        sourceAccount.withdraw(amount);
                                                        targetAccount.deposit(convertedAmount);
                                                        clearConsole.run();
                                                        System.out.println("Przelano " + amount + " " + sourceAccount.getCurrency() + " z rachunku " + fromAccountNumber + " na rachunek " + toAccountNumber);
                                                        System.out.println("Kwota po przeliczeniu: " + convertedAmount + " " + targetAccount.getCurrency());
                                                    } else {
                                                        System.out.println("Niewystarczające środki na rachunku źródłowym.");
                                                    }
                                                } else {
                                                    System.out.println("Nie znaleziono jednego z rachunków.");
                                                }
                                            }
                                            // Dodanie rachunku
                                            case "2" -> {
                                                clearConsole.run();
                                                System.out.println("Dodaj nowy rachunek bankowy.");
                                                System.out.println("Wybierz walutę nowego rachunku: 1 - PLN, 2 - USD, 3 - EUR, 4 - CHF, 5 - GBP");
                                                String currencyOption = scanner.nextLine();
        
                                                switch (currencyOption) {
                                                    // Dodanie rachunku w PLN
                                                    case "1" -> {
                                                        String newAccountNumber = generateAccountNumber();
                                                        String newCardNumber = generateCardNumber();
                                                        String newCardPin = generateCardPin();
                                                        System.out.println("Twój nowy numer rachunku w PLN: " + newAccountNumber);
                                                        accountManager.addAccount(new Account(newAccountNumber, newCardNumber, newCardPin,"PLN", 0, Username, Surname, Login, Password, Email));
                                                        System.out.println("1 - Wróć");
                                                        String back = scanner.nextLine();
                                                        if (back.equals("1")) {
                                                            clearConsole.run();
                                                            break OUTER_3;
                                                        }
                                                    }
                                                    // Dodanie rachunku w USD
                                                    case "2" -> {
                                                        String newAccountNumber = generateAccountNumberUSD();
                                                        String newCardNumber = generateCardNumber(); 
                                                        String newCardPin = generateCardPin();
                                                        System.out.println("Twój nowy numer rachunku w USD: " + newAccountNumber);
                                                        accountManager.addAccount(new Account(newAccountNumber, newCardNumber, newCardPin,"USD", 0, Username, Surname, Login, Password, Email));
                                                        System.out.println("1 - Wróć");
                                                        String back = scanner.nextLine();
                                                        if (back.equals("1")) {
                                                            clearConsole.run();
                                                            break OUTER_3;
                                                        }
                                                    }
                                                    // Dodanie rachunku w EUR
                                                    case "3" -> {
                                                        String newAccountNumber = generateAccountNumberEUR();
                                                        String newCardNumber = generateCardNumber(); 
                                                        String newCardPin = generateCardPin();
                                                        System.out.println("Twój nowy numer rachunku w EUR: " + newAccountNumber);
                                                        accountManager.addAccount(new Account(newAccountNumber, newCardNumber, newCardPin,"EUR", 0, Username, Surname, Login, Password, Email));
                                                        System.out.println("1 - Wróć");
                                                        String back = scanner.nextLine();
                                                        if (back.equals("1")) {
                                                            clearConsole.run();
                                                            break OUTER_3;
                                                        }
                                                    }
                                                    // Dodanie rachunku w CHF
                                                    case "4" -> {
                                                        String newAccountNumber = generateAccountNumberCHF();
                                                        String newCardNumber = generateCardNumber(); 
                                                        String newCardPin = generateCardPin();
                                                        System.out.println("Twój nowy numer rachunku w CHF: " + newAccountNumber);
                                                        accountManager.addAccount(new Account(newAccountNumber, newCardNumber, newCardPin,"CHF", 0, Username, Surname, Login, Password, Email));
                                                        System.out.println("1 - Wróć");
                                                        String back = scanner.nextLine();
                                                        if (back.equals("1")) {
                                                            clearConsole.run();
                                                            break OUTER_3;
                                                        }
                                                    }
                                                    // Dodanie rachunku w GBP
                                                    case "5" -> {
                                                        String newAccountNumber = generateAccountNumberGBP();
                                                        String newCardNumber = generateCardNumber(); 
                                                        String newCardPin = generateCardPin();
                                                        System.out.println("Twój nowy numer rachunku w GBP: " + newAccountNumber);
                                                        accountManager.addAccount(new Account(newAccountNumber, newCardNumber, newCardPin,"GBP", 0, Username, Surname, Login, Password, Email));
                                                        System.out.println("1 - Wróć");
                                                        String back = scanner.nextLine();
                                                        if (back.equals("1")) {
                                                            clearConsole.run();
                                                            break OUTER_3;
                                                        }
                                                    }
                                                }
                                            }
                                            // Wyświetlenie rachunków
                                            case "3" -> {
                                                clearConsole.run();
                                                System.out.println("Twoje rachunki:");
                                                System.out.println("-------------------------");
                                                displayUserAccounts(accountManager, loggedInAccount);
                                            }
                                            // Ustawienia
                                            case "4" -> {
                                                OUTER_2:
                                                while (true) {
                                                    clearConsole.run();
                                                    System.out.println("Wybierz opcję: 1 - Zmień adres e-mail, 2 - Zmień hasło, 3 - Powrót");
                                                    String ControlPanel = scanner.nextLine();
                    
                                                    switch (ControlPanel) {
                                                        // Zmiana adresu e-mail
                                                        case "1" -> {
                                                            clearConsole.run();
                                                            while (true) { 
                                                                System.out.print("Wprowadź aktualny adres e-mail: ");
                                                                String currentEmail = scanner.nextLine();
                                                                
                                                                if (currentEmail.equals(Email)) {
                                                                    clearConsole.run();
                                                                    while (true) { 
                                                                        System.out.print("Wprowadź nowy adres e-mail: ");
                                                                        String newEmail = scanner.nextLine();
                                                                        if (isValidEmail(newEmail) && !newEmail.equals(Email)) {
                                                                            Email = newEmail;
                                                                            clearConsole.run();
                                                                            System.out.println("Adres e-mail został zmieniony pomyślnie.");
                                                                            break;
                                                                        } else if (newEmail.equals(Email)) {
                                                                            clearConsole.run();
                                                                            System.out.println("Nowy adres e-mail nie może być taki sam jak stary. Spróbuj ponownie.");
                                                                        } else {
                                                                            clearConsole.run();
                                                                            System.out.println("Niepoprawny adres e-mail. Spróbuj ponownie.");
                                                                        }
                                                                    }
                                                                    break;
                                                                } else {
                                                                    clearConsole.run();
                                                                    System.out.println("Adres e-mail nie został potwierdzony. Spróbuj ponownie.");
                                                                }
                                                            }
                                                        }
                                                        // Zmiana hasła
                                                        case "2" -> {
                                                            clearConsole.run();
                                                            while (true) {
                                                                System.out.print("Wprowadź aktualne hasło: ");
                                                                String currentPassword = scanner.nextLine();
                                                                
                                                                if (currentPassword.equals(Password)) {
                                                                    clearConsole.run();
                                                                    while (true) { 
                                                                        System.out.print("Wprowadź nowe hasło: ");
                                                                        String newPassword = scanner.nextLine();
                                                                        if (isValidPassword(newPassword) && !newPassword.equals(Password)) {
                                                                            Password = newPassword;
                                                                            clearConsole.run();
                                                                            System.out.println("Hasło zostało zmienione pomyślnie.");
                                                                            break;
                                                                        } else if (newPassword.equals(Password)) {
                                                                            clearConsole.run();
                                                                            System.out.println("Nowe hasło nie może być takie samo jak stare. Spróbuj ponownie.");
                                                                        } else {
                                                                            clearConsole.run();
                                                                            System.out.println("Hasło musi mieć minimum 8 znaków, zawierać dużą i małą literę oraz chociaż jedną cyfrę. Spróbuj ponownie.");
                                                                        }
                                                                    }
                                                                    break;
                                                                } else {
                                                                    clearConsole.run();
                                                                    System.out.println("Hasło nie zostało potwierdzone. Spróbuj ponownie.");
                                                                }
                                                            }
                                                        }
                                                        // Powrót
                                                        case "3" -> {
                                                            clearConsole.run();
                                                            break OUTER_2;
                                                        }
                                                        default -> System.out.println("Niepoprawna opcja. Spróbuj ponownie.");
                                                    }
                                                }
                                            }
                                            // Wylogowanie
                                            case "5" -> {
                                                clearConsole.run();
                                                System.out.println("Wylogowano pomyślnie.");
                                                break OUTER;
                                            }
                                            default -> {
                                                System.out.println("Niepoprawna opcja. Spróbuj ponownie.");
                                            }
                                        }
                                    }
                                } else {
                                    clearConsole.run();
                                    System.out.println("Niepoprawne dane logowania. Spróbuj ponownie.");
                                    break OUTER;
                                }
                            }
                            // Wyjście
                            case "3" -> {
                                clearConsole.run();
                            }
                            default -> {
                                System.out.println("Niepoprawna opcja. Spróbuj ponownie.");
                            }
                        }
                        break;
                    }
                }
                    // Wyjście
                    case "3" -> {
                        clearConsole.run();
                        System.out.println("Dziękujemy za skorzystanie z naszych usług. Do zobaczenia!");
                        System.exit(0);
                    }
                    case "4" -> {
                        clearConsole.run();
                        System.out.println("Witaj w panelu obsługi banku.");
                        while (true) { 
                            System.out.print("Podaj login (admin): ");
                            String getAdminLogin = scanner.nextLine();

                            System.out.print("Podaj hasło (admin): ");
                            String getAdminPassword = scanner.nextLine();

                            if (getAdminLogin.equals(LoginAdmin) && getAdminPassword.equals(PasswordAdmin)) {
                                clearConsole.run();
                                System.out.println("Zalogowano pomyślnie.");
                                displayAllUsers(accountManager);
                                break;
                            } else {
                                clearConsole.run();
                                System.out.println("Niepoprawne dane logowania. Spróbuj ponownie.");
                            }
                        }
                    }
                }
            }
        }
    }
    // Metoda do walidacji adresu e-mail
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    // Metoda do walidacji hasła
    private static boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        boolean hasUpper = false, hasLower = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        return hasUpper && hasLower && hasDigit;
    }

    // Metody do generowania numeru rachunków
    private static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("PL ");
        for (int i = 0; i < 26; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    private static String generateAccountNumberUSD() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("USD ");
        for (int i = 0; i < 26; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
    
    private static String generateAccountNumberEUR() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("EUR ");
        for (int i = 0; i < 26; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    private static String generateAccountNumberCHF() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("CHF ");
        for (int i = 0; i < 26; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    private static String generateAccountNumberGBP() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("GBP ");
        for (int i = 0; i < 26; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    // Generowanie unikatowego numeru karty
    private static String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    // Generowanie unikatowego numeru PIN
    private static String generateCardPin() {
        Random random = new Random();
        StringBuilder cardPin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            cardPin.append(random.nextInt(10));
        }
        return cardPin.toString();
    }

    // Metoda do przeliczania walut
    private static double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }
        String key = fromCurrency + "-" + toCurrency;
        Double exchangeRate = exchangeRates.get(key);
        if (exchangeRate != null) {
            return amount * exchangeRate;
        } else {
            throw new IllegalArgumentException("Nieobsługiwany kurs wymiany: " + key);
        }
    }

    private static void displayAllUsers(AccountManager accountManager) {
        List<Account> accounts = accountManager.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Brak użytkowników w systemie.");
        } else {
            System.out.println("Lista użytkowników banku:");
            for (Account account : accounts) {
                System.out.println(account);
            }
        }
    }
    private static void displayUserAccounts(AccountManager accountManager, Account loggedInAccount) {
        List<Account> accounts = accountManager.getAccounts();
        for (Account account : accounts) {
            if (account.getLogin().equals(loggedInAccount.getLogin())) {
                System.out.println("Numer rachunku: " + account.getAccountNumber());
                System.out.println("Numer karty: " + account.getCardNumber());
                System.out.println("Numer PIN: " + account.getCardPin());
                System.out.println("Saldo: " + account.getBalance() + " " + account.getCurrency());
                System.out.println("-------------------------");
            }
        }
    }
}
