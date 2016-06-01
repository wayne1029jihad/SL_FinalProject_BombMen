package main;

import processing.core.PApplet;
import processing.event.KeyEvent;

import java.awt.event.KeyListener;
import controlP5.*;

import controlP5.Button;
import controlP5.ControlP5;

import java.util.Timer;
import controlP5.Textfield;
import ddf.minim.*;

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
	private Bomb bomb;
	Textarea myTextarea;
	private enum Gamestate {Init,Menu,GameStart};
	private Gamestate gstat = Gamestate.Init;
	public String chatword;
	Minim minim;
	ddf.minim.AudioPlayer song;

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

		cp5.addButton("btn3")
		.setLabel("Startgame").setPosition(500, 200).setSize(200, 50).hide();
		cp5.get(Button.class, "btn3").getCaptionLabel().setFont(createFont("Arial",20,true));

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
		                  .setColorForeground(color(255))
		                  .scroll(1)
		                  .showScrollbar().hide();
		 

		ch1= new Character_one(this,"CH1",45,40,1);
		bomb = new Bomb(ch1,this,3,45,40);
		gamemap = new Map(this,15,13);
		timer.schedule(bomb, 0, 450);
		
		//add music
		minim=new Minim(this);
		song=minim.loadFile("song/file.mp3");
		song.play();
					
		
	}
	
	public void btn1(){
		String []temp = {"LOGIN"};
		runSketch(temp, login);
	}
	public void btn2(){
		exit();
	}
	public void btn3(){
		if (gstat == Gamestate.Menu)
			gstat = Gamestate.GameStart;
	}
	public void draw() {
		background(40,160,110);
		if(login.loginpass){
			gstat = Gamestate.Menu;
			login.loginpass = false;
			cp5.get(Button.class, "btn1").hide();
			cp5.get(Button.class, "btn2").hide();
			cp5.get(Button.class, "btn3").show();
		}
		if (gstat == Gamestate.GameStart){
			cp5.get(Button.class, "btn3").hide();
			gamestart();
		}
		
	}
	public void gamestart() {
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
		bomb.draw();
		ch1.draw();
	}
	
	public void keyPressed(KeyEvent e){
		int key1 = e.getKeyCode();
		if(pressed == false)
		{
			pressed = true;
			if(key1 == java.awt.event.KeyEvent.VK_LEFT){
				if(gamemap.NoObstacle(ch1.next_x/45-1, ch1.next_y/40)){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
					ch1.move(Direction.LEFT,true);
				} else {
					ch1.move(Direction.LEFT,false);
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_DOWN){
				if(gamemap.NoObstacle(ch1.next_x/45, ch1.next_y/40+1)){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
					ch1.move(Direction.DOWN,true);
				} else {
					ch1.move(Direction.DOWN,false);
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_UP){
				if(gamemap.NoObstacle(ch1.next_x/45, ch1.next_y/40-1)){// at least at 1 block, use matrix to put character
					ch1.move(Direction.UP,true);
				} else {
					ch1.move(Direction.UP,false);
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_RIGHT){
				if(gamemap.NoObstacle(ch1.next_x/45+1, ch1.next_y/40)){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(ch1.next_x, ch1.next_y) == 4)
					ch1.move(Direction.RIGHT,true);
				} else {
					ch1.move(Direction.RIGHT,false);
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_SPACE)
			{
				if (ch1.canputbomb()) {
					ch1.bombput();
					bomb.setBombPosition(ch1.next_x, ch1.next_y);
					bomb.startBomb();
				} else {
					System.out.println("can't put a bomb");
				}
			}
			else if(key1== java.awt.event.KeyEvent.VK_ENTER)
			{
				chatword=cp5.get(Textfield.class, "space").getText();
				myTextarea.append(ch1.getName()+":"+chatword+"\n");
			}
			else if(key1==java.awt.event.KeyEvent.VK_PAUSE)
			{
				if(song.isPlaying())
					song.pause();
				else
					song.play();
			}
		}
	}
	public void	keyReleased()
	{
		pressed = false;
	}
	
	public void keyTyped(KeyEvent e){}
}
