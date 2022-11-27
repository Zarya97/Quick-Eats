package com.example.quickeats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.DataInputStream;

public class MainActivity extends AppCompatActivity implements IngListener {

    RecyclerView recycler_view;
    IngAdapter adapter;
    ArrayList<String> arrayList;
    ArrayList<IngClass> ingList = new ArrayList<>();     // ArrayList that holds the ingredient Objects
    ArrayList<Recipe> recipeList = new ArrayList<>();   // ArrayList used to hold recipe objects


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view = findViewById(R.id.recycler_view);
        setRecyclerView();

        createRecipeObjects(recipeList); // Method creates objects of recipes and puts them in the Recipe ArrayList

        recipeList.get(49).printRecipe();
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {      // Event Handler for submit button
            @Override
            public void onClick(View view) {
                if (!adapter.arrayList_0.isEmpty())     // If the ArrayList of selected ingredients is NOT empty
                    changeActivity();                   // The app will go to the ShowRecipesActivity
                else {                                   // If it IS empty
                    String message = "Nothing has been Selected";  // A message that tells the user they haven't selected anything
                    ShowMessage(message);           // The message is displayed to teh user
                }
            }
        });
        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {       // Event handler for clear button
            @Override
            public void onClick(View view) {            // WHen the clear button is clicked
                adapter.arrayList_0.clear();            // Clears the contents of the user selected arraylist
                adapter = null;                         // the adapter points to nothing
                setRecyclerView();                      // It resets the recycler view
                ShowMessage("Selection Cleared");   // Lets the user know their selections were cleared
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search for Ingredient");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }

        });

        return super.onCreateOptionsMenu(menu);
    }

    private ArrayList<String> getIngData(ArrayList<IngClass> ingList) {
        arrayList = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.ingredients);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line;
        try {
            int count = 0;      // used to initialize ID of ingredient object
            while ((line = reader.readLine()) != null) {    // while file isn't empty
                IngClass ingredient = new IngClass(line, count);   // creates ingredient object
                ingList.add(ingredient);    // Adds ingredient object to ingredient array
                arrayList.add(line);        // Adds string used for ingredient name to a string
                                            // ArrayList used for check boxes.
                count += 1;                 //increments count to set ID
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading ingredients", e);
            e.printStackTrace();
        }
        return arrayList;
    }

    private void setRecyclerView() {

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IngAdapter(this, getIngData(ingList), this);
        recycler_view.setAdapter(adapter);
    }


    public ArrayList<String> onIngChange(ArrayList<String> arrayList) {
        ArrayList<String> items = new ArrayList<>();
        for (String selected : arrayList) {
            items.addAll(Collections.singleton(selected));
        }
        return items;
    }
    // This method just shows the user a message pop up on the screen
    private void ShowMessage(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /****************************************************************************
     * Method: createRecipeObjects
     * Description: This method takes an ArrayList<Recipe> parameter and reads data from a file to
     * create recipe objects and then adds them to the ArrayList
     * @param recipes : ArrayList of Recipes that holds Recipe Objects
     ***************************************************************************/
    private void createRecipeObjects(ArrayList<Recipe> recipes) {
        DataInputStream is = new DataInputStream(getResources().openRawResource(R.raw.recipe_list));    // Input Stream to create Scanner Object
        Scanner recipeList = new Scanner(is);           // Scanner object created to read the file

        while (recipeList.hasNext()) {                       // While recipeList has another line to read
            Recipe recipe = new Recipe(ingList, recipeList); // Creates new recipe object
            recipes.add(recipe);                             // Adds the recipe object to the Recipes ArrayList
        }
        recipeList.close();                                 // Closes IO stream
    }
    /****************************************************************************
     * Method: changeActivity
     * Description: This method jumps to the ShowRecipeActivity. It creates a new Intent, puts data in that
     * intent so it can be passed to the next activity, and then starts the activity
     ***************************************************************************/
private void changeActivity(){
    Intent intent = new Intent(this,ShowRecipesActivity.class); // Creates new intent to switch to second activity
    intent.putParcelableArrayListExtra("RecipeList", (ArrayList<? extends Parcelable>)recipeList);  // Passes recipeList through the intent
    intent.putStringArrayListExtra("ChosenIngr",adapter.arrayList_0);           // passes Select ingredients ArrayList through the intent
    startActivity(intent);          // Goes to next activity
    }
}