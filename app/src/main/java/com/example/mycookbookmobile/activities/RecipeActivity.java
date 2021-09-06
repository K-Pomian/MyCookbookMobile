package com.example.mycookbookmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.mycookbookmobile.R;
import com.example.mycookbookmobile.database.DatabaseOpenHelper;
import com.example.mycookbookmobile.utils.FileReader;

public class RecipeActivity extends AppCompatActivity {
    private static final String TAG = RecipeActivity.class.getSimpleName();
    private static final String DISH_NAME = "dish_name";

    private FileReader fileReader;

    private TextView recipeDescriptionTextView;
    private TextView recipeNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        fileReader = new FileReader(this);

        recipeNameTextView = findViewById(R.id.recipe_name);
        recipeNameTextView.setText(getIntent().getExtras().getString(DISH_NAME));

        recipeDescriptionTextView = findViewById(R.id.recipe_description);
        recipeDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        String recipe = fileReader.getRecipe(getIntent().getExtras().getString(DISH_NAME));
        setRecipe(recipe);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setRecipe(String recipe) {
        TextView recipeTextView = findViewById(R.id.recipe_description);
        recipeTextView.setText(recipe);
    }
}