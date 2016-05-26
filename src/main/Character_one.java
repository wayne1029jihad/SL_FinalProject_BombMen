package main;

import processing.core.PImage;

public class Character_one extends AbstractCharacter{
	private static final long serialVersionUID = 1L;
	public PImage image = null;
	private PImage up,right,right_go,left,left_go,down;
	private String name;
	public Direction d;
	public int next_x = 0, next_y = 0;
	public Bomb bomb;
	private int number;//the number of bombs
	
	public Character_one(GameStage g, String name, int initial_X, int initial_Y, int num){
		initial(g,num);
		this.gs = g;
		this.name = name;
		next_x = initial_X;
		next_y = initial_Y;
		loadimage();
		//super.setSkill(new Punch());
	}
	public String getName(){
		return name;
	}
	public void initial(GameStage gs, int num){
		//getName();//before you use this to get player1 but every one are different name decide by server
		setNumber(num);
		setActive();
		setStartScore();
		setPowerTimes();
		bomb = new Bomb(1,3,gs);
	}
	public void setNumber(int num)
	{
		this.number=num;
	}
	public int getNumber()
	{
		return this.number;
	}
	public void move(Direction t)
	{
		if(t == Direction.UP) {
			image = up;
			next_y -= 40;
			d = t;
		} else if(t == Direction.RIGHT) {
			if (d == Direction.RIGHTGO) {
				image = right;
				d = Direction.RIGHT;
			} else {
				image = right_go;
				d = Direction.RIGHTGO;
			}
			next_x += 45;
		} else if(t == Direction.LEFT) {
			if (d == Direction.LEFTGO) {
				image = left;
				d = Direction.LEFT;
			} else {
				image = left_go;
				d = Direction.LEFTGO;
			}
			next_x -= 45;
		} else {
			image = down;
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
