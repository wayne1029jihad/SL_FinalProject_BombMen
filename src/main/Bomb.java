package main;


import java.util.TimerTask;

import processing.core.PImage;
public class Bomb extends TimerTask{
	private int power;
	private int bombX,bombY;
	protected GameStage gs;
	private int blocksizeX,blocksizeY;
	private PImage image;
	private PImage redbomb,blackbomb,smallbomb,fire;
	private AbstractCharacter ch;
	private boolean isexplode = false;
	private int count = 0;
	private boolean start = false;

	public Bomb(AbstractCharacter ch,GameStage gs,int power,int bsizeX,int bsizeY) {
		
		setPower(power);
		this.gs = gs;
		this.ch = ch;
		loadimage();
		image = blackbomb;
		blocksizeX = bsizeX;
		blocksizeY = bsizeY;
	}

	public void run() {
		if(start)
		{
			gs.gamemap.ChangeByUser(bombX, bombY, 3);
			if(count > 7)
			{
				gs.gamemap.ChangeByUser(bombX, bombY, 0);
				count = 0;
				start = false;
				isexplode = false;
				ch.bombrecover();
			}
			else if(count > 6)
			{
				isexplode = true;
			}
			else if(count > 4)
				image = redbomb;
			else
			{
				if(count % 2 ==0)
					image = blackbomb;
				else
					image = smallbomb;
			}
			count++;
		}
	}
	
	public void setPower(int p){
		power = p;
	}
	public int getPower(){
		return power;
	}
	public void setBombPosition(int x,int y)
	{
		bombX = x;
		bombY = y;
	}
	public int getBombX()
	{
		return bombX;
	}
	public int getBombY()
	{
		return bombY;
	}
	public void startBomb()
	{
		start = true;
	}
	private void loadimage()
	{
		redbomb = gs.loadImage("bump_red.png");
		blackbomb = gs.loadImage("bump.png");
		smallbomb = gs.loadImage("bump_s.png");
		fire = gs.loadImage("fire.png");
	}
	private void paintline(int shiftX,int shiftY)
	{
		int i;
		int X = bombX + shiftX,Y = bombY + shiftY;
		for (i = 1;i < power;i++) {
			if (gs.gamemap.getoneboxmap(X, Y) == 2
				|| gs.gamemap.getoneboxmap(X, Y) == 8)
				break;
			gs.image(fire, X*blocksizeX, Y*blocksizeY,blocksizeX,blocksizeY);
			gs.gamemap.ChangeByUser(X, Y, 0);
			X += shiftX;
			Y += shiftY;
		}
	}
	public void draw()
	{
		if (start) {
			if (isexplode){
				gs.image(fire, bombX*blocksizeX , bombY*blocksizeY,blocksizeX,blocksizeY);
				paintline(1,0);
				paintline(-1,0);
				paintline(0,1);
				paintline(0,-1);
			} else {
				gs.image(image,bombX*blocksizeX,bombY*blocksizeY,blocksizeX,blocksizeY);
			}
		}
	}
		
}
