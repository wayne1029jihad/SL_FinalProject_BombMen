package main;

import processing.core.PApplet;
import processing.event.KeyEvent;

import java.awt.event.KeyListener;
import java.applet.*;

import java.awt.*;

import controlP5.Button;
import controlP5.ControlP5;
/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.
*/
public class GameStage extends PApplet implements KeyListener{
	private static final long serialVersionUID = 1L;
	private final static int width = 1200, height = 650;
	private ControlP5 cp5;
	private LoginPanel login = new LoginPanel();
	private Map gamemap = new Map(this,15,13);
	private Character_one ch1 = new Character_one(this,"ch1");
	
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
		
		addKeyListener(this);
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
			gamemap.display();
		}
	}
	
	public void KeyPresses(KeyEvent e){//press W, A, S, D
		int key1 = e.getKeyCode();
		
		ch1.record_direction[0] = ch1.next_x;
		ch1.record_direction[1] = ch1.next_y;
		
		if(key1 == java.awt.event.KeyEvent.VK_A){
			ch1.d = Direction.LEFTGO;
			if(ch1.next_x >= 15){// at least at 1 block
				ch1.next_x -= 15;
			}
			ch1.record_direction[2] = ch1.next_x;
			ch1.record_direction[3] = ch1.next_y;
		}
		else if(key1 == java.awt.event.KeyEvent.VK_S){
			ch1.d = Direction.DOWN;
			if(ch1.next_y <= 637){
				ch1.next_y += 13;
			}
			ch1.record_direction[2] = ch1.next_x;
			ch1.record_direction[3] = ch1.next_y;
		}
		else if(key1 == java.awt.event.KeyEvent.VK_W){
			ch1.d = Direction.UP;
			if(ch1.next_y >= 13){
				ch1.next_y -= 13;
			}
			ch1.record_direction[2] = ch1.next_x;
			ch1.record_direction[3] = ch1.next_y;
		}
		else if(key1 == java.awt.event.KeyEvent.VK_D){
			ch1.d = Direction.RIGHTGO;
			if(ch1.next_x < 1185){
				ch1.next_x += 15;
			}
			ch1.record_direction[2] = ch1.next_x;
			ch1.record_direction[3] = ch1.next_y;
		}		
		draw();
	}
	
	public void keyTyped(KeyEvent e){}
}
