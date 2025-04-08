package lv.rvt.tools;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String username;
    private String password;
    private boolean admin;
    private List<Car> favorites; // Saraksts iemīļotajām mašīnām

    public Person(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.favorites = new ArrayList<>();
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

