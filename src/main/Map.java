package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import processing.core.PImage;

public class Map implements Map_interface{//
	PImage ch;//for test wait for character
	private GameStage gs;
	private int boxweight = 45;
	private int boxheight = 40;
	private int weight, height;
	private int []map;
	PImage background,obstacle,obstacle_break,wall,floor,bomb,fire;
	
	static BufferedReader br = null;//read bit map 	
	
	//constructer version
	public Map(GameStage gs, int weight, int height)
	{
		this.weight = weight;
		this.height = height;
		this.gs = gs;
		map = new int[weight*height]; 
		loadmap();
		loadcharacter(gs.ch1.getName());
	}
	public void loadmap()
	{
		floor = gs.loadImage("floor.png");
		fire = gs.loadImage("fire.png");
		obstacle = gs.loadImage("obstacle.png");//this png not in source
		obstacle_break = gs.loadImage("obstacle_break.png");
		wall = gs.loadImage("wall.png");
		gs.println(gs.dataPath(""));
		bomb = gs.loadImage("bump_red.png");
		readerfile("src/data/grass.txt",weight);
	}
	public void loadcharacter(String name)
	{
		ch = gs.loadImage(name+"_front.png");
	}
	public void setBomb(Bombtype type)
	{
		if(type == Bombtype.bump_red)
			bomb = gs.loadImage("bump_red.png");
		else if(type == Bombtype.bump_s)
			bomb = gs.loadImage("bump_s.png");
		else
			bomb = gs.loadImage("bump.png");
		display();		
	}
	public void setCharacter(Direction t ,String name)
	{
		if(t == Direction.UP)
			ch = gs.loadImage(name+"_back.png");
		else if(t == Direction.RIGHT)
			ch = gs.loadImage(name+"_right.png");
		else if(t == Direction.RIGHTGO)
			ch = gs.loadImage(name+"_right_go.png");
		else if(t == Direction.LEFTGO)
			ch = gs.loadImage(name+"_left_go.png");
		else if(t == Direction.LEFT)
			ch = gs.loadImage(name+"_left.png");
		else
			ch = gs.loadImage(name+"_front.png");
	}
	public void display()
	{
		int i;
		for(i = 0; i < height*weight; i++)
		{
			switch(map[i])
			{
				case 1:
					gs.image(obstacle_break, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
					break;
				case 2:
					gs.image(obstacle, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
					break;
				case 3:
					gs.image(bomb, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
					break;
				case 4:
					gs.image(ch, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
					break;
				case 5:
					gs.image(fire, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
					break;
				case 8:
					gs.image(wall, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
					break;
				default:
					gs.image(floor, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
			}
		}
	}
	public void ChangeByUser(int X, int Y , int maptype)
	{
		if(X+weight*Y >= 0 && X+weight*Y < weight*height)
			map[X+weight*Y] = maptype;
	}
	
	public void readerfile(String filename, int weight) 
	{
		int i ;
		int j = 0;
		String[] trans;//array for split();
	    try{		     
	      br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));//new a input stream and set format
	      String in = null;// initial in string to null
	      while((in = br.readLine()) != null){	//if still get line form file	    	   
	             trans  = in.split(" ");
	             for(i = 0;i < weight ;i++)
	             {
	            	 map[i+weight*j] = Integer.valueOf(trans[i]);
	             }
	        j++;
	      }
	    }catch(IOException ioe){//exception control
	    	ioe.printStackTrace();
	    }   
	 }	
	public boolean NoObstacle(int X,int Y)
	{
		if(X+weight*Y >= 0 && X+weight*Y < weight*height)
		{
			if(map[X+weight*Y] == 0)
				return true;
			else
				return false;	
		}
		else
			return true;
	}
	public int getoneboxmap(int X,int Y)
	{
		if(X+weight*Y >= 0 && X+weight*Y < weight*height)
			return map[X+weight*Y];
		else
			return -1;
	}
}
