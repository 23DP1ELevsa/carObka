package lv.rvt.tools;

import java.util.ArrayList;
import java.util.Objects;

public class Car {
    static ArrayList<Car> cars = new ArrayList<>();
    public static final String CARS_FILE = "data/cars.csv";
    
    // Car klase ar papildu lauku degvielas tipam
    private final String brand;
    private final String model;
    private final int year;
    private final int horsepower;
    private final String fuelType;
    private final String drive;
    private final String generation;
    private final double fuelConsumption; // Jauns lauks: vidējais degvielas patēriņš
    private final int price; // Jauns lauks: cena
    private final String description;

    public Car(String brand, String model, int year, int horsepower, String fuelType, String drive, String generation, double fuelConsumption, int price, String description) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.horsepower = horsepower;
        this.fuelType = fuelType;
        this.drive = drive;
        this.generation = generation;
        this.fuelConsumption = fuelConsumption;
        this.price = price;
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getDrive() {
        return drive;
    }

    public String getGeneration() {
        return generation;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return year == car.year &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, year);
    }
}

