package main;


import java.io.IOException;
import java.util.Random;
import processing.core.PImage;

public class PropGenerator {
	private PImage fireup, DEF, bump_add, bump_NO;
	private Map map;
	private GameStage gs;
	private int boxweight = 45;
	private int boxheight = 40;
	private int weight, height;
	private int []propmap;
	private int []Rmap;
	private Random ran = new Random();
	
	
	public PropGenerator(GameStage gs, Map map, int weight,int height,int []arr,int []ram)
	{
		this.gs = gs;
		this.map = map;
		this.weight = weight;
		this.height = height;
		propmap = new int[weight*height];
		Rmap = arr;
		for(int i = 0; i < weight*height; i++)
		{
			propmap[i] = ram[i];
		}
		loadImage();
		//setProp();
	}
	public void loadImage()
	{
		fireup = gs.loadImage("fireup.png");
		DEF = gs.loadImage("DEF.png");
		bump_add = gs.loadImage("bump_add.png");
		bump_NO = gs.loadImage("bump_NO.png");
	}
	public void setProp()
	{
		int i ,ram;
		for(i = 0; i < height*weight; i++)
		{
			if(propmap[i] == 1)
			{
				ram = ran.nextInt(15);
				if(ram == 0)
					propmap[i] = 4;
				if(ram == 1)
					propmap[i] = 5;
				if(ram == 2)
					propmap[i] = 6;
				if(ram == 3)
					propmap[i] = 7;
			}
		}
	}
	public void setProptoZero(int position)
	{
		propmap[position] = 0;
	}
	public void display()
	{
		int i;
		for(i = 0; i < height*weight; i++)
		{
			if(Rmap[i] == 0)
			{
				switch(propmap[i])
				{
					case 4:
						gs.image(fireup, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
						break;
					case 5:
						gs.image(DEF, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
						break;
					case 6:
						gs.image(bump_add, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
						break;
					case 7:
						gs.image(bump_NO, (i%weight)*boxweight, (i/weight)*boxheight,boxweight,boxheight);
						break;			
				}
			}
			getprop(i);
		}
	}
	public  void getprop(int position)
	{
		if(gs.self.next_x+gs.self.next_y*weight == position)
		{
			switch(propmap[position])
			{
				case 4:
					gs.client.sendMessage("PROP@"+gs.self.getid()+"@U@"+position);
					break;
				case 5:
					gs.client.sendMessage("PROP@"+gs.self.getid()+"@D@"+position);
					break;
				case 6:
					gs.client.sendMessage("PROP@"+gs.self.getid()+"@A@"+position);
					break;
				case 7:
					gs.client.sendMessage("PROP@"+gs.self.getid()+"@N@"+position);
					break;	
			}			
			propmap[position] = 0;			
		}
	}
}
