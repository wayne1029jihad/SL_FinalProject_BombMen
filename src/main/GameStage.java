package main;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.core.PImage;

import java.util.Timer;
import java.util.TimerTask;

import controlP5.*;

import controlP5.Button;
import controlP5.ControlP5;

import controlP5.Textfield;
import ddf.minim.*;


/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.
*/
public class GameStage extends PApplet{
	private static final long serialVersionUID = 1L;
	private final static int width = 1200, height = 650;
	private int boxweight = 45;
	private int boxheight = 40;
	private PImage background;
	private PImage c1, c2, c3, c4;
	private ControlP5 cp5;
	private LoginPanel login;
	public Map gamemap;
	public Character_one self;
	public Character_one opponent;
	private boolean pressed = false;
	Textarea myTextarea;
	private enum Gamestate {Init,Menu,ChMenu,WatingConn,GameStart,GameEnd};
	private Gamestate gstat = Gamestate.Init;
	public String chatword;
	Minim minim;
	ddf.minim.AudioPlayer frontMusic;
	ddf.minim.AudioPlayer song,explosionSound,laughingSound,shoutingSound,footstepSound,lostSound;
	//Client
	Client client = new Client("127.0.0.1",8000);
	private boolean ready = false;
	
	//timer
		private int sec = 0;
		private Timer timer = new java.util.Timer();
		private boolean timestart = false;
	//prop
	private int[]prop;
	//state
	private PImage win,lose,timeon;
	private boolean again = false;
	
	public void setup() {
		println(dataPath(""));
		size(width, height);
		smooth();
		println(dataPath(""));
		background = loadImage("begin.jpg");
		c1 = loadImage("CH1_front.png");
		c2 = loadImage("CH2_front.png");
		c3 = loadImage("CH3_front.png");
		c4 = loadImage("CH4_front.png");
		cp5 = new ControlP5(this);
		cp5.addButton("btn1")
		.setLabel("Login").setPosition(500, 300).setSize(200, 50);
		cp5.get(Button.class, "btn1").getCaptionLabel().setFont(createFont("Arial",20,true));

		cp5.addButton("btn2")
		.setLabel("Exit").setPosition(500, 500).setSize(200, 50);
		cp5.get(Button.class, "btn2").getCaptionLabel().setFont(createFont("Arial",20,true));

		cp5.addButton("btn3")
		.setLabel("Startgame").setPosition(500, 200).setSize(200, 50).hide();
		cp5.get(Button.class, "btn3").getCaptionLabel().setFont(createFont("Arial",20,true));
		
		cp5.addButton("btn4")
		.setLabel("Restart").setPosition(500, 300).setSize(200, 50).hide();
		cp5.get(Button.class, "btn4").getCaptionLabel().setFont(createFont("Arial",20,true));
		
		cp5.addButton("btn5")
		.setLabel("Menu").setPosition(500, 500).setSize(200, 50).hide();
		cp5.get(Button.class, "btn5").getCaptionLabel().setFont(createFont("Arial",20,true));
		
		cp5.addButton("CH1")
		.setLabel("CH1").setPosition(100, 100).setSize(100, 50).hide();
		cp5.get(Button.class, "CH1").getCaptionLabel().setFont(createFont("Arial",20,true));
		
		cp5.addButton("CH2")
		.setLabel("CH2").setPosition(300, 100).setSize(100, 50).hide();
		cp5.get(Button.class, "CH2").getCaptionLabel().setFont(createFont("Arial",20,true));
		
		cp5.addButton("CH3")
		.setLabel("CH3").setPosition(500, 100).setSize(100, 50).hide();
		cp5.get(Button.class, "CH3").getCaptionLabel().setFont(createFont("Arial",20,true));
		
		cp5.addButton("CH4")
		.setLabel("CH4").setPosition(700, 100).setSize(100, 50).hide();
		cp5.get(Button.class, "CH4").getCaptionLabel().setFont(createFont("Arial",20,true));

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
		 

		self= new Character_one(this,"CH1",1,1,2,client.getNumber());
		
		
		//add music
		minim=new Minim(this);
		frontMusic=minim.loadFile("song/middleMusic.mp3");
		song=minim.loadFile("song/song.mp3");
		explosionSound=minim.loadFile("song/explosion.mp3");
		laughingSound=minim.loadFile("song/laughing.mp3");
		shoutingSound=minim.loadFile("song/shouting.mp3");
		footstepSound=minim.loadFile("song/footstep.mp3");
		lostSound=minim.loadFile("song/lost.mp3");
		frontMusic.play();
		//Client
		client.connect();
		login = new LoginPanel(client);
		
		prop = new int[15*13];
		getprop();
		gamemap = new Map(this,15,13,prop);
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
			gstat = Gamestate.ChMenu;
	}
	public void btn4(){	//Restart the game
		cp5.get(Button.class, "btn4").hide();
		cp5.get(Button.class, "btn5").hide();
		sec = 0;
		gamemap.reset();
		self.reset();
		opponent.reset();
		self.setActive();
		opponent.setActive();
		client.sendMessage("READY");
		delay(200);
		getprop();
		gamemap.PG.setProp(prop);
		gstat = Gamestate.WatingConn;
	}
	public void btn5(){ //Go back to the menu
		cp5.get(Button.class, "btn4").hide();
		cp5.get(Button.class, "btn5").hide();
		cp5.get(Button.class, "btn1").show();
		cp5.get(Button.class, "btn2").show();
		gstat = Gamestate.Menu;
	}
	
	public void CH1(){
		self.setName("CH1");
		if (gstat == Gamestate.ChMenu)
			gstat = Gamestate.WatingConn;
	}
	public void CH2(){
		self.setName("CH2");
		if (gstat == Gamestate.ChMenu)
			gstat = Gamestate.WatingConn;
	}
	public void CH3(){
		self.setName("CH3");
		if (gstat == Gamestate.ChMenu)
			gstat = Gamestate.WatingConn;
	}
	public void CH4(){
		self.setName("CH4");
		if (gstat == Gamestate.ChMenu)
			gstat = Gamestate.WatingConn;
	}
	public void draw() {
		background(boxheight,160,110);
		image(background,0,0,1200,680);
		
		if(login.loginpass){
			gstat = Gamestate.Menu;
			login.loginpass = false;
			cp5.get(Button.class, "btn1").hide();
			cp5.get(Button.class, "btn2").hide();
			cp5.get(Button.class, "btn3").show();

		}
		if(gstat == Gamestate.ChMenu){
			image(c1, 100, 200, 100, 100);
			image(c2, 300, 200, 100, 100);
			image(c3, 500, 200, 100, 100);
			image(c4, 700, 200, 100, 100);
			cp5.get(Button.class, "btn3").hide();
			cp5.get(Button.class, "CH1").show();
			cp5.get(Button.class, "CH2").show();
			cp5.get(Button.class, "CH3").show();
			cp5.get(Button.class, "CH4").show();
		}
		else if (gstat == Gamestate.WatingConn) {
			cp5.get(Button.class, "CH1").hide();//
			cp5.get(Button.class, "CH2").hide();//
			cp5.get(Button.class, "CH3").hide();//
			cp5.get(Button.class, "CH4").hide();//
			gameWait();
			if(client.getchange())
				dataFromServer();
		} else if (gstat == Gamestate.GameStart) {
			
			gameStart();
			if(client.getchange())
				dataFromServer();
		}else if(gstat == Gamestate.GameEnd)
		{
			cp5.get(Textarea.class, "txt").setFont(createFont("Arial",20,true)).hide();
			cp5.get(Textfield.class, "space").setFont(createFont("Arial",20,true)).hide();
			cp5.get(Button.class, "btn4").show();
			cp5.get(Button.class, "btn5").show();
		}
		
	}
	private void gameStart() {
		frontMusic.pause();
		song.play();
		//song.setVolume((float) 0.01);
		gamemap.display();
		gamemap.PG.display();
		//introduction
		fill(155,220,235);
		rect(690, 5, 400, 100);
		noStroke();

		fill(0);
		textSize(19);
		if(timestart == false)
		{
			timestart = true;
			Timer();			
		}
		int time = 120-sec;
		text("Time : "+time/60+" : "+time%60, 695, 28);
		
		text("Introduction:", 695, 48);//28
		text("Name:"+self.getName(), 695, 72);//48
		text("XP:"+self.getXP(), 695, 95);//95
		//chat
		textSize(19);
		fill(155,220,0);
		rect(690, 105, 400, 30);
		noStroke();
		fill(0);
		text("Let's chat~", 695, 125);
		cp5.get(Textarea.class, "txt").setFont(createFont("Arial",20,true)).show();
		cp5.get(Textfield.class, "space").setFont(createFont("Arial",20,true)).show();
		self.draw();
		opponent.draw();
		
		if(time <= 0 || !self.isActive() || !opponent.isActive())
		{
			gstat = Gamestate.GameEnd;
		}
	}
	private void gameWait() {
		String token = " ";
		String temp;
		String[] trans;
		String number;
		textSize(39);
		text("Waiting For Opponent", 200, 450);
		if(!ready)
		{
			client.sendMessage("READY");
			self.setid(client.getNumber());
			ready = true;
		}

		temp = client.getdata();
		if(temp != null)
			 token= temp;
		client.setchange(false);
		if(token.equals("BEGIN") )
		{
			
			delay(100);
			if(!again)
			{
				client.sendMessage(self.getid()+"@"+self.getName()+"@"+self.getX()+"@"+self.getY());
				while(!client.getchange());
					token = client.getdata();
				client.setchange(false);
	
				trans = token.split("@",4);
				number = Integer.toString(self.getid());
				if(trans[0].equals(number))
				{
					delay(200);
					token = client.getdata();
					trans = token.split("@");
				}
				opponent = new Character_one(this,trans[1] ,Integer.valueOf(trans[2]),Integer.valueOf(trans[3]), 2,Integer.valueOf(trans[0]));
				client.setchange(false);
			}
			gstat =  Gamestate.GameStart;
			
			
			again = true;
		}
	}
	private void Timer()
	{
		timer.schedule(new TimerTask() {
			public void run(){
				if(sec < 300){
					sec++;
				}				
			}
		}, 0,1000);
	}
	public void keyPressed(KeyEvent e){
		int key1 = e.getKeyCode();
		if(pressed == false)
		{
			pressed = true;
			if(key1 == java.awt.event.KeyEvent.VK_LEFT){
				if(gamemap.NoObstacle(self.next_x-1, self.next_y)){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(self.next_x, self.next_y) == 4)
					client.sendMessage("OP"+"@"+self.getid()+"@L@T");
				} else {
					client.sendMessage("OP"+"@"+self.getid()+"@L@F");
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_DOWN){
				if(gamemap.NoObstacle(self.next_x, self.next_y+1)){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(self.next_x, self.next_y) == 4)
					client.sendMessage("OP"+"@"+self.getid()+"@D@T");
				} else {
					client.sendMessage("OP"+"@"+self.getid()+"@D@F");
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_UP){
				if(gamemap.NoObstacle(self.next_x, self.next_y-1)){// at least at 1 block, use matrix to put character
					client.sendMessage("OP"+"@"+self.getid()+"@U@T");
				} else {
					client.sendMessage("OP"+"@"+self.getid()+"@U@F");
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_RIGHT){
				if(gamemap.NoObstacle(self.next_x+1, self.next_y)){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(self.next_x, self.next_y) == 4)
					client.sendMessage("OP"+"@"+self.getid()+"@R@T");
				} else {
					client.sendMessage("OP"+"@"+self.getid()+"@R@F");
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_SPACE)
			{
				if(gamemap.NoObstacle(self.next_x, self.next_y))
					//self.bombput();
					client.sendMessage("OP"+"@"+self.getid()+"@S");
			}
			else if(key1== java.awt.event.KeyEvent.VK_ENTER)
			{
				chatword=cp5.get(Textfield.class, "space").getText();
				//myTextarea.append(self.getName()+":"+chatword+"\n");
				client.sendMessage("CHAT"+"@"+self.getName()+":"+chatword);
			}
			else if(key1==java.awt.event.KeyEvent.VK_PAUSE)
			{
				if(song.isPlaying())
					song.pause();
				else if(!song.isPlaying() )
					song.play();
			}
			else if(key1==java.awt.event.KeyEvent.VK_HOME)
			{
				if(frontMusic.isPlaying())
					frontMusic.pause();
				else if (!frontMusic.isPlaying())
					frontMusic.play();
			}
		}
	}
	public void	keyReleased()
	{
		pressed = false;
	}
	
	public void keyTyped(KeyEvent e){}
	public void dataFromServer()
	{
		 String token;		 
		 token = client.getdata();
		 client.setchange(false);
			 String []trans = token.split("@");
			 if(trans[0].equals("OP") && self.isActive() && opponent.isActive())
			 {
				 if(Integer.valueOf(trans[1])== self.getid())
				 {
					 if(trans[2].equals("S"))
						{
							self.bombput();
						}
					 else
					 {
						 if(trans[3].equals("T"))
						 {							 
							 if(trans[2].equals("L"))
								{
								 self.move(Direction.LEFT,true);
								 footstepSound.rewind(); 
								 footstepSound.play();
								}
							else if(trans[2].equals("R"))
								{
								self.move(Direction.RIGHT,true);
								footstepSound.rewind();
								footstepSound.play();
								}
							else if(trans[2].equals("U"))
								{
								self.move(Direction.UP,true);
								footstepSound.rewind();
								footstepSound.play();
								}
							else if(trans[2].equals("D"))
								{
								self.move(Direction.DOWN,true);
								footstepSound.rewind();
								footstepSound.play();
								}
						 }
						 else if(trans[3].equals("F"))
						 {
							 if(trans[2].equals("L"))
								{
								 self.move(Direction.LEFT,false);
								 footstepSound.rewind();
								 footstepSound.play();
								}
							else if(trans[2].equals("R"))
								{
								self.move(Direction.RIGHT,false);	
								 footstepSound.rewind();
								 footstepSound.play();
								}
							else if(trans[2].equals("U"))
								{
								self.move(Direction.UP,false);
								 footstepSound.rewind();
								 footstepSound.play();
								}
							else if(trans[2].equals("D"))
								{
								self.move(Direction.DOWN,false); 
								 footstepSound.rewind();
								 footstepSound.play();
								}
						 }
					 }
							
				 }
				 else if(Integer.valueOf(trans[1])== opponent.getid())
				 {
					System.out.println("opponent: X = "+opponent.getX());
					System.out.println(trans[2]);

					if(trans[2].equals("S"))
					{
						 opponent.bombput();
					}
					 else
					 {
						 if(trans[3].equals("T"))
						 {
							 if(trans[2].equals("L"))
						 		opponent.move(Direction.LEFT,true);
							else if(trans[2].equals("R"))
								opponent.move(Direction.RIGHT,true);	
							else if(trans[2].equals("U"))
								opponent.move(Direction.UP,true);
							else if(trans[2].equals("D"))
								opponent.move(Direction.DOWN,true);
						 }
						 else if(trans[3].equals("F"))
						 {
							 if(trans[2].equals("L"))
								opponent.move(Direction.LEFT,false);
							else if(trans[2].equals("R"))
								opponent.move(Direction.RIGHT,false);	
							else if(trans[2].equals("U"))
								opponent.move(Direction.UP,false);
							else if(trans[2].equals("D"))
								opponent.move(Direction.DOWN,false); 
						 }
					 }
				 }
			 }
			 else if(trans[0].equals("CHAT"))
			 {
				myTextarea.append(trans[1]+"\n");			
			 }
			 else if(trans[0].equals("PROP"))
			 {
				 if(Integer.valueOf(trans[1])== self.getid())
				 {
					 song.pause();
					 laughingSound.rewind();
					 laughingSound.play();
					 song.play();
					 if(trans[2].equals("U"))
					 { self.fireup();
					 }
					 else if(trans[2].equals("D"))
						 self.DEF();
					 else if(trans[2].equals("A"))
						 self.bump_add();
					 else if(trans[2].equals("N"))
						 self.bump_NO();
				 }
				 else if(Integer.valueOf(trans[1])== opponent.getid())
				 {
					 laughingSound.rewind();
					 laughingSound.play();
					 if(trans[2].equals("U"))
						 {
						 opponent.fireup();
						 }
					 else if(trans[2].equals("D"))
						 opponent.DEF();
					 else if(trans[2].equals("A"))
						 opponent.bump_add();
					 else if(trans[2].equals("N"))
						 opponent.bump_NO();
				 }
				 gamemap.PG.setProptoZero(Integer.valueOf(trans[3]));
			 }
			 else if(trans[0].equals("DEAD"))
			 {
				 song.pause();
				 if(Integer.valueOf(trans[1]) == self.getid())
				 { 
					 lostSound.rewind();
					 lostSound.play();
					 self.disActive();
				 }
				 else if(Integer.valueOf(trans[1])== opponent.getid())
				 {
					 lostSound.rewind();
					 lostSound.play();
					 opponent.disActive();
				 }
			 }
			 //client.setchange(false);
	}
	public void getprop()
	{
		delay(100);
		while(!client.getchange()){};
		String propdata = client.getdata();
		String []trans;
		trans = propdata.split("@");
		System.out.println("trans size : "+trans.length);
		for(int i = 0; i < trans.length; i++)
		{
			prop[i] = Integer.valueOf(trans[i]);
		}
	}
}
