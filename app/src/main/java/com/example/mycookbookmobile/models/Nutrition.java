package com.example.mycookbookmobile.models;

public class Nutrition {
    public static final String TABLE_NAME = "nutritions";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_CARBS = "carbs";
    public static final String COLUMN_PROTEINS = "proteins";
    public static final String COLUMN_FAT = "fat";
    public static final String COLUMN_DISH_NAME = "dish_name";

    private int id;
    private int calories;
    private int carbs;
    private int proteins;
    private int fat;
    private String dishName;

    public Nutrition(int id, int calories, int carbs, int proteins, int fat, String dishName) {
        this.id = id;
        this.calories = calories;
        this.carbs = carbs;
        this.proteins = proteins;
        this.fat = fat;
        this.dishName = dishName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}
