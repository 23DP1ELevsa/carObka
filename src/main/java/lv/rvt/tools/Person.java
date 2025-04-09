package lv.rvt.tools;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String username;
    private String password;
    private boolean admin;
    private List<Car> favorites; // Saraksts iemīļotajām mašīnām
    private String textcolor;

    public Person(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.favorites = new ArrayList<>();
        setColor();
    }

    public Person(String username, String password) {
        this(username, password, false);
        setColor();
    }
    public Person() {
        this("", "", false);
        setColor();
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setColor() {
        if (admin) {
            textcolor = ConsoleColors.RED;
        } else if (username.equals("")) {
            textcolor = ConsoleColors.GREEN;
        } else {
            textcolor = ConsoleColors.CYAN;
        }
    }

    public String getColor() {
        return textcolor;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public List<Car> getFavorites() {
        return favorites;
    }

    public void addFavorite(Car car) {
        favorites.add(car);
    }

    public void removeFavorite(Car car) {
        favorites.remove(car);
    }

    public int getFavoriteCount() {
        return favorites.size();
    }
}

