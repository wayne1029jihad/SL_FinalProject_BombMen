package main;

import javax.swing.JFrame;

/**
* This class is the main class of the program.
* You can customize different window width and height for your program here. 
*
* @author  Richo
* @version 1.0
* @since   2016-04-26 
*/
public class Main extends JFrame{
	private static final long serialVersionUID = 1L;
	private final static int windowWidth = 1200, windowHeight = 670;
	
	public static void main(String [] args){
		
		GameStage game = new GameStage();
		game.init();
		game.start();
		game.setFocusable(true);
		
		JFrame window = new JFrame("BombMen");
		window.setContentPane(game);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(windowWidth, windowHeight);
		window.setVisible(true);
	}
}
