package main;

import processing.core.PApplet;
import controlP5.Button;
import controlP5.ControlP5;
/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.
*/
public class GameStage extends PApplet{
	private static final long serialVersionUID = 1L;
	private final static int width = 1200, height = 650;
	private ControlP5 cp5;
	private LoginPanel login = new LoginPanel();
	private Map gamemap = new Map(this,15,13);
	public void setup() {
		size(width, height);
		smooth();

		cp5 = new ControlP5(this);
		cp5.addButton("btn1")
		.setLabel("Login").setPosition(500, 100).setSize(200, 50);
		cp5.get(Button.class, "btn1").getCaptionLabel().setFont(createFont("Arial",20,true));
		
		cp5.addButton("btn2")
		.setLabel("Exit").setPosition(500, 300).setSize(200, 50);
		cp5.get(Button.class, "btn2").getCaptionLabel().setFont(createFont("Arial",20,true));

	}
	public void btn1(){
		String []temp = {"LOGIN"};
		runSketch(temp, login);
	}
	public void btn2(){
		exit();
	}
	public void draw() {
		background(40,160,110);
		if(login.loginpass){
			cp5.get(Button.class, "btn1").hide();
			cp5.get(Button.class, "btn2").hide();
			gamemap.draw();
		}
	}
}
