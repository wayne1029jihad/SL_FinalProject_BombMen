package main;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;


public abstract class AbstractCharacter extends JPanel{
	private static final long serialVersionUID = 1L;
	private Vector<AbstractSkill> skills = new Vector<AbstractSkill>();	
	protected GameStage gs;
	protected boolean isActive;
	abstract public String getName();
	abstract public void initial();
	protected ArrayList<Bomb> bomb;
	protected int currentbomb;
	protected int bombnumber;
	protected int totalbomb;
	public int next_x = 0, next_y = 0;
	protected boolean Nobomb = false;
	
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
	
	public void setActive(){
		isActive = true;
	}
	public void disActive(){
		isActive = false;
	}
	public boolean isActive(){
		return isActive;
	}		
}
