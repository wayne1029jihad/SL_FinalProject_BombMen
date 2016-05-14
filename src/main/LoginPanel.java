package main;

import processing.core.PApplet;

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
		System.out.println(cp5.get(Textfield.class, "Password").getText());
		System.out.println(cp5.get(Textfield.class, "Account").getText());
		frame.setVisible(false);
		stop();
	}
	public void draw() {
		
	}
}
