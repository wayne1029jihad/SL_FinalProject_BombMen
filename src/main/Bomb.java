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
						//ï¿½î¾«?¶ï?ï¿½ï??­ï¿½ï¿½ï?ï¿½î¡¼?¦é–¬î¼¼î?ï¿½îž¥ï¿½î?
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
					//ï¿½î??ˆï?ï¿½ï?î¾«æ•º?¼î¾«?¶ï???¿½î·›è­?’ï¿½?™ï¿½?’ï¿½î¡¼è?
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
