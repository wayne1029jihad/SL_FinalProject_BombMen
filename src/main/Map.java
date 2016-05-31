package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import processing.core.PImage;

public class Map implements Map_interface{//
	private GameStage gs;
	private int boxweight = 45;
	private int boxheight = 40;
	private int weight, height;
	private int []map;
	PImage background,obstacle,obstacle_break,wall,floor,bomb;
	
	static BufferedReader br = null;//read bit map 	
	
	//constructer version
	public Map(GameStage gs, int weight, int height)
	{
		this.weight = weight;
		this.height = height;
		this.gs = gs;
		map = new int[weight*height]; 
		loadmap();
	}
	public void loadmap()
	{
		floor = gs.loadImage("floor.png");
		obstacle = gs.loadImage("obstacle.png");//this png not in source
		obstacle_break = gs.loadImage("obstacle_break.png");
		wall = gs.loadImage("wall.png");
		//gs.println(gs.dataPath(""));
		bomb = gs.loadImage("bump_red.png");
		readerfile("src/data/grass.txt",weight);
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
