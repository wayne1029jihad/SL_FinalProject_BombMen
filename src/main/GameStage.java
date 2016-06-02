package main;

import processing.core.PApplet;
import processing.event.KeyEvent;

import java.awt.event.KeyListener;
import controlP5.*;

import controlP5.Button;
import controlP5.ControlP5;

import controlP5.Textfield;
import ddf.minim.*;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.
*/
public class GameStage extends PApplet implements KeyListener{
	private static final long serialVersionUID = 1L;
	private final static int width = 1200, height = 650;
	private int boxweight = 45;
	private int boxheight = 40;
	private ControlP5 cp5;
	private LoginPanel login = new LoginPanel();
	public Map gamemap;
	public Character_one self;
	private Character_one opponent;
	private boolean pressed = false;
	Textarea myTextarea;
	private enum Gamestate {Init,Menu,WatingConn,GameStart};
	private Gamestate gstat = Gamestate.Init;
	public String chatword;
	Minim minim;
	ddf.minim.AudioPlayer song;
	//Client
		Client client = new Client("127.0.0.1",8000);
		private boolean ready = false;
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
		 

		self= new Character_one(this,"CH1",45,40,5,client.getNumber());		
		gamemap = new Map(this,15,13);
		
		//add music
		minim=new Minim(this);
		song=minim.loadFile("song/file.mp3");
		//song.play();
		//Client
		client.connect();			
		
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
			gstat = Gamestate.WatingConn;
	}
	public void draw() {
		background(boxheight,160,110);
		if(login.loginpass){
			gstat = Gamestate.Menu;
			login.loginpass = false;
			cp5.get(Button.class, "btn1").hide();
			cp5.get(Button.class, "btn2").hide();
			cp5.get(Button.class, "btn3").show();
		}
		if (gstat == Gamestate.WatingConn)
		{
			String token = " ";
			String temp;
			String[] trans;
			String number;			
			textSize(39);
			text("Waiting For Opponent", 200, 450);
			if(!ready)
			{
				client.sendMessage("READY");
				self.setNumber(client.getNumber());
				ready = true;
			}
							
			temp = client.getdata();
			if(temp != null)
				 token= temp; 
			client.setchange(false);			
			if(token.equals("BEGIN") )
			{
				System.out.println("send");
				delay(500);
				client.sendMessage(self.getNumber()+"@"+self.getName()+"@"+self.getX()+"@"+self.getY());
				while(!client.getchange()){};					
					token = client.getdata();
					client.setchange(false);
						
		
				trans = token.split("@",4);
				number = Integer.toString(self.getNumber());
				if(trans[0].equals(number))
				{
					delay(100);
						token = client.getdata();
						trans = token.split("@");
						opponent = new Character_one(this, trans[1],Integer.valueOf(trans[2]),Integer.valueOf(trans[3]), 5,Integer.valueOf(trans[0]));
						gstat =  Gamestate.GameStart;
						client.setchange(false);
						System.out.println("start");
				}
				else
				{
					System.out.println(trans[0]+" "+trans[1]+" "+trans[2]+" ");
					opponent = new Character_one(this, trans[1],Integer.valueOf(trans[2]),Integer.valueOf(trans[3]), 5,Integer.valueOf(trans[0]));
					System.out.println(opponent.getNumber());
					gstat =  Gamestate.GameStart;
					client.setchange(false);
					
				}
			}
		}
		if(client.getchange())
		{
			dataFromServer();			
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
		text("Name:"+self.getName(), 695, 48);
		text("Score:"+self.getNowScore(), 695, 70);
		text("XP:"+self.getXP(), 695, 95);
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
	}
	
	public void keyPressed(KeyEvent e){
		int key1 = e.getKeyCode();
		if(pressed == false)
		{
			pressed = true;
			if(key1 == java.awt.event.KeyEvent.VK_LEFT){
				if(gamemap.NoObstacle(self.next_x/boxweight-1, self.next_y/boxheight)){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(self.next_x, self.next_y) == 4)
					//self.move(Direction.LEFT,true);
					client.sendMessage("OP"+"@"+self.getNumber()+"@L@T");
				} else {
					//self.move(Direction.LEFT,false);
					client.sendMessage("OP"+"@"+self.getNumber()+"@L@F");
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_DOWN){
				if(gamemap.NoObstacle(self.next_x/boxweight, self.next_y/boxheight+1)){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(self.next_x, self.next_y) == 4)
					//self.move(Direction.DOWN,true);
					client.sendMessage("OP"+"@"+self.getNumber()+"@D@T");
				} else {
					//self.move(Direction.DOWN,false);
					client.sendMessage("OP"+"@"+self.getNumber()+"@D@F");
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_UP){
				if(gamemap.NoObstacle(self.next_x/boxweight, self.next_y/boxheight-1)){// at least at 1 block, use matrix to put character
					//self.move(Direction.UP,true);
					client.sendMessage("OP"+"@"+self.getNumber()+"@U@T");
				} else {
					//self.move(Direction.UP,false);
					client.sendMessage("OP"+"@"+self.getNumber()+"@U@F");
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_RIGHT){
				if(gamemap.NoObstacle(self.next_x/boxweight+1, self.next_y/boxheight)){// at least at 1 block, use matrix to put character					if(gamemap.getoneboxmap(self.next_x, self.next_y) == 4)
					//self.move(Direction.RIGHT,true);
					client.sendMessage("OP"+"@"+self.getNumber()+"@R@T");
				} else {
					//self.move(Direction.RIGHT,false);
					client.sendMessage("OP"+"@"+self.getNumber()+"@R@F");
				}
			}
			else if(key1 == java.awt.event.KeyEvent.VK_SPACE)
			{
				if(gamemap.NoObstacle(self.next_x/boxweight, self.next_y/boxheight))
					//self.bombput();
					client.sendMessage("OP"+"@"+self.getNumber()+"@S");
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
	public void dataFromServer()
	{
		 String token;		 
		 token = client.getdata();
		 client.setchange(false);
			 String []trans = token.split("@");
			 if(trans[0].equals("OP"))
			 {
				 if(Integer.valueOf(trans[1])== self.getNumber())
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
								self.move(Direction.LEFT,true);
							else if(trans[2].equals("R"))
								self.move(Direction.RIGHT,true);	
							else if(trans[2].equals("U"))
								self.move(Direction.UP,true);
							else if(trans[2].equals("D"))
								self.move(Direction.DOWN,true);
						 }
						 else if(trans[3].equals("F"))
						 {
							 if(trans[2].equals("L"))
								self.move(Direction.LEFT,false);
							else if(trans[2].equals("R"))
								self.move(Direction.RIGHT,false);	
							else if(trans[2].equals("U"))
								self.move(Direction.UP,false);
							else if(trans[2].equals("D"))
								self.move(Direction.DOWN,false); 
						 }
					 }
							
				 }
				 else if(Integer.valueOf(trans[1])== opponent.getNumber())
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
			 //client.setchange(false);
	}
}
