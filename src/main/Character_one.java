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
	public Image ima;
	private String s;
	private int now_x,now_y;//character
	public Direction d;
	
	public int boxweight = 45, boxheight = 40;
	public int next_x = 0, next_y = 0;
	
	public Character_one(GameStage g, String name, int initial_X, int initial_Y){
		initial(g);
		s = name;
		next_x = initial_X;
		next_y = initial_Y;
		//super.setSkill(new Punch());
	}
	public String getName(){
		return s;
	}
	public void initial(GameStage gs){
		//getName();//before you use this to get player1 but every one are different name decide by server
		setActive();
		setStartScore();
		setPowerTimes();
		//setCharacter(Direction.DOWN,"ch1");
	}
	/*
	public void setCharacter(Direction t ,String name)
	{
		 gs.println(gs.dataPath(""));
		if(t == Direction.UP)
			image = gs.loadImage(name+"_back.png");
		else if(t == Direction.RIGHT)
			image = gs.loadImage(name+"_right.png");
		else if(t == Direction.RIGHTGO)
			image = gs.loadImage(name+"_right_go.png");
		else if(t == Direction.LEFTGO)
			image = gs.loadImage(name+"_left_go.png");
		else if(t == Direction.LEFT)
			image = gs.loadImage(name+"_left.png");
		else
			image = gs.loadImage(name+"_front.png");
	}*/
}
