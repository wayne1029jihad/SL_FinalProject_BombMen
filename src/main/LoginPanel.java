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

		cp5.addLabel("Your Account:")
		.setPosition(40,60)
		.setSize(200,200)
		.setFont((createFont("Arial",32,true)))// use true/false for smooth/no-smooth
		.setColor(color(0,0,0));

		cp5.addLabel("Your Password:")
		.setPosition(40,200)
		.setSize(200,200)
		.setFont((createFont("Arial",32,true)))
		.setColor(color(0,0,0));

		cp5.addTextfield("Account")
		.setSize(200, 60)
		.setFont(createFont("Arial",20,true))
		.setColorCursor(color(0,0,0))
		.setPosition(50, 100)
		.setColorBackground(color(155,220,235))
		.setColor(color(0,0,0))
		.getCaptionLabel().hide();

		cp5.addTextfield("Password")
		.setSize(200, 60)
		.setFont(createFont("Arial",20,true))
		.setColorCursor(color(0,0,0))
		.setPosition(50, 250)
		.setColorBackground(color(155,220,235))
		.setColor(color(0,0,0))
		.setPasswordMode(true)
		.getCaptionLabel().hide();

		cp5.addButton("submit").setPosition(150,400).setSize(100,50);
		cp5.getController("submit").getCaptionLabel().setFont((createFont("Arial",20,true)));
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
