package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
public class Map extends PApplet implements Map_interface{//
	private Character characters1;//for test
	private Character characters2;//for test
	PImage ch1,ch2;//for test wait for character
	private GameStage gs;
	private int boxweight = 45;
	private int boxheight = 40;
	private int weight, height;
	private int []map;
	PImage background,obstacle,obstacle_break,wall,floor,bomb;
	
	static BufferedReader br = null;//read bit map 	
	
	public void setup()//for test
	{
		size(800, 750);
		weight = 15;
		height = 13;
		println(dataPath(""));
		map = new int[weight*height]; 
		loadmap();
		loadcharacter();
	}
	/*constructer version
	public Map(GameStage gs, int weight, int height)
	{
		size(800, 750);
		weight = 15;
		height = 13;
		println(dataPath(""));
		map = new int[weight*height]; 
		loadmap();
	}*/
	public void loadmap()
	{
		floor = loadImage("floor.png");	
		obstacle = loadImage("obstacle.png");//this png not in source
		obstacle_break = loadImage("obstacle_break.png");
		wall = loadImage("wall.png");
				readerfile("grass.txt",weight);
	}
	public void loadcharacter()
	{
		ch1 = loadImage("CH1_front.png");
		ch2 = loadImage("CH2_front.png");	

	}
	public void setBomb(int state)
	{
		if(state == 2)
			bomb = loadImage("bump_red.png");
		else if(state == 1)
			bomb = loadImage("bump_s.png");
		else
			bomb = loadImage("bump.png");
		draw();//display();		
	}
	//public void display()
	public void draw()
	{
		int i;
		for(i = 0; i < height*weight; i++)
		{
			switch(map[i])
			{
				case 1:
					image(obstacle_break, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
					break;
				case 2:
					image(obstacle, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
					break;
				case 3:
					image(bomb, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
					break;
				case 8:
					image(wall, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
					break;
				default:
					image(floor, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
			}
		}
	}
	public void ChangeByUser(int x, int y , int type)
	{
		map[weight*y+x] = type;
	}
	
	public void readerfile(String filename, int weight) 
	{
		int i ;
		int j = 0;
		String[] trans;//array for split();
	    try{		     
	      br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf8"));//new a input stream and set format
	      String in = null;// initial in string to null
	      while((in = br.readLine()) != null){	//if still get line form file	    	   
	             System.out.println(in);
	             trans  = in.split(" ");
	             for(i = 0;i < weight ;i++)
	             {
	            	 System.out.println(Integer.valueOf(trans[i]));
	            	 map[i+weight*j] = Integer.valueOf(trans[i]);
	             }
	        j++;
	      }
	    }catch(IOException ioe){//exception control
	    	ioe.printStackTrace();
	    }   
	 }	
}
