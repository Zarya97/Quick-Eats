import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		final int TOTAL_RECIPES = 2;		// Total amount of recipes in the application
		
		File recipeFile = new File("recipeList.txt");	// File that reads the recipes and creates objects for them
		Recipes[] recipes = new Recipes[TOTAL_RECIPES];	// Recipes array to hold recipes
		Scanner recipeList = new Scanner(recipeFile);
		Scanner empty = new Scanner(System.in);
		int recipeCount = 0;
		// Code to create objects from the input file and put them into the array
		while(recipeList.hasNext()) {				// While input file has another line to read
			Recipes recipe = new Recipes();			// Creates new recipe object
			recipe.setName(recipeList.nextLine());	// Sets name of recipe object from text file
		
			String recipeIngredients = recipeList.nextLine();	// Creates string that holds ingredients from file
			String[] temp = recipeIngredients.split(",");		// Splits string into an array with each ingredient 
															// being a single element of the array
			recipe.setIngredients(temp);		// passes array to method that copies ingredients to the recipe object
			
			int numberSteps = Integer.parseInt(recipeList.nextLine());	// reads string of number of steps and turns into int
			recipe.setSteps(recipeList, numberSteps);	// Reads n number of lines from input file based on number of steps
	
			recipes[recipeCount] = recipe;				// Adds the recipe object to the Recipes array
			recipeCount += 1;
			
			//empty.nextLine();				// Reads newline that is used in text file for organization
			recipe.printRecipe();		// Prints contents of recipe object
		}
		
		recipeList.close();	// Closes IO stream
	}
	
}
