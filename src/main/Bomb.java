package main;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
public class Bomb extends AbstractSkill{
	TimerTask changeToFire;
	Timer timer;
	private Bombtype type;
	@Override
	public void initial(int num,int power,int accuracy,BufferedImage bomb,BufferedImage fire) {
		setNumber(num);
		setAccuracy(accuracy);
		setPower(power);
		setImage(bomb,fire);
		timer= new Timer();
	}
	public void launch(Character ch)
	{
		if(getNumber()>0)
		{
			//setBombX(ch.getX());
			//setBombY(ch.getY());
			changeToFire=new TimerTask()
					{
						//�?��?��??����?�?�閬�?���?
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
						}
				
					};
			timer.schedule(changeToFire,getPower());
			for(int x=getBombX()-getAccuracy();x<=getBombX()+getAccuracy();x++)
			{
				for(int y=getBombY()-getAccuracy();y<=getBombY()+getAccuracy();y++)
				{
					//��??��?��?敺?�?��???��蝭?��?��?���?
					if((x-getBombX())*(x-getBombX())+(y-getBombY())*(y-getBombY())<=getAccuracy()*getAccuracy())
					{
						
					}
				}
			}
			setNumber(getNumber()-1);
		}
		
	}
	public void setType(Bombtype t){
		type = t;
	}
	public Bombtype getType(){
		return type;
	}
		
	
}
