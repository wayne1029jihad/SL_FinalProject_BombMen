package main;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
public class randomGenerator {
	private int []propmap;
	private Random ran = new Random();
	private int weight, height;
	
	static BufferedReader br = null;//read bit map
	
	public randomGenerator(int weight,int height)
	{
		this.weight = weight;
		this.height = height;
		propmap = new int[weight*height];
		readerfile("src/data/grass.txt",weight);
		setProp();
	}
	
	public void setProp()
	{
		int i ,ram;
		for(i = 0; i < height*weight; i++)
		{
			if(propmap[i] == 1)
			{
				ram = ran.nextInt(13);
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
	            	 propmap[i+weight*j] = Integer.valueOf(trans[i]);
	             }
	        j++;
	      }
	    }catch(IOException ioe){//exception control
	    	ioe.printStackTrace();
	    }   
	 }	
	public String getPropMap()
	{
		String S, Ss;
		S = Integer.toString(propmap[0]);
		for(int i = 1; i <propmap.length; i++)
		{
			Ss = "@"+Integer.toString(propmap[i]);
			S = S+Ss;
		}
		return S;
	}
}
