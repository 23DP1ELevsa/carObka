package lv.rvt;

import lv.rvt.tools.*;
import java.util.*;
import java.io.*;

public class Main {
    private static ArrayList<Person> users = new ArrayList<>();
    private static final String CSV_FILE = "data/users.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Lietotāju ielāde no CSV
        loadUsers();
        
        if(users.isEmpty()){
            System.out.println("Lietotāju nav. Izveidojiet pirmo lietotāju (administratoru).");
        }

        int choice;
        do {
            System.out.println("\nCarObka - automobiļu kolekcija");
            System.out.println("1 - Kolekcijas apskate");
            System.out.println("2 - Ieiet profilā vai reģistrēties");
            System.out.println("3 - Sazināties ar mums");
            System.out.println("0 - Iziet no programmas");
            System.out.print("Ievadiet izvēli: ");
            

            choice = scanner.nextInt();
            scanner.nextLine(); // Veidojam atstarpi
            Loading loading = new Loading();
            loading.LoadingScreen(); // Ielādēšanas ekrāns

            switch (choice) {
                case 1:
                    displayCollection();
                    break;
                case 2:
                    loginOrRegister(scanner);
                    break;
                case 3:
                    contactUs(scanner);
                    break;
                case 0:
                    System.out.println("Paldies, ka izmantojāt CarObka!");
                    break;
                default:
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 0);
    }

    // Metode, lai parādītu kolekciju (PĀRVEIDOT - mašīnas būs ievietoti csv failā)
    private static void displayCollection() {
        System.out.println("\nAutomobiļu kolekcija:");
        System.out.println("1 - Auto №1 (BMW, 2020)");
        System.out.println("2 - Auto №2 (Audi, 2019)");
        System.out.println("3 - Auto №3 (Mercedes, 2021)");
        System.out.println("4 - Auto №4 (Volkswagen, 2022)");
    }

    // Profila ieejas vai reģistrācijas metode
    private static void loginOrRegister(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nIeiet profilā:");
            System.out.println("1 - Ieiet kā lietotājs");
            System.out.println("2 - Ieiet kā administrators");
            System.out.println("3 - Pievienot jaunu profilu");
            System.out.println("4 - Atgriezties galvenajā izvēlnē");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                // Lietotāja profila ieeja
                System.out.println("\nIeiet kā lietotājs:");
                System.out.print("Lietotāja vārds (vai '0' lai atgriezties): ");
                String username = scanner.nextLine();
                if (username.equals("0")) continue;
                System.out.print("Parole (vai '0' lai atgriezties): ");
                String password = scanner.nextLine();
                if (password.equals("0")) continue;
                Person foundUser = findUser(username);
                if (foundUser != null && foundUser.validatePassword(password) && !foundUser.isAdmin()) {
                    System.out.println("Laipni lūgti, " + username + "!");
                } else {
                    System.out.println("Nederīgs lietotāja vārds vai parole, vai arī profils nav lietotāja profils.");
                }
            } else if (choice == 2) {
                // Administrātora profila ieeja
                System.out.println("\nIeiet ka administators:");
                System.out.print("Lietotāja vārds (vai '0' lai atgriezties): ");
                String username = scanner.nextLine();
                if (username.equals("0")) continue;
                System.out.print("Parole (vai '0' lai atgriezties): ");
                String password = scanner.nextLine();
                if (password.equals("0")) continue;
                Person foundUser = findUser(username);
                if (foundUser != null && foundUser.validatePassword(password) && foundUser.isAdmin()) {
                    System.out.println("Laipni lūgti, administrators " + username + "!");
                    adminMenu(scanner);
                } else {
                    System.out.println("Nederīgs lietotāja vārds vai parole, vai arī profils nav administratora profils.");
                }
            } else if (choice == 3) {
                // Jauna profila pievienošana
                System.out.println("\nPievienot jaunu profilu:");
                System.out.print("Lietotāja vārds (vai '0' lai atgriezties): ");
                String newUsername = scanner.nextLine();
                if (newUsername.equals("0")) continue;
                System.out.print("Parole (vai '0' lai atgriezties): ");
                String newPassword = scanner.nextLine();
                if (newPassword.equals("0")) continue;
                if (findUser(newUsername) == null) {
                    // Pirmais profils tiks izveidots kā administrators, citi - kā lietotāji
                    boolean isAdmin = users.isEmpty();
                    users.add(new Person(newUsername, newPassword, isAdmin));
                    saveUsers();
                    if(isAdmin){
                        System.out.println("Pirmais profils izveidots kā administrators!");
                    } else {
                        System.out.println("Profils " + newUsername + " veiksmīgi izveidots.");
                    }
                } else {
                    System.out.println("Lietotājs ar šādu lietotājvārdu jau pastāv.");
                }
            } else if (choice == 4) {
                System.out.println("Atgriežamies galvenajā izvēlnē...");
            } else {
                System.out.println("Nederīga ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 4);
    }

    // Administratora izvēlnes metode (PĀRVEIDOT - pievienot funkcionalitāti)
    private static void adminMenu(Scanner scanner) {
        int adminChoice;
        do {
            System.out.println("\nАдминистративное меню:");
            System.out.println("1 - Удалить профиль");
            System.out.println("2 - Показать список профилей");
            System.out.println("0 - Выход из административного меню");
            System.out.print("Ievadiet izvēli: ");
            adminChoice = scanner.nextInt();
            scanner.nextLine();

            if (adminChoice == 1) {
                deleteUser(scanner);
            } else if (adminChoice == 2) {
                listUsers();
            } else if (adminChoice == 0) {
                System.out.println("Izeja no administratora izvēlnes...");
            } else {
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (adminChoice != 0);
    }

    // Lietotāja dzēšanas metode
    private static void deleteUser(Scanner scanner) {
        System.out.print("Ievadiet lietotāja vārdu, kuru vēlaties dzēst (vai '0' lai atgrieztos): ");
        String usernameToDelete = scanner.nextLine();
        if (usernameToDelete.equals("0")) {
            System.out.println("Atcelts.");
            return;
        }
        Person userToDelete = findUser(usernameToDelete);
        if (userToDelete != null) {
            // Var dzēst tikai lietotājus, kas nav administrators
            if (userToDelete.isAdmin()) {
                System.out.println("Administrators nevar tikt dzēsts.");
                return;
            }
            users.remove(userToDelete);
            saveUsers();
            System.out.println("Profils " + usernameToDelete + " veiksmīgi dzēsts.");
        } else {
            System.out.println("Lietotājs ar šādu lietotājvārdu nav atrasts.");
        }
    }

    // Lietotāju saraksts
    private static void listUsers() {
        System.out.println("\nLietotāju saraksts:");
        for (Person user : users) {
            System.out.println("Lietotājs: " + user.getUsername() + " (Administrators: " + user.isAdmin() + ")");
        }
    }

    // Metode, lai atrastu lietotāju pēc lietotājvārda
    private static Person findUser(String username) {
        for (Person user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Lieotāju ielāde no CSV faila
    private static void loadUsers() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    boolean isAdmin = Boolean.parseBoolean(parts[2].trim());
                    users.add(new Person(username, password, isAdmin));
                }
            }
        } catch (IOException e) {
            System.out.println("Lietotāju ievades kļūda " + e.getMessage());
        }
    }

    // Metode, lai saglabātu lietotājus CSV failā
    private static void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (Person user : users) {
                pw.println(user.getUsername() + "," + user.getPassword() + "," + user.isAdmin());
            }
        } catch (IOException e) {
            System.out.println("Kļūda, saglabājot lietotājus: " + e.getMessage());
        }
    }

    // Metode, lai sazinātos ar mums
    private static void contactUs(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nSazināties ar mums:");
            System.out.println("1 - Sazināties");
            System.out.println("2 - Atgriezties galvenajā izvēlnē");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                int subChoice;
                do {
                    System.out.println("\nSaziņa ar mums:");
                    System.out.println("1 - Atstāt atsauksmi");
                    System.out.println("2 - Paziņot par kļūdu");
                    System.out.println("3 - Iedot savu ideju");
                    System.out.println("4 - Atgriezties galvenajā izvēlnē");
                    System.out.print("Ievadiet izvēli: ");
                    subChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (subChoice == 1) {
                        System.out.println("Lūdzu, atstājiet savu atsauksmi.");
                    } else if (subChoice == 2) {
                        System.out.println("Lūdzu, aprakstiet kļūdu.");
                    } else if (subChoice == 3) {
                        System.out.println("Lūdzu, aprakstiet savu ideju.");
                    } else if (subChoice == 4) {
                        System.out.println("Atgriežamies galvenajā izvēlnē...");
                    } else {
                        System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
                    }
                } while (subChoice != 4);
            } else if (choice == 2) {
                System.out.println("Atgriežamies galvenajā izvēlnē...");
            } else {
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 2);
    }

    // Person klase
    static class Person {
        private String username;
        private String password;
        private boolean admin;

        public Person(String username, String password, boolean admin) {
            this.username = username;
            this.password = password;
            this.admin = admin;
        }

        public String getUsername() {
            return username;
        }
        
        public String getPassword() {
            return password;
        }

        public boolean isAdmin() {
            return admin;
        }

        public boolean validatePassword(String password) {
            return this.password.equals(password);
        }
    }
}