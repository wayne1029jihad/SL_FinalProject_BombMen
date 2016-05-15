package main;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import controlP5.ControlP5;
import controlP5.Textfield;

@SuppressWarnings("serial")
public class LoginPanel extends PApplet{
	private ControlP5 cp5;
	private int message = 0;
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

		cp5.addButton("Submit").setPosition(50,400).setSize(100,50);
		cp5.getController("Submit").getCaptionLabel().setFont((createFont("Arial",20,true)));

		cp5.addButton("New").setPosition(200,400).setSize(100,50);
		cp5.getController("New").getCaptionLabel().setFont((createFont("Arial",20,true)));

		cp5.addButton("Cancel").setPosition(350,400).setSize(100,50);
		cp5.getController("Cancel").getCaptionLabel().setFont((createFont("Arial",20,true)));
	}
	public void Submit(){
		int i;
		boolean pass = false;
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
			message = 1;
		} else {
			if (obj.getString("password").equals(password)) {
				System.out.println("password correct");
				pass = true;
			} else {
				message = 2;
			}
		}

		System.out.println(password);
		System.out.println(account);
		if(pass){
			frame.setVisible(false);
			stop();
		}
	}
	public void New(){
		BufferedWriter output;
		JSONArray arr = loadJSONArray("account.json");
		JSONObject obj = null;
		String account = cp5.get(Textfield.class, "Account").getText();
		String password = cp5.get(Textfield.class, "Password").getText();
		int i;

		for (i = 0;i< arr.size();i++){
			obj = arr.getJSONObject(i);
			if (obj.getString("account").equals(account))
				break;
		}
		if (i < arr.size()){
			message = 3;
			return;
		}

		obj.setString("account", account);
		obj.setString("password", password);

		arr.append(obj);

		try {
			output = new BufferedWriter(new FileWriter("account.json"));
			output.write(arr.toString());
			output.close();
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}
	public void Cancel(){
		frame.setVisible(false);
		stop();
	}
	public void draw() {
		background(255,255,255);
		switch(message){
			case 1:
				fill(255,0,0);
				textFont(createFont("Arial",24,true));
				text("Sorry, we don't recognize this account.",30,370);
				break;
			case 2:
				fill(255,0,0);
				textFont(createFont("Arial",28,true));
				text("password wrong",100,370);
				break;
			case 3:
				fill(255,0,0);
				textFont(createFont("Arial",28,true));
				text("Can't use this account",100,370);
				break;
		}
		
	}
}
