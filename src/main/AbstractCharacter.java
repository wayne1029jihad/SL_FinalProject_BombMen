package main;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import processing.core.PImage;

public abstract class AbstractCharacter extends JPanel{
	private boolean isActive;
	
	private Vector<AbstractSkill> skills = new Vector<AbstractSkill>();
	private PImage image;
	
	protected GameStage gs;
	abstract public String getName();
	abstract public void initial(GameStage s);
	abstract public void setCharacter(Direction d, String name);
	
	private int NOW_SCORE, WIN_SCORE;
	
	//public int x,y;
	public int powertimes;
	public int next_x = 0, next_y = 0;
	public int record_direction[] = new int[4];////To record the origin movement and next movement (x1, y1) & (x2, y2)
	
	public void setActive(){
		isActive = true;
	}
	public void disActive(){
		isActive = false;
	}
	public boolean isActive(){
		return isActive;
	}
	public void setImage(PImage bi){
		image = bi;
	}
	public PImage getImage(){
		return image;
	}
	public void setSkill(AbstractSkill s){
		skills.add(s);
	}
	public Vector<AbstractSkill> getSkills(){
		return skills;
	}
	
	public void setStartScore(){
		NOW_SCORE = 0;
	}
	public void addScore(){
		NOW_SCORE += 1;
	}
	public int getNowScore(){
		return NOW_SCORE;
	}
	public void setNowScore(int score){
		NOW_SCORE = score;
	}
	
	public void setPowerTimes(){
		powertimes = 1;
	}
	public void addPowerTimes(){
		powertimes += 1;
	}
	public int getPowerTimes(){
		return powertimes;
	}
	/*public void Move(int px, int py){
		x = px;
		y = py;
	}*/
	
}
