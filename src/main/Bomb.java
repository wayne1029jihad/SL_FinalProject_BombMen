package main;

import java.awt.image.BufferedImage;

import java.util.TimerTask;
public class Bomb extends TimerTask{
	private int power;
	private Bombtype type;
	private int bombX,bombY;
	protected GameStage gs;
	private int count = 0;
	private boolean start = false;

	public Bomb(int num,int power,GameStage gs) {
		
		setPower(power);
		this.gs = gs;
	}

	public void run() {
		if(start)
		{
			if(count > 7)
			{
				for(int i = 0; i < 15*13; i++)
				{
					if((int) gs.gamemap.getoneboxmap(i%13, i/13) == 5)
					{
						gs.gamemap.ChangeByUser(i%13, i/13, 0);
					}
				}
				count = 10;
				start = false;
			}
			else if(count > 6)
			{
				explore();			
			}
			else if(count > 4)
				type = Bombtype.bump_red;
			else
			{
				if(count % 2 ==0)
					type = Bombtype.bump_b;
				else
					type = Bombtype.bump_s;
			}
			gs.gamemap.setBomb(type);
			count++;
		}
	}
	
	public void explore()
	{
		gs.gamemap.ChangeByUser(bombX, bombY, 5);
		for(int i = 1; i < power; i++)
		{
			if(gs.gamemap.NoObstacle(bombX+i, bombY) || gs.gamemap.getoneboxmap(bombX+i, bombY) == 1)
			{
				if(gs.gamemap.getoneboxmap(bombX+i, bombY) == 1)
				{
					gs.gamemap.ChangeByUser(bombX+i, bombY, 5);
					if(gs.gamemap.getoneboxmap(bombX+i+1, bombY) == 1)
						break;
				}
				else
					gs.gamemap.ChangeByUser(bombX+i, bombY, 5);					
			}
			else
				break;
		}
		for(int i = 1; i < power; i++)
		{			
			if(gs.gamemap.NoObstacle(bombX, bombY+i) || gs.gamemap.getoneboxmap(bombX, bombY+i) == 1)
			{
				if(gs.gamemap.getoneboxmap(bombX, bombY+i) == 1)
				{
					gs.gamemap.ChangeByUser(bombX, bombY+i, 5);
					if(gs.gamemap.getoneboxmap(bombX, bombY+i+1) == 1)
						break;
				}
				else
					gs.gamemap.ChangeByUser(bombX, bombY+i, 5);					
			}
			else
				break;
		}
		for(int i = 1; i < power; i++)
		{
			if(gs.gamemap.NoObstacle(bombX-i, bombY) || gs.gamemap.getoneboxmap(bombX-i, bombY) == 1)
			{
				if(gs.gamemap.getoneboxmap(bombX-i, bombY) == 1)
				{
					gs.gamemap.ChangeByUser(bombX-i, bombY, 5);
					if(gs.gamemap.getoneboxmap(bombX-i-1, bombY) == 1)
						break;
				}
				else
					gs.gamemap.ChangeByUser(bombX-i, bombY, 5);					
			}
			else
				break;
		}
		for(int i = 1; i < power; i++)
		{
			if(gs.gamemap.NoObstacle(bombX, bombY-i) || gs.gamemap.getoneboxmap(bombX, bombY-i) == 1)
			{
				if(gs.gamemap.getoneboxmap(bombX, bombY-i) == 1)
				{
					gs.gamemap.ChangeByUser(bombX, bombY-i, 5);
					if(gs.gamemap.getoneboxmap(bombX, bombY-i-1) == 1)
						break;
				}
				else
					gs.gamemap.ChangeByUser(bombX, bombY-i, 5);	
			}
			else
				break;
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
	public void setType(Bombtype t){
		type = t;
	}
	public Bombtype getType(){
		return type;
	}
	public int getCount()
	{
		return count;
	}
	public void resetCount()
	{
		count = 0;
	}
	public void startBomb()
	{
		start = true;
	}
		
}
