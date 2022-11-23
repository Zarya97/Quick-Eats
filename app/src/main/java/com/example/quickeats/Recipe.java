package com.example.quickeats;

import java.util.ArrayList;
import java.util.Scanner;

public class Recipe {
    private String name;                        //Private field used to identify the recipe name
    private ArrayList<String> ingredientUnits = new ArrayList<>();             // Units for the ingredients used
    private ArrayList<String> steps = new ArrayList<>();            // Private field used to hold steps in a string array
    private ArrayList<IngClass> recipeHas = new ArrayList<>();    // Private field used to identify the ingredients for the recipe
    private boolean boolSame = true;                                // Boolean value used to compare if recipe matches with ingredients selected

    Recipe(ArrayList<IngClass> ingrAll, Scanner recipeFile) {        // Recipe Constructor
        setName(recipeFile);                                        // calls setName method
        setIngredients(recipeFile, ingrAll);                        // calls setIngredients method
        setUnits(recipeFile);
        setSteps(recipeFile);                                        // calls setSTeps method
    }

    /****************************************************************************
     * Method: setName
     * Description: takes file and reads String. Sets name field of Recipe object
     * @param inFile
     ***************************************************************************/
    public void setName(Scanner inFile) {
        this.name = inFile.nextLine();                // Sets the name of this recipe Object to the string read from file
    }

    /****************************************************************************
     * Method: setIngredients
     * Description: Takes input file and arrayList of ingredients. It reads the number of ingredients for recipe from file
     * 		and then adds the ingredients to recipeHas ArrayList based on ID number of Ingredient from ingrAll ArrayList
     * @param inFile, ingrAll
     ***************************************************************************/
    public void setIngredients(Scanner inFile, ArrayList<IngClass> ingrAll) {
        int numIngredients = inFile.nextInt();                // NUmber of ingredients for recipe
        for (int i = 0; i < numIngredients; i++) {            // for number of ingredients
            recipeHas.add(ingrAll.get(inFile.nextInt()));    // add the ingredient based on ID number to recipeHas ArrayList
        }
        String empty = inFile.nextLine();        // Used to read empty \n after int
    }


    public ArrayList<String> getUnits(){
            return this.ingredientUnits;
    }

    public ArrayList<IngClass> getRecipeHas(){
        return this.recipeHas;
    }
    public ArrayList<String> getSteps(){
        return this.steps;
    }

    /****************************************************************************
     * Method: setSteps
     * Description: Takes input file and reads number of steps for Recipe. It then iterates through the number of steps
     * 				and reads each one. It then adds it to the steps ArrayList of the recipe Object
     * @param file
     ***************************************************************************/
    public void setSteps(Scanner file) {
        int numSteps = file.nextInt();            // Reads number of steps for the recipe
        file.nextLine();    // Reads newLine after the number
        for (int i = 0; i < numSteps; i++) {    // for number of steps
            this.steps.add(file.nextLine());    // Read the step from the file and add it to steps ArrayList of recipe Object
        }
        file.skip("(\r\n|[\n\r\u2028\u2029\u0085])?"); // Skips newline that is used in text file for organization
    }

    /****************************************************************************
     * Method: setUnits
     * Description: This method takes a scanner file as a parameter, reads a string of units, and splits it
     * into an array using the split method. It then adds each element of the array to the ArrayList.
     * @param file
     ***************************************************************************/
    public void setUnits(Scanner file) {
        String unitString = file.nextLine();        //Reads units as a full string
        String[] tempArray = unitString.split(":");    // SPlits string into array using ":" as regex splitter
        for (int i = 0; i < tempArray.length; i++) {    // For the amount of elements in the array
            this.ingredientUnits.add(tempArray[i]);    // the unit added to the ingredientUnits ArrayList
        }
    }

    /****************************************************************************
     * Method: isNotSame
     * Description: Sets boolSame to false if recipe doesn't have same ingredients as selected
     ***************************************************************************/

    public void isNotSame() {
        this.boolSame = false;        // Sets private field boolSame to false
    }

    public boolean getBoolSame() {
        return this.boolSame;
    }


    /****************************************************************************
     * Method: compareIngr
     * Description: Takes an ArrayList of the ingredients selected as parameter and compares them to the recipes
     * ingredients. COmpares IDs and if they are the same, the IngrCount is incremented. If the ingredients counted
     * are the same as the amount in the recipe, then it is added to a validRecipes ArrayList
     ***************************************************************************/
    public void compareIngr(ArrayList<IngClass> ingrSelected) {

        int recipeIngrCount = 0;    // Count kept to see if a recipe has all ingredients that are selected

        for (int ingrSelect = 0; ingrSelect < ingrSelected.size(); ingrSelect++) {    // to compare each ingredient selected to what the recipe has
            for (int recipeIngr = 0; recipeIngr < this.recipeHas.size(); recipeIngr++) {        // to compare every single ingredient the recipe has to the j ingredient selected
                if (this.recipeHas.get(recipeIngr).getID() == ingrSelected.get(ingrSelect).getID()) {    // If ID of recipe Ingredient and selected ingredient is the same
                    recipeIngrCount += 1;
                    break;            // Ingredient matches so it breaks out of the loop
                }
            }
        }

        if (recipeIngrCount != this.recipeHas.size())    // If the ingredients counted for the recipe does not match the amount of ingredients the recipe has, it means not all of ingredients in the recipe were found
            this.isNotSame();    // This means that all of the recipes ingredients are not selected
    }


    public void printRecipe() {
        System.out.println("\nName: " + this.name + "\nIngredients:\n ");
        for (int i = 0; i < this.recipeHas.size(); i++) {
            System.out.print(String.format("%-20s%s\n", this.ingredientUnits.get(i), this.recipeHas.get(i).getText()));
        }
        System.out.println();
        for (int i = 0; i < this.steps.size(); i++) {
            System.out.println(this.steps.get(i));
        }
    }
}
