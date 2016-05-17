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
	private int weight, height;
	private int []map;
	PImage background,obstacle,obstacle_break,wall,floor;
	
	static BufferedReader br = null;//read bit map 	
	
	public void setup()//for test
	{
		size(800, 750);
		weight = 15;
		height = 13;
		println(dataPath(""));
		map = new int[weight*height]; 
		loadmap();		
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
		obstacle = loadImage("bump.png");//this png not in source
		obstacle_break = loadImage("obstacle_break.png");
		wall = loadImage("bump_red.png");//this png not in source¡Ause rad bomb 
		readerfile("grass.txt",weight);
	}
	public void draw()
	{
		int i;
		for(i = 0; i < height*weight; i++)
		{
			switch(map[i])
			{
				case 1:
					image(obstacle_break, (i%weight)*45, (i/weight)*40,45,40);
					break;
				case 2:
					image(obstacle, (i%weight)*45, (i/weight)*40,45,40);
					break;
				case 8:
					image(wall, (i%weight)*45, (i/weight)*40,45,40);
					break;
				default:
					image(floor, (i%weight)*45, (i/weight)*40,45,40);
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
