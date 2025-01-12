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
            
            //Usuwanie zawartości konsoli
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
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
            
            //Wprowadzenie loginu użytkownika
            String setLogin;
            System.out.println("Podaj swój Login: ");
            setLogin = scanner.nextLine();

            String Login = setLogin;
            
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
            
            //Wprowadzenie adresu zamieszkania użytkownika
            String setAddress;
            System.out.print("Podaj swój adres zamieszkania: ");
            setAddress = scanner.nextLine();
            
            @SuppressWarnings("unused")
            String Address = setAddress;

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
            
            //Wprowadzenie hasła użytkownika
            String setPassword;
            System.out.print("Ustaw swoję hasło: ");
            setPassword = scanner.nextLine();

            String Password = setPassword;
            
            //Utworzenie numeru konta
            String accountNumber = generateAccountNumber();
            System.out.println("Twój nowy numer rachunku w PLN: " + accountNumber);
            
            //Utworzenie numeru karty i PIN
            String cardNumber = generateCardNumber();
            String cardPin = generateCardPin();
            System.out.println("Twój nowy numer karty: " + cardNumber);
            System.out.println("Twój nowy PIN do karty: " + cardPin);
            
            //Logowanie do aplikacji
            System.out.print("Wprowadź swój Login lub adres e-mail: ");
            String loginOrEmail = scanner.nextLine();
            
            System.out.print("Wprowadź hasło: ");
            String correctPassword = scanner.nextLine();
            
            if ((loginOrEmail.equals(Login) || loginOrEmail.equals(Email)) && correctPassword.equals(Password)) {
                System.out.println("Logowanie zakończone sukcesem.");
                OUTER:
                while (true) {
                    System.out.println("Wybierz opcję: 1 - Bankowość online, 2 - Obsługa bankomatów, 3 - Wyloguj");
                    String AppOption = scanner.nextLine();
                    //Sekcja bankowości online
                    switch (AppOption) {
                        case "1" -> {
                            while (true) {
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
                                System.out.print("Włóż kartę podając numer karty: ");
                                String recipientCardNumber = scanner.nextLine();
                                
                                System.out.print("Wprowadź PIN: ");
                                String recipientCardPIN = scanner.nextLine();
                                
                                if (recipientCardNumber.equals(cardNumber) && recipientCardPIN.equals(cardPin)) {
                                    OUTER_1:
                                    while (true) {
                                        System.out.println("Wybierz opcję: 1 - Wpłać, 2 - Wypłać, 3 - Zmień PIN, 4 - Wysuń kartę");
                                        String optionServices = scanner.nextLine();
                                        //Wpłata
                                        switch (optionServices) {
                                            case "1" -> {
                                                while (true) {
                                                    System.out.print("Podaj kwotę do wpłacenia (minimum 10 zł): ");
                                                    double payment = scanner.nextDouble();
                                                    scanner.nextLine();
                                                    if (payment >= 10 && payment % 10 == 0) {
                                                        System.out.println("Wpłacono: " + payment + " zł na kartę o numerze: " + cardNumber);
                                                        break;
                                                    } else {
                                                        System.out.println("Kwota musi być wielokrotnością liczby 10 i nie mniejsza niż 10 zł. Spróbuj ponownie.");
                                                    }
                                                }
                                            }
                                            //Wypłata
                                            case "2" -> {
                                                while (true) {
                                                    System.out.print("Podaj kwotę do wypłacenia (minimum 10 zł): ");
                                                    double paycheck = scanner.nextDouble();
                                                    scanner.nextLine();
                                                    if (paycheck >= 10 && paycheck % 10 == 0) {
                                                        System.out.println("Wypłacono: " + paycheck + " zł z karty o numerze: " + cardNumber);
                                                        break;
                                                    } else {
                                                        System.out.println("Kwota musi być wielokrotnością liczby 10 i nie mniejsza niż 10 zł. Spróbuj ponownie.");
                                                    }
                                                }
                                            }
                                            //Zmiana PINu
                                            case "3" -> {
                                                System.out.print("Wprowadź nowy PIN (4 cyfry): ");
                                                String newPin = scanner.nextLine();
                                                while (!newPin.matches("\\d{4}")) {
                                                    System.out.println("PIN musi składać się z 4 cyfr. Spróbuj ponownie.");
                                                    newPin = scanner.nextLine();
                                                }
                                                cardPin = newPin;
                                                System.out.println("PIN został zmieniony pomyślnie.");
                                            }
                                            //Wysunięcie karty
                                            case "4" -> {
                                                System.out.println("Karta została wysunięta.");
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
                        //Wylogowanie z aplikacji
                        case "3" -> {
                            System.out.println("Wylogowano pomyślnie.");
                            break OUTER;
                        }
                        default -> System.out.println("Niepoprawna opcja. Spróbuj ponownie.");
                    }
                }
            } else {
                System.out.println("Logowanie nie powiodło się. Spróbuj ponownie.");
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