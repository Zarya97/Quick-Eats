package com.example.quickeats;

import android.os.Parcel;
import android.os.Parcelable;

public class IngClass implements Parcelable {
    private boolean checked;
    private String text;
    private int ID;

    public IngClass(String text,int ID) {
        this.checked = false;
        this.text = text;
        this.ID = ID;
    }

    protected IngClass(Parcel in) {
        checked = in.readByte() != 0;
        text = in.readString();
        ID = in.readInt();
    }

    public static final Creator<IngClass> CREATOR = new Creator<IngClass>() {
        @Override
        public IngClass createFromParcel(Parcel in) {
            return new IngClass(in);
        }

        @Override
        public IngClass[] newArray(int size) {
            return new IngClass[size];
        }
    };

    public void setText(String text) {
		this.text = text;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
    public void setChecked(boolean Check) {
        this.checked = Check;
    }

    public boolean getChecked() {
        return checked;
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
        parcel.writeByte((byte) (checked ? 1 : 0));
        parcel.writeString(text);
        parcel.writeInt(ID);
    }
}
