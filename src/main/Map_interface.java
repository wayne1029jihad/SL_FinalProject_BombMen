package main;

public interface Map_interface  {
	void loadmap();//load map information using Json, like image, mapsize , coordinate
	public void display();//using processing to show image
	public void ChangeByUser(int x, int y , int type);//when user do something, map will show the change
	public void readerfile(String filename, int weight); 
	public void setBomb(int state);
	public void loadcharacter(String name);
	public boolean obstacledetect(int X,int Y);
}
