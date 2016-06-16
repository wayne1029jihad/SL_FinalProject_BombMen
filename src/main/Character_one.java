package main;

import java.util.ArrayList;
import java.util.TimerTask;


public class Character_one extends AbstractCharacter{
	private static final long serialVersionUID = 1L;
	
	public Character_one(GameStage g, String name, int initial_X, int initial_Y,int num,int id){
		initial();
		this.gs = g;
		this.name = name;
		this.initial_X = next_x = initial_X;
		this.initial_Y = next_y = initial_Y;
		loadimage();
		bombnumber = num;
		this.id = id;
		initial_bombnum = totalbomb = bombnumber;
		bomb = new ArrayList<Bomb>();
		for (int i = 0;i < totalbomb;i++) {
			Bomb b = new Bomb(this,gs,bombPower,boxweight,boxheight);
			timer.schedule(b, 0, 450);
			bomb.add(b);
		}
	}
	public String getName(){
		return name;
	}
	public void setName(String na)
	{
		name = na;
		loadimage();
	}
	public void initial(){
		setActive();
	}
	public void setActive(){
		isActive = true;
	}
	public void disActive(){
		isActive = false;
	}
	public boolean isActive(){
		return isActive;
	}		
	public void move(Direction t,boolean forward)
	{
		if(t == Direction.UP) {
			image = up;
			d = t;
			if (forward)
				next_y -= 1;
		} else if(t == Direction.RIGHT) {
			if (d == Direction.RIGHTGO) {
				image = right;
				d = Direction.RIGHT;
			} else {
				image = right_go;
				d = Direction.RIGHTGO;
			}
			if (forward)
				next_x += 1;
		} else if(t == Direction.LEFT) {
			if (d == Direction.LEFTGO) {
				image = left;
				d = Direction.LEFT;
			} else {
				image = left_go;
				d = Direction.LEFTGO;
			}
			if (forward)
				next_x -= 1;
		} else {
			image = down;
			if (forward)
				next_y += 1;
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
		gs.image(image, next_x*boxweight, next_y*boxheight,boxweight,boxheight);
	}
	public void setid(int num)
	{
		this.id=num;
		if(num == 1)
		{
			initial_X = next_x = next_x *13;
			initial_Y = next_y = next_y *11;
		}
	}
	public void setXP(int life)
	{
		this.life=life;
		System.out.println(life);
		if(life == 0)
			gs.client.sendMessage("DEAD@"+getid());
	}
	public int getXP()
	{
		return life;
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
	public void bombput()
	{
		if(Nobomb == false)
		{
			System.out.println("get prop:"+Nobomb);
			if (bombnumber == 0)
				System.out.println("can't put a bomb");
			else {
				bombnumber = bombnumber - 1;
				bomb.get(currentbomb).setBombPosition(next_x,next_y);
				bomb.get(currentbomb).startBomb();
				currentbomb = (currentbomb+1)%totalbomb;
			}
		}
	}
	public void bombrecover()
	{
		bombnumber = bombnumber + 1;
	}
	
	
	//fireup
	public void fireup()
	{
		bombPower++;
		for (Bomb b : bomb)
			b.setPower(bombPower);
	}
	public void DEF()
	{
		setXP(getXP()+1);
	}
	public void bump_add()
	{
		totalbomb++;
		bombnumber++;
		Bomb b = new Bomb(this,gs,bombPower,boxweight,boxheight);
		timer.schedule(b, 0, 450);
		bomb.add(b);
	}
	public void bump_NO()
	{
		Nobomb = true;
		timer.schedule(new TimerTask() {
			public void run(){						
						Nobomb = false;
				}			
		}, 10000);
	}
	public void reset()
	{
		next_x = initial_X;
		next_y = initial_Y;
		bombnumber = initial_bombnum;
		currentbomb = 0;
		totalbomb = initial_bombnum;
		bombPower =  initial_power;
		image = down;
		life = 1;
		bomb.clear();		
		bomb = new ArrayList<Bomb>();
		for (int i = 0;i < totalbomb;i++) {
			Bomb b = new Bomb(this,gs,bombPower,boxweight,boxheight);
			timer.schedule(b, 0, 450);
			bomb.add(b);
		}
		isActive = true;
	}
	
}
