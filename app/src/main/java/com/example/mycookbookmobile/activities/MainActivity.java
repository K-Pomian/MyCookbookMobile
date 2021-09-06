package com.example.mycookbookmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.mycookbookmobile.R;
import com.example.mycookbookmobile.database.DatabaseOpenHelper;
import com.example.mycookbookmobile.fragments.DishFragment;
import com.example.mycookbookmobile.models.Dish;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DISH_FRAGMENT_TAG = DishFragment.class.getSimpleName();

    private DatabaseOpenHelper db;

    private DishFragment dishFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseOpenHelper(this);
        db.getReadableDatabase();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        displayFragment();

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY).toLowerCase();
            String dishName = query.substring(0, 1).toUpperCase() + query.substring(1);
            Bundle bundle = new Bundle();
            bundle.putString("dishName", dishName);
            dishFragment.setArguments(bundle);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        return true;
    }

    @Override
    public boolean onSearchRequested() {
        super.onSearchRequested();
        return true;
    }

    private void displayFragment() {
        dishFragment = DishFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.dish_fragment, dishFragment, DISH_FRAGMENT_TAG).commitNow();
    }

    public void showRecipe(View view) {
        dishFragment.showRecipe(view);
    }

    public void nextRecipe(View view) {
        dishFragment.nextRecipe(view);
    }

    public void previousRecipe(View view) {
        dishFragment.previousRecipe(view);
    }

}