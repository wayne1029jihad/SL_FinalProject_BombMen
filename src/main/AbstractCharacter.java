package main;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import processing.core.PImage;

public abstract class AbstractCharacter extends JPanel{
	private Vector<AbstractSkill> skills = new Vector<AbstractSkill>();	
	protected GameStage gs;
	private boolean isActive;
	abstract public String getName();
	abstract public void initial(GameStage g,int num);;
	private int NOW_SCORE, WIN_SCORE;
	private int powertimes;	
	
	public void setActive(){
		isActive = true;
	}
	public void disActive(){
		isActive = false;
	}
	public boolean isActive(){
		return isActive;
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
	
}
