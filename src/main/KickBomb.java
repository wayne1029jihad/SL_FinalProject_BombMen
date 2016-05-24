package main;

public class KickBomb {
	
	public void Kick(Bomb bomb,AbstractCharacter ch)
	{
		bomb.setBombPosition(ch.getX(),ch.getY());
	}
}
