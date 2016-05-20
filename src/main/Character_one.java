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
	public Direction d = Direction.DOWN;
	
	public Character_one(GameStage g){
		initial(g);
		//super.setSkill(new Punch());
	}
	public String getName(){
		s = "Player1";
		return s;
	}
	public void initial(GameStage gs){
		getName();
		setActive();
		setStartScore();
		setPowerTimes();
		
		try{
			//image = ImageIO.read(new File("src\\angry_bird.jpg") );
			
			if(d == Direction.DOWN){
				image = gs.loadImage("angry_bird.jpg");
			}
			else if(d == Direction.UP){
				image = gs.loadImage("angry_bird.jpg");
			}
			else if(d == Direction.LEFT){
				image = gs.loadImage("left.jpg");
			}
			else if(d == Direction.RIGHT){
				image = gs.loadImage("right.jpg");
			}
			
		}catch(Exception ex){
			System.out.println("NO EXAMPLE.JPG");
		}
		setImage(image);
	}
}
