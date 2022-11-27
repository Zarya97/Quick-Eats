package com.example.quickeats;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowRecipesActivity extends AppCompatActivity {

    private Button goBack;          // Button used to Finish Activity
    private Button getRecipes;      // Button Used to Display Recipes
    private Button randomRecipe;    // Button Used to randomly display a recipe if the user doesn't have any or doesn't like the options
    private int randNum;            // Number that creates a random array position of the recipeList array list
    private ArrayList<Recipe> validRecipes = new ArrayList<>();  // Array List used to hold recipes matching the ingredients the user selected
    private ArrayList<Recipe> randRecipes  = new ArrayList<>();  // Array List used to hold random recipes from random button
    private StringBuilder display = new StringBuilder();         // String Builder used to display information on the screen
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {        // This method is invoked when the activity is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipes_layout);

        Intent intent = getIntent();        // Gets intent from the activity call
        ArrayList<Recipe> recipeList = intent.getParcelableArrayListExtra("RecipeList");     // List of Recipes
        ArrayList<String> chosenIngr = intent.getStringArrayListExtra("ChosenIngr");  // List of ingredient Strings
        scrollView = findViewById(R.id.scrollView);         // variable to control scroll view
        display = displayChosenIngr(chosenIngr);      // Returns a string builder of ingredients selected

        TextView titleView = (TextView) findViewById(R.id.titleView);       // Finds textView in Layout
        titleView.setText("Chosen Ingredients");                            // Sets the titleView with set Text
        TextView messageView = (TextView) findViewById(R.id.messageView);   // Finds messageView in layout by ID
        messageView.setText(display.toString());                             // Sets text in messageView as the stringBuilder of ingredients Chosen


        randomRecipe = findViewById(R.id.random);                       // Button used to randomize recipe
        randomRecipe.setOnClickListener(new View.OnClickListener(){     // Sets On Click Listener for random button
            @Override
            public void onClick(View view){                             // When the random button is clicked.
                randRecipes.clear();                                    // It clears any previous recipes from other clicks
                if (!getRecipes.isEnabled())                            // If the get recipes button is not enabled
                    getRecipes.setEnabled(true);                        // it is set to enabled so the user can access the original recipes given
                for (int i = 0; i < 5; i++) {                           // for five iterations
                    randNum = (int) (Math.random() * (recipeList.size() - 1));  // a random number 0 through the recipeList size is selected
                    if (!randRecipes.contains(recipeList.get(randNum)))         // If the randRecipe list doesn't already contain the recipe
                        randRecipes.add(recipeList.get(randNum));               // the recipe is added to the random list
                }
                display = createRecipeOutput(randRecipes);          // a display of the random recipes is created
                messageView.setText(display);                       // The random recipes are displayed on the screen
                scrollView.fullScroll(ScrollView.FOCUS_UP);         // Scrolls the user back to the top
            }
        });

         goBack = findViewById(R.id.goBack);                        // Button that goes back to the Main Activity
         goBack.setOnClickListener(new View.OnClickListener(){      // Sets On CLick Listener for Go Back Button
             @Override
             public void onClick(View view) {                       // When the go back button is clicked
              //   if (!validRecipes.isEmpty()){                      // If the valid recipes list is not empty
                  //   for (int i = 0; i < validRecipes.size(); i++)  // it goes through the entire list
                   //      validRecipes.get(i).resetBoolSame();       // and resets the boolSame value for each recipe object
              //   }
                 finish();                              // The activity is finished and goes back to main activity
             }
         });

         getRecipes = findViewById(R.id.getRecipes);        // Button to Display Recipes
         getRecipes.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view) {                           // When the get recipes button is clicked
                 titleView.setText("Recipes");                          // Title is set to display "Recipes"
                 randomRecipe.setVisibility(View.VISIBLE);               // The random recipes button is set to visible
                if (validRecipes.isEmpty()) {                           // if there is nothing in the valid recipes list
                    for (int i = 0; i < recipeList.size(); i++) {       // for the amount of recipes in the app
                        recipeList.get(i).compareIngrHere(chosenIngr);  // each recipe is compared with the ingredients selected
                        if (recipeList.get(i).getBoolSame())            // if the compare method has set the bool field of the recipe to true
                            validRecipes.add(recipeList.get(i));        // the recipe is added to the valid recipes list
                    }
                }
                if(!validRecipes.isEmpty()) {                       // If the valid recipes list is not empty
                    display = createRecipeOutput(validRecipes);             // a string builder is created of the valid recipes
                    messageView.setText(display.toString());               // the valid recipes are displayed on the screen
                }
                else
                    messageView.setText(getString(R.string.noRecipes));  // The app tells the user to try a random recipe
                 getRecipes.setEnabled(false);                          // the get recipes button is set to false
                 scrollView.fullScroll(ScrollView.FOCUS_UP);         // Scrolls the user back to the top
             }
         });
    }
    /****************************************************************************
     * Method: createRecipeOutput
     * Description: This method is passed an array list of recipes and returns a full string builder
     * of them all to display in the message text view on the screen
     * @param recipeList: ArrayList<Recipe>
     ***************************************************************************/
    private StringBuilder createRecipeOutput(ArrayList<Recipe> recipeList){
        StringBuilder recipeOutput = new StringBuilder();               // New string builder to return

        for (int i = 0; i < recipeList.size(); i++){                    // for the size of the passed recipe list
            recipeOutput.append(recipeList.get(i).getName() + "\n\nIngredients: \n");       // the name of the recipe is appended
            for (int j = 0; j < recipeList.get(i).getRecipeHas().size(); j++){              // for amount of ingredients each recipe has
                String string = String.format("%s %s\n", recipeList.get(i).getUnits().get(j),   // creates string of ingredient units and ingredient name of recipe
                        recipeList.get(i).getRecipeHas().get(j).getText());
                recipeOutput.append(string);                            // Appends string to string builder
            }
            if(!recipeList.get(i).getMissingIngr().isEmpty()) {                 // If the Missing ingredients list is not empty
                for (int j = 0; j < recipeList.get(i).getMissingIngr().size(); j++) {       // for the amount of Ingredients missing
                    if (j == 0)                                             // If it's the first iteration
                        recipeOutput.append("\nMissing Ingredients:\n");    // Missing Ingredient header is appended
                    recipeOutput.append(recipeList.get(i).getMissingIngr().get(j).getText() + "\n");  // Appends the name of the missing ingredient
                }
            }
            for (int j = 0; j < recipeList.get(i).getSteps().size(); j++)                   // for the amount of steps in each recipe
                recipeOutput.append("\n" + recipeList.get(i).getSteps().get(j) + "\n");     // each step is appended to string builder
            String separator = getString(R.string.seperator);          // gets string from xml files
            if(i != recipeList.size() - 1)                // if the the iteration is not on the last recipe of the list
                recipeOutput.append(separator);            // the separator is appended to the string builder
        }
        return recipeOutput;            // The string builder is returned to be displayed
    }
    /****************************************************************************
     * Method: displayChosenIngr
     * Description: This method is passed an Array List of strings which are the names
     * of the ingredients the user has chosen and creates a string builder to return
     * that is used to display the ingredients to the user and confirm they added all that they
     * wanted to
     * @param ingrChosen: ArrayList<String>
     ***************************************************************************/
    private StringBuilder displayChosenIngr(ArrayList<String> ingrChosen){
        StringBuilder message = new StringBuilder();                        // new string builder to return
        message.append("Are these all the ingredients you selected?\n\n");  // appends prompt message to string builder
        for(int i = 0; i < ingrChosen.size(); i++) {            // for the amount of ingredients chosen
            message.append(ingrChosen.get(i) + "\n");           // the ingredient is appended to the string builder
        }
        return message;             // the string builder is returned
    }
}