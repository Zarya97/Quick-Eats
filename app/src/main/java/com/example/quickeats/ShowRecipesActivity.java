package com.example.quickeats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowRecipesActivity extends AppCompatActivity {

    private Button goBack;          // Button used to Finish Activity
    private Button getRecipes;      // Button Used to Display Recipes
    ArrayList<Recipe> validRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipes_layout);

        Intent intent = getIntent();        // Gets intent from the activity call
        ArrayList<Recipe> recipeList = intent.getParcelableArrayListExtra("RecipeList");     // List of Recipes
        ArrayList<String> chosenIngr = intent.getStringArrayListExtra("ChosenIngr");  // List of ingredient Strings
        StringBuilder display = displayChosenIngr(chosenIngr);      // Returns a string builder of ingredients selected




        TextView titleView = (TextView) findViewById(R.id.titleView);       // Finds textView in Layout
        titleView.setText("Recipes");                                       // Sets the titleView with set Text
        TextView messageView = (TextView) findViewById(R.id.messageView);   // Finds messageView in layout by ID

        messageView.setText(display.toString());            // Sets text in messageView as teh stringBuilder of ingredients Chosen


         goBack = findViewById(R.id.goBack);                        // Button that goes back to the Main Activity
         goBack.setOnClickListener(new View.OnClickListener(){      // Sets On CLick Listener for Go Back Button
             @Override
             public void onClick(View view) {
                 if (!validRecipes.isEmpty()){
                     for (int i = 0; i < validRecipes.size(); i++)
                         validRecipes.get(i).resetBoolSame();
                 }
                 validRecipes.clear();
                 finish();      // This button closes the Activity when it's clicked and goes back to Main Activity
             }
         });

         getRecipes = findViewById(R.id.getRecipes);        // Button to Display Recipes
         getRecipes.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view) {

                 for (int i = 0; i < recipeList.size();i++){
                     recipeList.get(i).compareIngrHere(chosenIngr);
                     if (recipeList.get(i).getBoolSame()) {
                         validRecipes.add(recipeList.get(i));
                         System.out.println(validRecipes.size());
                     }
                 }
                 StringBuilder recipeDisplay = createRecipeOutput(validRecipes);
                 messageView.setText(recipeDisplay.toString());
                 getRecipes.setEnabled(false);
             }
         });
    }

    private StringBuilder createRecipeOutput(ArrayList<Recipe> recipeList){
        StringBuilder recipeOutput = new StringBuilder();

        for (int i = 0; i < recipeList.size(); i++){
            recipeOutput.append(recipeList.get(i).getName() + "\n\nIngredients: \n");
            for (int j = 0; j < recipeList.get(i).getRecipeHas().size(); j++){
                String string = String.format("%s %s\n", recipeList.get(i).getUnits().get(j),
                        recipeList.get(i).getRecipeHas().get(j).getText());
                recipeOutput.append(string);
            }
            for (int j = 0; j < recipeList.get(i).getSteps().size(); j++)
                recipeOutput.append("\n" + recipeList.get(i).getSteps().get(j) + "\n");
            String seperator = getString(R.string.seperator);
            if(i != recipeList.size() - 1)
                recipeOutput.append(seperator);
        }

        return recipeOutput;
    }

    private StringBuilder displayChosenIngr(ArrayList<String> ingrChosen){
        StringBuilder message = new StringBuilder();
        message.append("Are these all the ingredients you selected?\n\n");

        for(int i = 0; i < ingrChosen.size(); i++)
        {
            message.append(ingrChosen.get(i) + "\n");
        }
        return message;
    }



}
