package lv.rvt;

import java.util.*;
import java.io.*;

public class Main {
    private static ArrayList<Person> users = new ArrayList<>();
    private static final String CSV_FILE = "data/users.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Загружаем пользователей из CSV
        loadUsers();
        
        if(users.isEmpty()){
            System.out.println("Нет пользователей. Создайте первого пользователя (администратор).");
        }

        int choice;
        do {
            System.out.println("\nCarObka - коллекция автомобилей");
            System.out.println("1 - Коллекция");
            System.out.println("2 - Войти в профиль");
            System.out.println("3 - Связаться с нами");
            System.out.println("0 - Выход");
            System.out.print("Введите номер выбора: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // очищаем буфер

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
                    System.out.println("Спасибо, что воспользовались CarObka!");
                    break;
                default:
                    System.out.println("Неверный ввод, попробуйте ещё раз.");
            }
        } while (choice != 0);
    }

    // Метод для отображения коллекции автомобилей
    private static void displayCollection() {
        System.out.println("\nКоллекция автомобилей:");
        System.out.println("1 - Автомобиль №1 (BMW, 2020)");
        System.out.println("2 - Автомобиль №2 (Audi, 2019)");
        System.out.println("3 - Автомобиль №3 (Mercedes, 2021)");
        System.out.println("4 - Автомобиль №4 (Tesla, 2022)");
    }

    // Метод для входа в профиль или регистрации
    private static void loginOrRegister(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nВойти в профиль");
            System.out.println("1 - Войти как пользователь");
            System.out.println("2 - Войти как администратор");
            System.out.println("3 - Добавить профиль");
            System.out.println("4 - Вернуться в главное меню");
            System.out.print("Введите номер выбора: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                // Вход в пользовательский профиль
                System.out.println("\nВход в профиль пользователя:");
                System.out.print("Имя пользователя (или '0' для возврата): ");
                String username = scanner.nextLine();
                if (username.equals("0")) continue;
                System.out.print("Пароль (или '0' для возврата): ");
                String password = scanner.nextLine();
                if (password.equals("0")) continue;
                Person foundUser = findUser(username);
                if (foundUser != null && foundUser.validatePassword(password) && !foundUser.isAdmin()) {
                    System.out.println("Добро пожаловать, " + username + "!");
                } else {
                    System.out.println("Неверное имя пользователя или пароль, либо профиль не является пользовательским.");
                }
            } else if (choice == 2) {
                // Вход в профиль администратора
                System.out.println("\nВход в профиль администратора:");
                System.out.print("Имя пользователя (или '0' для возврата): ");
                String username = scanner.nextLine();
                if (username.equals("0")) continue;
                System.out.print("Пароль (или '0' для возврата): ");
                String password = scanner.nextLine();
                if (password.equals("0")) continue;
                Person foundUser = findUser(username);
                if (foundUser != null && foundUser.validatePassword(password) && foundUser.isAdmin()) {
                    System.out.println("Добро пожаловать, администратор " + username + "!");
                    adminMenu(scanner);
                } else {
                    System.out.println("Неверное имя пользователя или пароль, либо профиль не является администраторским.");
                }
            } else if (choice == 3) {
                // Добавление нового профиля
                System.out.println("\nДобавить профиль:");
                System.out.print("Имя пользователя (или '0' для возврата): ");
                String newUsername = scanner.nextLine();
                if (newUsername.equals("0")) continue;
                System.out.print("Пароль (или '0' для возврата): ");
                String newPassword = scanner.nextLine();
                if (newPassword.equals("0")) continue;
                if (findUser(newUsername) == null) {
                    // Первый пользователь становится администратором, все остальные — пользователями
                    boolean isAdmin = users.isEmpty();
                    users.add(new Person(newUsername, newPassword, isAdmin));
                    saveUsers();
                    if(isAdmin){
                        System.out.println("Первый профиль создан как администратор!");
                    } else {
                        System.out.println("Профиль успешно добавлен!");
                    }
                } else {
                    System.out.println("Пользователь с таким именем уже существует!");
                }
            } else if (choice == 4) {
                System.out.println("Возврат в главное меню...");
            } else {
                System.out.println("Неверный ввод, попробуйте ещё раз.");
            }
        } while (choice != 4);
    }

    // Административное меню для дополнительных действий (например, удаление профиля)
    private static void adminMenu(Scanner scanner) {
        int adminChoice;
        do {
            System.out.println("\nАдминистративное меню:");
            System.out.println("1 - Удалить профиль");
            System.out.println("2 - Показать список профилей");
            System.out.println("0 - Выход из административного меню");
            System.out.print("Введите номер выбора: ");
            adminChoice = scanner.nextInt();
            scanner.nextLine();

            if (adminChoice == 1) {
                deleteUser(scanner);
            } else if (adminChoice == 2) {
                listUsers();
            } else if (adminChoice == 0) {
                System.out.println("Выход из административного меню...");
            } else {
                System.out.println("Неверный ввод, попробуйте ещё раз.");
            }
        } while (adminChoice != 0);
    }

    // Метод для удаления пользователя
    private static void deleteUser(Scanner scanner) {
        System.out.print("Введите имя пользователя для удаления (или '0' для отмены): ");
        String usernameToDelete = scanner.nextLine();
        if (usernameToDelete.equals("0")) {
            System.out.println("Отмена удаления.");
            return;
        }
        Person userToDelete = findUser(usernameToDelete);
        if (userToDelete != null) {
            // Можно добавить дополнительную проверку, например, чтобы администратор не удалил свой собственный профиль.
            users.remove(userToDelete);
            saveUsers();
            System.out.println("Профиль '" + usernameToDelete + "' успешно удалён.");
        } else {
            System.out.println("Пользователь с таким именем не найден.");
        }
    }

    // Метод для отображения списка пользователей
    private static void listUsers() {
        System.out.println("\nСписок профилей:");
        for (Person user : users) {
            System.out.println("Пользователь: " + user.getUsername() + " (Админ: " + user.isAdmin() + ")");
        }
    }

    // Метод для поиска пользователя по имени
    private static Person findUser(String username) {
        for (Person user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Метод для загрузки пользователей из CSV-файла
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
            System.out.println("Ошибка при загрузке пользователей: " + e.getMessage());
        }
    }

    // Метод для сохранения пользователей в CSV-файл
    private static void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (Person user : users) {
                pw.println(user.getUsername() + "," + user.getPassword() + "," + user.isAdmin());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении пользователей: " + e.getMessage());
        }
    }

    // Метод для раздела "Связаться с нами"
    private static void contactUs(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nСвязь с нами:");
            System.out.println("1 - Связаться");
            System.out.println("2 - Вернуться в главное меню");
            System.out.print("Введите номер выбора: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                int subChoice;
                do {
                    System.out.println("\nСвязь:");
                    System.out.println("1 - Оставить отзыв");
                    System.out.println("2 - Сообщить об ошибке");
                    System.out.println("3 - Предложить идею для нового раздела");
                    System.out.println("4 - Вернуться в главное меню");
                    System.out.print("Введите номер выбора: ");
                    subChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (subChoice == 1) {
                        System.out.println("Пожалуйста, оставьте свой отзыв.");
                    } else if (subChoice == 2) {
                        System.out.println("Пожалуйста, опишите ошибку.");
                    } else if (subChoice == 3) {
                        System.out.println("Пожалуйста, предложите идею для нового раздела.");
                    } else if (subChoice == 4) {
                        System.out.println("Возврат в главное меню...");
                    } else {
                        System.out.println("Неверный ввод, попробуйте снова.");
                    }
                } while (subChoice != 4);
            } else if (choice == 2) {
                System.out.println("Возврат в главное меню...");
            } else {
                System.out.println("Неверный ввод, попробуйте снова.");
            }
        } while (choice != 2);
    }

    // Класс Person для хранения данных пользователя
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