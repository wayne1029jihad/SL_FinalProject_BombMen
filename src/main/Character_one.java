package main;

import java.util.ArrayList;
import java.util.Timer;

import processing.core.PImage;

public class Character_one extends AbstractCharacter{
	private static final long serialVersionUID = 1L;
	public PImage image = null;
	private PImage up,right,right_go,left,left_go,down;
	private String name;
	public Direction d;
	private int boxweight = 45;
	private int boxheight = 40;
	private int id;//the number of player
	
	public Character_one(GameStage g, String name, int initial_X, int initial_Y,int num,int id){
		initial();
		this.gs = g;
		this.name = name;
		next_x = initial_X;
		next_y = initial_Y;
		loadimage();
		bombnumber = num;
		this.id = id;
		totalbomb = bombnumber;
		bomb = new ArrayList<Bomb>();
		for (int i = 0;i < totalbomb;i++) {
			Bomb b = new Bomb(this,gs,3,boxweight,boxheight);
			new Timer().schedule(b, 0, 450);
			bomb.add(b);
		}
	}
	public String getName(){
		return name;
	}
	public void initial(){
		setActive();
		setStartScore();
		setPowerTimes();
	}
	public void move(Direction t,boolean forward)
	{
		if(t == Direction.UP) {
			image = up;
			d = t;
			if (forward)
				next_y -= boxheight;
		} else if(t == Direction.RIGHT) {
			if (d == Direction.RIGHTGO) {
				image = right;
				d = Direction.RIGHT;
			} else {
				image = right_go;
				d = Direction.RIGHTGO;
			}
			if (forward)
				next_x += boxweight;
		} else if(t == Direction.LEFT) {
			if (d == Direction.LEFTGO) {
				image = left;
				d = Direction.LEFT;
			} else {
				image = left_go;
				d = Direction.LEFTGO;
			}
			if (forward)
				next_x -= boxweight;
		} else {
			image = down;
			if (forward)
				next_y += boxheight;
		}
	}
	private void loadimage()
	{
		up = gs.loadImage(name+"_back.png");
		right = gs.loadImage(name+"_right.png");
		right_go = gs.loadImage(name+"_right_go.png");
		left = gs.loadImage(name+"_left.png");
		left_go = gs.loadImage(name+"_left_go.png");
		down = gs.loadImage(name+"_front.png");
		image = down;
	}
	public void draw()
	{
		for (Bomb b : bomb)
			b.draw();
		gs.image(image, next_x, next_y,boxweight,boxheight);
	}
	public void setid(int num)
	{
		this.id=num;
		if(num == 1)
		{
			next_x = next_x *13;
			next_y = next_y *11;
		}
	}
	public int getid()
	{
		return id;
	}
	public int getX()
	{
		return next_x;
	}
	public int getY()
	{
		return next_y;
	}
}
