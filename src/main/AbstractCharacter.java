package main;

import java.util.Vector;

import javax.swing.JPanel;


public abstract class AbstractCharacter extends JPanel{
	private static final long serialVersionUID = 1L;
	private Vector<AbstractSkill> skills = new Vector<AbstractSkill>();	
	protected GameStage gs;
	private boolean isActive;
	abstract public String getName();
	abstract public void initial();
	private int NOW_SCORE;
	private int powertimes;	
	private int life = 1;
	protected int bombnumber = 0;
	public void bombput()
	{
		bombnumber = bombnumber - 1;
	}
	public void bombrecover()
	{
		bombnumber = bombnumber + 1;
	}
	public boolean canputbomb(){
		if (bombnumber == 0)
			return false;
		else
			return true;
	}
	public void setXP(int life)
	{
		this.life=life;
		if(life==0)
			isActive=false;
		else isActive=true;
	}
	public int getXP()
	{
		return life;
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
