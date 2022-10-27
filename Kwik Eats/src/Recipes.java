import java.util.Scanner; 

public class Recipes{
	 
		private String name;			//Private field used to identify the recipe name
		private String[] ingredients;	// Private field used to identify the ingredients for the recipe
		private String[] steps; 		// Private field used to hold steps in a string array
	
		public void setName(String name) {	//Takes a string argument and sets the name field of Recipes object 
			this.name = name;				// Sets name argument passed to name of object  
		}

		public void setIngredients(String[] ingredientArray) {	//This method takes a string array of ingredients as an argument
			
			ingredients = new String[ingredientArray.length];	// Initializes array with the length of the passed array
			for (int i = 0; i < ingredients.length; i++) {	// For the length of the String array ingredients 
				this.ingredients[i] = ingredientArray[i];	// The i element of the ingredientsArray becomes the i element of the private filed of the object
			}
		}
		public void setSteps(Scanner file, int arrayLen ){	// This method takes scanner input from file and the array length as arguements
			this.steps = new String[arrayLen];				// Initializes steps array of the object
			for (int i = 0; i < this.steps.length; i++  ) {	// 
				steps[i] = file.nextLine();
			}
		}
		
		public String getName() {			// Returns the name of the name private field of object
			return this.name;
		}
		
		public String[] getIngredients() {	// Returns ingredients string array from object
				return this.ingredients;
		}
		
		public String[] getSteps(){			// Returns the steps array of the object
			return this.steps;
		}
		
		public void printRecipe() {		// Prints the contents of the recipe object 
			System.out.print("\nRecipe: " + this.name + '\n');
			System.out.print("Ingredients: ");
			for (int i = 0; i < this.ingredients.length ; i++)
				System.out.print(ingredients[i] + ", ");
			System.out.println();
			for (int i = 0; i < this.steps.length; i++)
				System.out.println(steps[i]);
		}
		
	}