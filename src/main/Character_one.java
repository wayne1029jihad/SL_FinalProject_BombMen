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

public class Character_one extends AbstractCharacter{
	public BufferedImage image = null;
	public Image ima;
	private String s;
	
	public Character_one(GameStage g){
		initial();
		//super.setSkill(new Punch());
	}
	public String getName(){
		s = "Player1";
		return s;
	}
	public void initial(){
		getName();
		setActive();
		setStartScore();
		setPowerTimes();
		
		try{
			image = ImageIO.read(new File("src\\resource\\angry_bird.jpg") );
		}catch(Exception ex){
			System.out.println("NO EXAMPLE.JPG");
		}
		setImage(image);
	}
}
