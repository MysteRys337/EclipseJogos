package controls;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.player.PlayerAction;

public class Control {
	
	private static PlayerAction player = new PlayerAction();

	public static PlayerAction pressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_W)
			player.setUp(true);
		
		else if(e.getKeyCode() == KeyEvent.VK_S)
			player.setDown(true);
		
		if(e.getKeyCode() == KeyEvent.VK_D)
			player.setRight(true);
		
		else if(e.getKeyCode() == KeyEvent.VK_A)
			player.setLeft(true);
		
		return player;
	}

	public static PlayerAction released(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_W)
			player.setUp(false);
		
		else if(e.getKeyCode() == KeyEvent.VK_S)
			player.setDown(false);
		
		if(e.getKeyCode() == KeyEvent.VK_D)
			player.setRight(false);
		
		else if(e.getKeyCode() == KeyEvent.VK_A)
			player.setLeft(false);
		
		return player;
	}
	
	public static PlayerAction mouse_pressed(MouseEvent e,int scale) {
		
		player.setAction(true);
		player.setX(e.getX() / scale);
		player.setY(e.getY() / scale);
		
		return player;
	}
	
	public static PlayerAction mouse_released(MouseEvent e,int scale) {
		
		player.setAction(false);
		player.setX(e.getX() / scale);
		player.setY(e.getY() / scale);
		
		return player;
	}

}
