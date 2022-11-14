package com.example.quickeats;

public class IngClass {
    private boolean checked;
    private String text;
    private int ID;

    public IngClass(String text,int ID) {
        this.checked = false;
        this.text = text;
        this.ID = ID;
    }

    public void setText(String text) {
		this.text = text;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
    
    public boolean getCheck() {
        return checked;
    }

    public String getText() {
        return text;
    }
}
