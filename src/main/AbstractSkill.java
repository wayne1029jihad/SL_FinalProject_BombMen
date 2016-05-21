package main;
import java.awt.image.BufferedImage;

public abstract class AbstractSkill {
	private int power,accuracy;//what accuracy use to?
	private BufferedImage bomb,fire; 
	int number;//the number of bombs
	int bombX,bombY;
	private int state; 
	protected GameStage gs;
	abstract public void initial(int num,int power,int accuracy,BufferedImage bomb,BufferedImage fire);
	public void setPower(int p){
		power = p;
	}
	public int getPower(){
		return power;
	}
	public void setAccuracy(int a){
		accuracy = a;
	}
	public int getAccuracy(){
		return accuracy;
	}
	public void setImage(BufferedImage buffer,BufferedImage fire){
		this.bomb = buffer;
		this.fire=fire;
		}
	public void setNumber(int num)
	{
		this.number=num;
	}
	public int getNumber()
	{
		return this.number;
	}
	public void setBombX(int x)
	{
		bombX=x;
	}
	public int getBombX()
	{
		return bombX;
	}
	public void setBombY(int y)
	{
		bombY=y;
	}
	public int getBombY()
	{
		return bombY;
	}
}
