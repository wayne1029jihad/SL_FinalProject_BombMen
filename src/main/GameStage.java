package main;

import processing.core.PApplet;
import processing.core.PFont;
import processing.event.KeyEvent;

import java.awt.event.KeyListener;
import java.applet.*;
import controlP5.*;
import java.awt.*;

import controlP5.Button;
import controlP5.ControlP5;

import java.util.Timer;
import controlP5.Textfield;
/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.
*/
public class GameStage extends PApplet implements KeyListener{
	private static final long serialVersionUID = 1L;
	private final static int width = 1200, height = 650;
	private ControlP5 cp5;
	private LoginPanel login = new LoginPanel();
	public Map gamemap;
	public Character_one ch1; 
	private boolean pressed = false;
	private Timer timer = new Timer();
	Textarea myTextarea;
	public String chatword;
	
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
			
			cp5.addTextfield("space")
			.setSize(400, 30)
			.setFont(createFont("Arial",20,true))
			.setColorCursor(color(0,0,0))
			.setPosition(690, 500)
			.setColorBackground(color(155,220,235))
			.setColor(color(0,0,0))
			.hide();
			
			myTextarea = cp5.addTextarea("txt")
						 .setPosition(690,135)
						 .setSize(400,365)
		                  .setFont(createFont("arial",20))
		                  .setLineHeight(14)
		                  .setColor(color(128))
		                  .setColorBackground(color(255))
		                  .setColorForeground(color(255)).hide();

		 
		//addKeyListener(this);
		ch1= new Character_one(this,"CH1",1,1,1);
		gamemap = new Map(this,15,13);
		gamemap.ChangeByUser(1,1,4);
		timer.schedule(ch1.bomb, 0, 450);
					
		
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
			cp5.get(Button.class, "btn2").hide();
			gamemap.display();
			
			//introduction
			fill(155,220,235);
			rect(690, 5, 400, 100);	
			noStroke();
			
			fill(0);	
			textSize(19);
			text("Introduction:", 695, 28);
			text("Name:"+ch1.getName(), 695, 48); 
			text("Score:"+ch1.getNowScore(), 695, 70); 
			text("XP:"+ch1.getXP(), 695, 95); 
			//chat
			textSize(19);
			fill(155,220,0);
			rect(690, 105, 400, 30);	
			noStroke();
			fill(0);
			text("Let's chat~", 695, 125); 
			cp5.get(Textarea.class, "txt").setFont(createFont("Arial",20,true)).show();
			cp5.get(Textfield.class, "space").setFont(createFont("Arial",20,true)).show();
		}
		if(ch1.bomb.getCount() == 10)
		{
			System.out.println("bombreset");
			//timer.purge();
			ch1.bomb.resetCount();			
		}
		
	}
	
	
	public void keyPressed(KeyEvent e){//press W, A, S, D
		int key1 = e.getKeyCode();
		if(pressed == false)
		{
			pressed = true;
			if(key1 == java.awt.event.KeyEvent.VK_LEFT){
				ch1.d = Direction.LEFT;
				gamemap.setCharacter(ch1.d, ch1.getName());
				if(gamemap.NoObstacle(ch1.next_x-1, ch1.next_y) ||gamemap.getoneboxmap(ch1.next_x-1, ch1.next_y) == 4 ){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
					if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)	
						gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 0);
					ch1.next_x --;
					gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 4);
				}			
			}
			else if(key1 == java.awt.event.KeyEvent.VK_DOWN){
				ch1.d = Direction.DOWN;
				gamemap.setCharacter(ch1.d, ch1.getName());
				if(gamemap.NoObstacle(ch1.next_x, ch1.next_y+1) ||gamemap.getoneboxmap(ch1.next_x, ch1.next_y+1) == 4 ){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
					if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)	
						gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 0);
					ch1.next_y++;
					gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 4);
				}			
			}
			else if(key1 == java.awt.event.KeyEvent.VK_UP){
				ch1.d = Direction.UP;
				gamemap.setCharacter(ch1.d, ch1.getName());
				if(gamemap.NoObstacle(ch1.next_x, ch1.next_y-1) ||(gamemap.getoneboxmap(ch1.next_x, ch1.next_y-1) == 4 )){// at least at 1 block, use matrix to put character
					if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
						gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 0);
					ch1.next_y--;
					gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 4);
				}			
			}
			else if(key1 == java.awt.event.KeyEvent.VK_RIGHT){
				ch1.d = Direction.RIGHT;
				gamemap.setCharacter(ch1.d, ch1.getName());
				if(gamemap.NoObstacle(ch1.next_x+1, ch1.next_y) || (gamemap.getoneboxmap(ch1.next_x+1, ch1.next_y) == 4) ){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
					if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)	
						gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 0);
					ch1.next_x ++;
					gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 4);
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_SPACE)
			{
				gamemap.ChangeByUser(ch1.next_x, ch1.next_y, 3);
				ch1.bomb.setBombPosition(ch1.next_x,ch1.next_y);
				ch1.bomb.startBomb();
			}
			else if(key1== java.awt.event.KeyEvent.VK_ENTER)
			{
				chatword=cp5.get(Textfield.class, "space").getText();
				myTextarea.setText(ch1.getName()+":"+chatword+"\n");
			}
			draw();
		}
	}
	public void	keyReleased()
	{
		pressed = false;
	}
	
	public void keyTyped(KeyEvent e){}
}
