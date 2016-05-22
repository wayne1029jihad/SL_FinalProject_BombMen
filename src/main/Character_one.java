package main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

import processing.core.PImage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Character_one extends AbstractCharacter{
	public PImage image = null;
	private String s;
	public Direction d;
	public int next_x = 0, next_y = 0;
	public Bomb bomb;
	private int number;//the number of bombs
	
	public Character_one(GameStage g, String name, int initial_X, int initial_Y, int num){
		initial(g,num);
		s = name;
		next_x = initial_X;
		next_y = initial_Y;
		//super.setSkill(new Punch());
	}
	public String getName(){
		return s;
	}
	public void initial(GameStage gs, int num){
		//getName();//before you use this to get player1 but every one are different name decide by server
		setNumber(num);
		setActive();
		setStartScore();
		setPowerTimes();
		bomb = new Bomb(1,3,gs);
		//setCharacter(Direction.DOWN,"ch1");
	}
	public void setNumber(int num)
	{
		this.number=num;
	}
	public int getNumber()
	{
		return this.number;
	}
}
