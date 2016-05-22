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
	private Map gamemap;
	public Character_one ch1; 
	
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
		ch1= new Character_one(this,"CH1",1,1);
		gamemap = new Map(this,15,13);
		gamemap.ChangeByUser(1,1,4);
		
	}
	public void game()
	{/*
		ch1= new Character_one(this,"ch1",1,1);
		gamemap.ChangeByUser(1,1,4);*/
		
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
		//	cp5.get(Button.class, "btsn2").hide();
			gamemap.display();
		}
	}
	
	
	public void keyPressed(KeyEvent e){//press W, A, S, D
		int key1 = e.getKeyCode();
		if(key1 == java.awt.event.KeyEvent.VK_A){
			ch1.d = Direction.LEFT;
			if(gamemap.NoObstacle(ch1.next_x-1, ch1.next_y)){// at least at 1 block, use matrix to put character
				if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
					gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 0);
				ch1.next_x --;
				ch1.Move(ch1.next_x, ch1.next_y);
				gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 4);
			}			
		}
		else if(key1 == java.awt.event.KeyEvent.VK_S){
			ch1.d = Direction.DOWN;
			if(gamemap.NoObstacle(ch1.next_x, ch1.next_y+1)){// at least at 1 block, use matrix to put character
				if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
					gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 0);
				ch1.next_y++;
				ch1.Move(ch1.next_x, ch1.next_y);
				gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 4);
			}			
		}
		else if(key1 == java.awt.event.KeyEvent.VK_W){
			ch1.d = Direction.UP;
			if(gamemap.NoObstacle(ch1.next_x, ch1.next_y-1)){// at least at 1 block, use matrix to put character
				if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
					gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 0);
				ch1.next_y--;
				ch1.Move(ch1.next_x, ch1.next_y);
				gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 4);
			}			
		}
		else if(key1 == java.awt.event.KeyEvent.VK_D){
			ch1.d = Direction.RIGHT;
			if(gamemap.NoObstacle(ch1.next_x+1, ch1.next_y)){// at least at 1 block, use matrix to put character
				if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
					gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 0);
				ch1.next_x ++;
				ch1.Move(ch1.next_x, ch1.next_y);
				gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 4);
			}
		}
		else if(key1 == java.awt.event.KeyEvent.VK_SPACE)
		{
			gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 3);
		}
		draw();
	}
	
	public void keyTyped(KeyEvent e){}
}
