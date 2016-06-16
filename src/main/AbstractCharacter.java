package main;

import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JPanel;

import processing.core.PImage;


public abstract class AbstractCharacter extends JPanel{
	private static final long serialVersionUID = 1L;
	protected GameStage gs;
	public PImage image = null;
	protected PImage up,right,right_go,left,left_go,down;
	protected String name;
	public Direction d;
	protected int life = 1;
	protected int boxweight = 45;
	protected int boxheight = 40;
	protected int id;//the number of player
	protected int bombPower = 3;
	protected boolean isActive;
	public int next_x = 0, next_y = 0;
	//for reset
	protected int initial_X, initial_Y,initial_bombnum;
	protected int initial_power = 3;
	//prop
	protected Timer timer = new Timer();
	//bomb
	protected ArrayList<Bomb> bomb;
	protected int currentbomb;
	protected int bombnumber;
	protected int totalbomb;
	protected boolean Nobomb = false;
	
	abstract public String getName();
	abstract public void initial();
	abstract public void setName(String na);
	abstract public void setActive();
	abstract public void disActive();
	abstract public void move(Direction t,boolean forward);
	abstract public void bombput();
	abstract public void reset();		
	
}
