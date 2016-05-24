package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ProtectedShirts {
	BufferedImage shirts;
	int x,y;
	boolean iswear=false;
	AbstractCharacter ch;
	ProtectedShirts(AbstractCharacter ch,int x,int y) throws IOException
	{
		shirts = ImageIO.read(new File(""));
		this.x=x;
		this.y=y;
		this.ch=ch;
	}
	public int getShirtsX()
	{
		return x;
	}
	public int getShirtsY()
	{
		return y;
	}
	public void setwear(boolean iswear)
	{
		iswear=true;
		ch.setXP(ch.getXP()+1);
	}
	public boolean iswearing()
	{
		return iswear;
	}
}
