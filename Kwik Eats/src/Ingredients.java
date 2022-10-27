
public class Ingredients{
		private String name;
		private boolean chosen;

		public void setStatus(boolean selected) {
			chosen = selected;
		}

		public void setName(String name) {
			this.name = name;
		}

		Ingredients(String name){
			this.name = name;
			chosen = false;
		}

		Ingredients(){
			name = "\0";
			chosen = false;
			}

	}