package com.example.mycookbookmobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mycookbookmobile.models.Dish;
import com.example.mycookbookmobile.models.Nutrition;
import com.example.mycookbookmobile.utils.FileReader;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mycookbook_db";
    private Context context;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        fillDatabase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertDish(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Dish.COLUMN_NAME, name);
        contentValues.put(Dish.COLUMN_DESCRIPTION, description);

        long id = db.insert(Nutrition.TABLE_NAME, null, contentValues);

        return id;
    }

    public long insertNutrition(int calories, int carbs, int proteins, int fat, String dishName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Nutrition.COLUMN_CALORIES, calories);
        contentValues.put(Nutrition.COLUMN_CARBS, carbs);
        contentValues.put(Nutrition.COLUMN_PROTEINS, proteins);
        contentValues.put(Nutrition.COLUMN_FAT, fat);
        contentValues.put(Nutrition.COLUMN_DISH_NAME, dishName);

        long id = db.insert(Nutrition.TABLE_NAME, null, contentValues);

        return id;
    }

    public Dish getDishByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Dish.TABLE_NAME, new String[]{Dish.COLUMN_ID, Dish.COLUMN_NAME,
                        Dish.COLUMN_DESCRIPTION},
                Dish.COLUMN_NAME + "=?",
                new String[]{name},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (cursor.getCount() == 0) {
            return null;
        }

        Dish dish = new Dish(
                cursor.getInt(cursor.getColumnIndex(Dish.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Dish.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Dish.COLUMN_DESCRIPTION))
        );

        cursor.close();

        return dish;
    }

    public Dish getDishByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Dish.TABLE_NAME, new String[]{Dish.COLUMN_ID, Dish.COLUMN_NAME, Dish.COLUMN_DESCRIPTION},
                Dish.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Dish dish = new Dish(
                cursor.getInt(cursor.getColumnIndex(Dish.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Dish.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Dish.COLUMN_DESCRIPTION))
        );

        cursor.close();

        return dish;
    }

    public Nutrition getNutritionByDishName(String dishName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Nutrition.TABLE_NAME, new String[]{Nutrition.COLUMN_ID, Nutrition.COLUMN_CALORIES,
                        Nutrition.COLUMN_CARBS, Nutrition.COLUMN_PROTEINS, Nutrition.COLUMN_FAT, Nutrition.COLUMN_DISH_NAME},
                Nutrition.COLUMN_DISH_NAME + "=?",
                new String[]{dishName},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Nutrition nutrition = new Nutrition(
                cursor.getInt(cursor.getColumnIndex(Nutrition.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Nutrition.COLUMN_CALORIES)),
                cursor.getInt(cursor.getColumnIndex(Nutrition.COLUMN_CARBS)),
                cursor.getInt(cursor.getColumnIndex(Nutrition.COLUMN_PROTEINS)),
                cursor.getInt(cursor.getColumnIndex(Nutrition.COLUMN_FAT)),
                cursor.getString(cursor.getColumnIndex(Nutrition.COLUMN_DISH_NAME))
        );

        cursor.close();

        return nutrition;
    }

    public void fillDatabase(SQLiteDatabase db) {
        FileReader fileReader = new FileReader(context);
        db.execSQL(fileReader.getSqlScript("create_dishes"));
        db.execSQL(fileReader.getSqlScript("create_nutritions"));
        db.execSQL(fileReader.getSqlScript("insert_dishes"));
        db.execSQL(fileReader.getSqlScript("insert_nutritions"));
    }

    public int getNumberOfDishRecords() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Dish.TABLE_NAME, null);
        int count = cursor.getCount();

        return count;
    }
}
