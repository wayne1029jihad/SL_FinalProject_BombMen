package main;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import controlP5.ControlP5;
import controlP5.Textfield;

@SuppressWarnings("serial")
public class LoginPanel extends PApplet{
	private ControlP5 cp5;
	public void setup() {
		size(500, 500);
		smooth();

		cp5 = new ControlP5(this);
		cp5.addTextfield("Account");
		cp5.addTextfield("Password");
		cp5.addButton("submit").setPosition(150,400).setSize(100,50);
		cp5.getController("submit").getCaptionLabel().setFont((createFont("Arial",20,true)));
		
		Textfield text = cp5.get(Textfield.class, "Account");
		text.setSize(200, 60);
		text.setFont(createFont("Arial",20,true));// use true/false for smooth/no-smooth
		text.setColorCursor(color(0,0,0));
		text.setPosition(150, 100);
		text.setColorBackground(color(155,220,235));
		text.setColor(color(0,0,0));
		text.getCaptionLabel().hide();
		
		text = cp5.get(Textfield.class, "Password");
		text.setSize(200, 60);
		text.setFont(createFont("Arial",20,true));
		text.setColorCursor(color(0,0,0));
		text.setPosition(150, 300);
		text.setColorBackground(color(155,220,235));
		text.setColor(color(0,0,0));
		text.setPasswordMode(true);
		text.getCaptionLabel().hide();
		
		
	}
	public void submit(){
		int i;
		JSONArray arr = loadJSONArray("account.json");
		JSONObject obj = null;
		String account = cp5.get(Textfield.class, "Account").getText();
		String password = cp5.get(Textfield.class, "Password").getText();

		for (i = 0;i< arr.size();i++){
			obj = arr.getJSONObject(i);
			if (obj.getString("account").equals(account))
				break;
		}

		if(arr.size() == 0 ){
			System.out.println("account list is empty");
		} else if (arr.size() == i){
			System.out.println("Sorry, we don't recognize this account.");
		} else {
			if (obj.getString("password").equals(password)) {
				System.out.println("password correct");
			} else {
				System.out.println("password wrong");
			}
		}

		System.out.println(password);
		System.out.println(account);
		frame.setVisible(false);
		stop();
	}
	public void draw() {
		
	}
}
