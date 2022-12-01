package com.example.quickeats;

import android.os.Parcel;
import android.os.Parcelable;

public class IngClass implements Parcelable {
    private String text;            // Private field that holds the name of the ingredient
    private int ID;                 // Private field that holds the ID/ array position of the ingredient in mass list

    public IngClass(String text,int ID) {           // Overloaded Constructor for ingredient object
        this.text = text;                   // Changes private text field to passed parameter
        this.ID = ID;                       // changes private ID field to passed parameter
    }

    protected IngClass(Parcel in) {         // This is a constructor used to pass Ingredient objects to the second activity
        text = in.readString();
        ID = in.readInt();
    }

    public static final Creator<IngClass> CREATOR = new Creator<IngClass>() {   // this uses parcel constructor to create parcel of ingredient objects
        @Override
        public IngClass createFromParcel(Parcel in) {
            return new IngClass(in);
        }

        @Override
        public IngClass[] newArray(int size) {
            return new IngClass[size];
        }
    };

    // Setter methods for the private fields of the ingredient objects, weren't used but here for further development
    public void setText(String text) {
		this.text = text;
	}
	public void setID(int ID) {
		this.ID = ID;
	}

    // Getter methods for the private fields
	public int getID() {
		return ID;
	}
    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeInt(ID);
    }
}