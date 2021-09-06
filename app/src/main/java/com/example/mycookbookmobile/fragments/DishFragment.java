package com.example.mycookbookmobile.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycookbookmobile.R;
import com.example.mycookbookmobile.activities.RecipeActivity;
import com.example.mycookbookmobile.database.DatabaseOpenHelper;
import com.example.mycookbookmobile.models.Dish;
import com.example.mycookbookmobile.models.Nutrition;
import com.example.mycookbookmobile.utils.FileReader;
import com.google.android.material.imageview.ShapeableImageView;

public class DishFragment extends Fragment {
    private static final String TAG = DishFragment.class.getSimpleName();
    private static final String DISH_NAME = "dish_name";

    private static int currentDish = 1;

    private DatabaseOpenHelper db;
    private FileReader fileReader;

    private ShapeableImageView dishShapeableImageView;
    private TextView dishNameTextView;
    private TextView dishDescriptionTextView;
    private TextView caloriesTextView;
    private TextView carbsTextView;
    private TextView proteinsTextView;
    private TextView fatTextView;

    public DishFragment() {
    }

    public static DishFragment newInstance() {
        return new DishFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseOpenHelper(getContext());
        fileReader = new FileReader(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_dish, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dishShapeableImageView = view.findViewById(R.id.dish_image);
        dishNameTextView = view.findViewById(R.id.dish_name);
        dishDescriptionTextView = view.findViewById(R.id.dish_description);
        caloriesTextView = view.findViewById(R.id.calories_view);
        carbsTextView = view.findViewById(R.id.carbs_view);
        proteinsTextView = view.findViewById(R.id.proteins_view);
        fatTextView = view.findViewById(R.id.fat_view);

        if (getArguments() == null) {
            setDish(currentDish);
        } else {
            String dishName = getArguments().getString("dishName");
            Dish dish = db.getDishByName(dishName);
            if (dish == null) {
                Toast.makeText(getContext(), "No recipe found", Toast.LENGTH_SHORT).show();
                setDish(currentDish);
            } else {
                setDish(dish);
            }
        }

    }

    public void showRecipe(View view) {
        Intent intent = new Intent(requireActivity(), RecipeActivity.class);
        String dishName = dishNameTextView.getText().toString();
        intent.putExtra(DISH_NAME, dishName);
        startActivity(intent);
    }

    public void nextRecipe(View view) {
        if (currentDish == db.getNumberOfDishRecords()) {
            currentDish = 1;
        } else {
            currentDish++;
        }

        setDish(currentDish);
    }

    public void previousRecipe(View view) {
        if (currentDish == 1) {
            currentDish = db.getNumberOfDishRecords();
        } else {
            currentDish--;
        }

        setDish(currentDish);
    }

    private void setDish(int id) {
        Dish dish = db.getDishByID(id);

        dishNameTextView.setText(dish.getName());

        dishDescriptionTextView.setText(dish.getDescription());
        setImage();

        setNutririons();
    }

    public void setDish(Dish dish) {
        dishNameTextView.setText(dish.getName());
        dishDescriptionTextView.setText(dish.getDescription());
        currentDish = dish.getId();
        setImage();

        setNutririons();
    }

    private void setNutririons() {
        Nutrition nutrition = db.getNutritionByDishName(dishNameTextView.getText().toString());
        caloriesTextView.setText(getString(R.string.calories, nutrition.getCalories()));
        carbsTextView.setText(getString(R.string.carbs, nutrition.getCarbs()));
        proteinsTextView.setText(getString(R.string.proteins, nutrition.getProteins()));
        fatTextView.setText(getString(R.string.fat, nutrition.getFat()));

    }

    private void setImage() {
        Drawable image = fileReader.getImage(dishNameTextView.getText().toString().toLowerCase());
        dishShapeableImageView.setImageDrawable(image);
    }
}