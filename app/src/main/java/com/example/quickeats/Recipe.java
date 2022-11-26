package com.example.quickeats;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Recipe implements Parcelable {
    private String name;                                                // Private field used to identify the recipe name
    private ArrayList<String> ingredientUnits = new ArrayList<>();      // Units for the ingredients used
    private ArrayList<String> steps = new ArrayList<>();                // Private field used to hold steps in a string array
    private ArrayList<IngClass> recipeHas = new ArrayList<>();          // Private field used to identify the ingredients for the recipe
    private ArrayList<Integer> missingIngr = new ArrayList<>();         // Private field used to hold the ingredients of teh Recipe the user doesn't have
    private boolean boolSame = false;                                   // Boolean value used to compare if recipe matches with ingredients selected

    Recipe(ArrayList<IngClass> ingrAll, Scanner recipeFile) {           // Recipe Constructor that uses all the setters to create recipe objects
        setName(recipeFile);                                            // calls setName method
        setIngredients(recipeFile, ingrAll);                            // calls setIngredients method
        setUnits(recipeFile);                                           // calls setUnits Method
        setSteps(recipeFile);                                           // calls setSteps method
    }

    protected Recipe(Parcel in) {                                       // This is the Constructor used to create a Recipe Parcel so Recipe Objects can be passed
        name = in.readString();                                         // to the other activity it's being used in
        ingredientUnits = in.createStringArrayList();
        recipeHas = in.createTypedArrayList(IngClass.CREATOR);
        steps = in.createStringArrayList();
        boolSame = in.readByte() != 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {           // This Creator Method just works with the calls that pass
        @Override                                                                   // the Recipe Objects to the other activity via intent
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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
        int numIngredients = inFile.nextInt();                // Reads an integer as the number of ingredients for recipe
        for (int i = 0; i < numIngredients; i++) {            // for the number of ingredients
            recipeHas.add(ingrAll.get(inFile.nextInt()));    // add the ingredient based on ID number to recipeHas ArrayList.
        }                                                    // The ID number of each Ingredient Object is it's place in the Ingredient ArrayList
        inFile.nextLine();                                   // Used to read empty \n after int
    }
    /****************************************************************************
     * Method: setSteps
     * Description: Takes input file and reads number of steps for Recipe. It then iterates through the number of steps
     * 				and reads each one. It then adds it to the steps ArrayList of the recipe Object
     * @param file
     ***************************************************************************/
    public void setSteps(Scanner file) {
        int numSteps = file.nextInt();          // Reads number of steps for the recipe
        file.nextLine();                        // Reads newLine after the number
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
        String unitString = file.nextLine();                       //Reads units for each ingredient as a full string
        String[] tempArray = unitString.split(":");         // Splits string into array using ":" as regex splitter, which is placed between each unit in the text File
        this.ingredientUnits.addAll(Arrays.asList(tempArray));    // The array holding the units is turned into an arrayList and set as ingredientUnits field of object
    }

    /****************************************************************************
     * Method: isSame
     * Description: Sets boolSame to true if recipe has the same ingredients as selected by the user
     ***************************************************************************/
    public void isSame() {this.boolSame = true; }       // Sets private field boolSame to true

    /****************************************************************************
     * Method: resetBoolSame
     * Description: This method reset the boolSame field of the recipe object after it has been set to true
     ***************************************************************************/
    public void resetBoolSame(){this.boolSame = false;}

    // All these methods are the getter methods that access the private fields of the Recipe Object
    public String getName() { return this.name;}
    public ArrayList<String> getUnits(){
        return this.ingredientUnits;
    }
    public ArrayList<IngClass> getRecipeHas(){
        return this.recipeHas;
    }
    public ArrayList<String> getSteps(){
        return this.steps;
    }
    public boolean getBoolSame() {
        return this.boolSame;
    }

    /****************************************************************************
     * Method: compareIngr
     * Description: Takes an ArrayList of strings that are the names of the ingredients selected as parameter and compares them to the recipes
     * ingredients. Compares ingredient names and if the string have the same contents, the flag is set to 1 meaning the ingredient of that
     * iteration was selected by the user. If they don't match, the flag is still 0 and the ingredient the recipe has on that
     * iteration is put in the missingIngredients field of the recipe object. At the end of the loops the ratio of
     * how many ingredients were missing and how many the recipe has is calculated. If the recipe has no missing
     * ingredients, or the user selected at least half of the ingredients the recipe uses, the boolSame value will be set to true
     * meaning the recipe is valid.
     * @param stringSelected :ArrayList of ingredients selected by user
     ***************************************************************************/
    public void compareIngrHere(ArrayList<String> stringSelected) {
        double ratioMissing = 1.0;      // Variable used to decided if the user has at least half of what the recipe needs
        int flag;                       // Catches when an ingredient has been found
        int i = 0;                      // Integer used for outer loop
        int j;                          // Integer used for nested loop
        while (i < this.recipeHas.size()) {     // While i is < then the number of ingredients the recipe has
            flag = 0;                   // Flag is initialized to 0
            j = 0;                      // Integer for nested for loop is initialized
            while ((j < stringSelected.size()) && flag == 0) {      // While j is < the amount of ingredients the user selected
                if (this.recipeHas.get(i).getText().equals(stringSelected.get(j))) {    // If the names of the ingredient selected and the recipe ingredient match
                    flag = 1;           // The flag is set to 1
                }
                j++;                    // J is incremented for the loop
            }
            if (flag == 0) {            // If flag == 0, it means the ingredient in the recipe wasn't found
                this.missingIngr.add(recipeHas.get(i).getID()); // So it is added to the missing ingredient ArrayList
            }
            i++;                        // I is increment for the loop
        }
        if (!missingIngr.isEmpty()) {       // If the missingIngredients ArrayList is NOT empty
            ratioMissing = (Double.valueOf(this.missingIngr.size()/Double.valueOf(this.recipeHas.size())));
        }   // The ratioMissing becomes the number missing ingredients over the number of ingredients in the recipe
        if(this.missingIngr.isEmpty() || ratioMissing <= 0.5){      // if the recipe is missing no ingredients or the is only missing <= half
            this.isSame();          // The recipe is valid and will be displayed.
        }
    }

    // Print Recipe only used for debugging purposes
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

    @Override
    public int describeContents() {         // This method is part of implementing the Parcelable class
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {       // This method is used to reconstruct object when it
        parcel.writeString(name);                           // is passed
        parcel.writeStringList(ingredientUnits);
        parcel.writeTypedList(recipeHas);
        parcel.writeStringList(steps);
        parcel.writeByte((byte) (boolSame ? 1 : 0));
    }
}
