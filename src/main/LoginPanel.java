package main;

import processing.core.PApplet;

import controlP5.ControlP5;
import controlP5.Textfield;

public class LoginPanel extends PApplet{
	private static final long serialVersionUID = 1L;
	private ControlP5 cp5;
	private int message = 0;
	private Client client;
	public boolean loginpass;
	public LoginPanel(Client c) {
		client = c;
		loginpass = false;
	}
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
		String account = cp5.get(Textfield.class, "Account").getText();
		String password = cp5.get(Textfield.class, "Password").getText();
		client.sendMessage("submmit@" + account + "@" + password);

		delay(300);
		String temp = client.getdata();

		if (temp.equals("true")) {
			loginpass = true;
			frame.setVisible(false);
			message = 0;
			stop();
		} else if (temp.equals("noaccount")) {
			message = 1;
		} else if (temp.equals("wrongpwd")) {
			message = 2;
		} else if (temp.equals("islogin")) {
			message = 4;
		}


	}
	public void New(){
		String account = cp5.get(Textfield.class, "Account").getText();
		String password = cp5.get(Textfield.class, "Password").getText();
		String temp;
		client.sendMessage("new@" + account + "@" + password);
		delay(300);
		temp = client.getdata();
		if (temp.equals("cantadd")){
			message = 3;
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
			case 4:
				fill(255,0,0);
				textFont(createFont("Arial",28,true));
				text("already login",100,370);
				break;
		}

	}
}
