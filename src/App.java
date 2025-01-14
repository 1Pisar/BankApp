//Wykorzystanie bibliotek Java do stworzenia aplikacji bankowej

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateFormat.setLenient(false);
            
            // Metoda do czyszczenia konsoli
            Runnable clearConsole = () -> {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            };

            //Usuwanie zawartości konsoli
            clearConsole.run();
            
            //Przywitanie użytkownika
            System.out.println("Witaj w banku PB Bank!");
            System.out.println("Załóż konto i korzystaj z naszych usług.");
            
            //Wprowadzenie imienia użytkownika
            String setUsername;
            while (true) {
                System.out.print("Podaj imię: ");
                setUsername = scanner.nextLine();
                if (setUsername.matches("[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+")) {
                    break;
                } else {
                    System.out.println("Imię może zawierać tylko litery. Spróbuj ponownie.");
                }
            }

            @SuppressWarnings("unused")
            String Username = setUsername;
            clearConsole.run();
            
            //Wprowadzenie nazwiska użytkownika
            String setSurname;
            while (true) {
                System.out.print("Podaj nazwisko: ");
                setSurname = scanner.nextLine();
                if (setSurname.matches("[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+")) {
                    break;
                } else {
                    System.out.println("Nazwisko może zawierać tylko litery. Spróbuj ponownie.");
                }
            }

            @SuppressWarnings("unused")
            String Surname = setSurname;
            clearConsole.run();
            
            //Wprowadzenie loginu użytkownika
            String setLogin;
            System.out.println("Podaj swój Login: ");
            setLogin = scanner.nextLine();

            String Login = setLogin;
            clearConsole.run();
            
            //Wprowadzenie adresu e-mail użytkownika
            String setEmail;
            while (true) {
                System.out.print("Podaj swój adres e-mail: ");
                setEmail = scanner.nextLine();
                if (setEmail.contains("@")) {
                    break;
                } else {
                    System.out.println("Adres e-mail musi zawierać znak '@'. Spróbuj ponownie.");
                }
            }

            String Email = setEmail;
            clearConsole.run();
            
            //Wprowadzenie adresu zamieszkania użytkownika
            String setAddress;
            System.out.print("Podaj swój adres zamieszkania: ");
            setAddress = scanner.nextLine();
            
            @SuppressWarnings("unused")
            String Address = setAddress;
            clearConsole.run();

            //Wprowadzenie daty urodzenia użytkownika
            String setBirth;
            while (true) {
                System.out.print("Podaj swoją datę urodzenia (dd-MM-yyyy): ");
                setBirth = scanner.nextLine();
                try {
                    dateFormat.parse(setBirth);
                    break;
                } catch (ParseException e) {
                    System.out.println("Niepoprawny format daty. Spróbuj ponownie wpisując (dd-MM-yyyy).");
                }
            }
            
            @SuppressWarnings("unused")
            String Birth = setBirth;
            clearConsole.run();
            
            //Wprowadzenie numeru PESEL użytkownika
            String setPesel;
            while (true) {
                System.out.print("Podaj swój numer PESEL (11 cyfr): ");
                setPesel = scanner.nextLine();
                if (setPesel.matches("\\d{11}")) {
                    break;
                } else {
                    System.out.println("Numer PESEL musi składać się z 11 cyfr. Spróbuj ponownie.");
                }
            }

            @SuppressWarnings("unused")
            String Pesel = setPesel;
            clearConsole.run();
            
            //Wprowadzenie hasła użytkownika
            String setPassword;
            System.out.print("Ustaw swoję hasło: ");
            setPassword = scanner.nextLine();

            String Password = setPassword;
            clearConsole.run();
            
            //Utworzenie numeru konta
            String accountNumber = generateAccountNumber();
            System.out.println("Twój nowy numer rachunku w PLN: " + accountNumber);
            
            //Utworzenie numeru karty i PIN
            String cardNumber = generateCardNumber();
            String cardPin = generateCardPin();
            System.out.println("Twój nowy numer karty: " + cardNumber);
            System.out.println("Twój nowy PIN do karty: " + cardPin);
            System.out.println("Zapamiętaj te dane, ponieważ nie zostaną one wyświetlone ponownie.");
            
            while (true) {
                //Logowanie do aplikacji 
                System.out.println("Zaloguj się do aplikacji");
                System.out.print("Wprowadź swój Login lub adres e-mail: ");
                String loginOrEmail = scanner.nextLine();
                
                System.out.print("Wprowadź hasło: ");
                String correctPassword = scanner.nextLine();

                if ((loginOrEmail.equals(Login) || loginOrEmail.equals(Email)) && correctPassword.equals(Password)) {
                    System.out.println("Logowanie zakończone sukcesem.");
                    clearConsole.run();
                    OUTER:
                    while (true) {
                        System.out.println("Wybierz opcję: 1 - Bankowość online, 2 - Obsługa bankomatów, 3 - Zarządzanie kontem, 4 - Wyloguj");
                        String AppOption = scanner.nextLine();
                        //Sekcja bankowości online
                        switch (AppOption) {
                            case "1" -> {
                                while (true) {
                                    clearConsole.run();
                                    System.out.println("Podaj dane do przelewu.");
                                    System.out.print("Podaj nazwę odbiorcy: ");
                                    String recipientName = scanner.nextLine();
                                    
                                    System.out.print("Podaj numer rachunku odbiorcy (26 cyfr): ");
                                    String recipientAccountNumber = scanner.nextLine();
                                    
                                    while (!recipientAccountNumber.matches("\\d{26}")) {
                                        System.out.println("Numer rachunku musi składać się z 26 cyfr. Spróbuj ponownie.");
                                        recipientAccountNumber = scanner.nextLine();
                                    }
                                    
                                    System.out.print("Podaj kwotę do przelania: ");
                                    double amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    
                                    System.out.print("Podaj adres odbiorcy: ");
                                    String recipientAddress = scanner.nextLine();
                                    
                                    clearConsole.run();
                                    System.out.println("Przelew zakończony sukcesem.");
                                    System.out.println("Nazwa odbiorcy: " + recipientName);
                                    System.out.println("Numer rachunku odbiorcy: " + recipientAccountNumber);
                                    System.out.println("Kwota: " + amount + " zł");
                                    System.out.println("Adres odbiorcy: " + recipientAddress);
                                    break;
                                }
                            }   
                            //Sekcja obsługi bankomatów
                            case "2" -> {
                                while (true) {
                                    clearConsole.run();
                                    System.out.print("Włóż kartę podając numer karty: ");
                                    String recipientCardNumber = scanner.nextLine();
                                    
                                    System.out.print("Wprowadź PIN: ");
                                    String recipientCardPIN = scanner.nextLine();
                                    
                                    if (recipientCardNumber.equals(cardNumber) && recipientCardPIN.equals(cardPin)) {
                                        clearConsole.run();
                                        OUTER_1:
                                        while (true) {
                                            System.out.println("Wybierz opcję: 1 - Wpłać, 2 - Wypłać, 3 - Zmień PIN, 4 - Wysuń kartę");
                                            String optionServices = scanner.nextLine();
                                            //Wpłata
                                            switch (optionServices) {
                                                case "1" -> {
                                                    clearConsole.run();
                                                    while (true) {
                                                        System.out.print("Podaj kwotę do wpłacenia (minimum 10 zł): ");
                                                        double payment = scanner.nextDouble();
                                                        scanner.nextLine();
                                                        if (payment >= 10 && payment % 10 == 0) {
                                                            clearConsole.run();
                                                            System.out.println("Wpłacono: " + payment + " zł na kartę o numerze: " + cardNumber);
                                                            break;
                                                        } else {
                                                            clearConsole.run();
                                                            System.out.println("Kwota musi być wielokrotnością liczby 10 i nie mniejsza niż 10 zł. Spróbuj ponownie.");
                                                        }
                                                    }
                                                }
                                                //Wypłata
                                                case "2" -> {
                                                    clearConsole.run();
                                                    while (true) {
                                                        System.out.print("Podaj kwotę do wypłacenia (minimum 10 zł): ");
                                                        double paycheck = scanner.nextDouble();
                                                        scanner.nextLine();
                                                        if (paycheck >= 10 && paycheck % 10 == 0) {
                                                            clearConsole.run();
                                                            System.out.println("Wypłacono: " + paycheck + " zł z karty o numerze: " + cardNumber);
                                                            break;
                                                        } else {
                                                            clearConsole.run();
                                                            System.out.println("Kwota musi być wielokrotnością liczby 10 i nie mniejsza niż 10 zł. Spróbuj ponownie.");
                                                        }
                                                    }
                                                }
                                                //Zmiana PINu
                                                case "3" -> {
                                                    clearConsole.run();
                                                    while (true) { 
                                                        System.out.print("Wprowadź nowy PIN (4 cyfry): ");
                                                        String newPin = scanner.nextLine();
                                                        if (newPin.matches("\\d{4}") && !newPin.equals(cardPin)) {
                                                            clearConsole.run();
                                                            System.out.println("Pin został zmieniony poprawnie.");
                                                            cardPin = newPin;
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
                                                //Wysunięcie karty
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
                            //Sekcja zarządzania kontem
                            case "3" -> {
                                clearConsole.run();
                                OUTER_2:
                                while (true) {
                                    System.out.println("Wybierz opcję: 1 - Zmień adres e-mail, 2 - Zmień hasło, 3 - Powrót");
                                    String ControlPanel = scanner.nextLine();
    
                                    switch (ControlPanel) {
                                        //Zmiana adresu e-mail
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
                                                        if (newEmail.contains("@") && !newEmail.equals(Email)) {
                                                            Email = newEmail;
                                                            clearConsole.run();
                                                            System.out.println("Adres e-mail został zmieniony pomyślnie.");
                                                            break;
                                                        } else if (newEmail.equals(Email)) {
                                                            clearConsole.run();
                                                            System.out.println("Nowy adres e-mail nie może być taki sam jak stary. Spróbuj ponownie.");
                                                        } else {
                                                            clearConsole.run();
                                                            System.out.println("Adres e-mail musi zawierać znak '@'. Spróbuj ponownie.");
                                                        }
                                                    }
                                                    break;
                                                } else {
                                                    clearConsole.run();
                                                    System.out.println("Adres e-mail nie został potwierdzony. Spróbuj ponownie.");
                                                }
                                            }
                                        }
                                        //Zmiana hasła
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
                                                        if (!newPassword.equals(Password)) {
                                                            Password = newPassword;
                                                            clearConsole.run();
                                                            System.out.println("Hasło zostało zmienione pomyślnie.");
                                                            break;
                                                        } else {
                                                            clearConsole.run();
                                                            System.out.println("Nowe hasło nie może być takie samo jak stare. Spróbuj ponownie.");
                                                        }
                                                    }
                                                    break;
                                                } else {
                                                    clearConsole.run();
                                                    System.out.println("Hasło nie zostało potwierdzone. Spróbuj ponownie.");
                                                }
                                            }
                                        }
                                        //Powrót
                                        case "3" -> {
                                            clearConsole.run();
                                            break OUTER_2;
                                        }
                                        default -> System.out.println("Niepoprawna opcja. Spróbuj ponownie.");
                                    }
                                }
                            }
                            //Wylogowanie z aplikacji
                            case "4" -> {
                                clearConsole.run();
                                System.out.println("Wylogowano pomyślnie.");
                                break OUTER;
                            }
                            default -> System.out.println("Niepoprawna opcja. Spróbuj ponownie.");
                        }
                    }
                } else {
                    clearConsole.run();
                    System.out.println("Logowanie nie powiodło się. Spróbuj ponownie.");
                }
            }
        }
    }

    //Generowanie unikatowego numeru konta
    private static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("PL");
        for (int i = 0; i < 26; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    //Generowanie unikatowego numeru karty
    private static String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    //Generowanie unikatowego numeru PIN
    private static String generateCardPin() {
        Random random = new Random();
        StringBuilder cardPin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            cardPin.append(random.nextInt(10));
        }
        return cardPin.toString();
    }
}
