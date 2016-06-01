package main;

import processing.core.PImage;

public class Character_one extends AbstractCharacter{
	private static final long serialVersionUID = 1L;
	public PImage image = null;
	private PImage up,right,right_go,left,left_go,down;
	private String name;
	public Direction d;
	public int next_x = 0, next_y = 0;
	
	public Character_one(GameStage g, String name, int initial_X, int initial_Y,int num){
		initial();
		this.gs = g;
		this.name = name;
		next_x = initial_X;
		next_y = initial_Y;
		loadimage();
		bombnumber = num;
		//super.setSkill(new Punch());
	}
	public String getName(){
		return name;
	}
	public void initial(){
		//getName();//before you use this to get player1 but every one are different name decide by server
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
				next_y -= 40;
		} else if(t == Direction.RIGHT) {
			if (d == Direction.RIGHTGO) {
				image = right;
				d = Direction.RIGHT;
			} else {
				image = right_go;
				d = Direction.RIGHTGO;
			}
			if (forward)
				next_x += 45;
		} else if(t == Direction.LEFT) {
			if (d == Direction.LEFTGO) {
				image = left;
				d = Direction.LEFT;
			} else {
				image = left_go;
				d = Direction.LEFTGO;
			}
			if (forward)
				next_x -= 45;
		} else {
			image = down;
			if (forward)
				next_y += 40;
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
		gs.image(image, next_x, next_y,45,40);
	}
}
