package controls;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.player.PlayerAction;

public class Control {
	
	private static PlayerAction player = new PlayerAction();

	public static PlayerAction pressed(KeyEvent e) {
		
		boolean defaultValue = true;
		
		if(e.getKeyCode() == KeyEvent.VK_W)
			player.setUp(defaultValue);
		
		else if(e.getKeyCode() == KeyEvent.VK_S)
			player.setDown(defaultValue);
		
		if(e.getKeyCode() == KeyEvent.VK_D)
			player.setRight(defaultValue);
		
		else if(e.getKeyCode() == KeyEvent.VK_A)
			player.setLeft(defaultValue);
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) 
			player.setSpacebar(defaultValue);
		
		if(e.getKeyCode() == KeyEvent.VK_Q)
			player.setQ(defaultValue);
		
		else if(e.getKeyCode() == KeyEvent.VK_E)
			player.setE(defaultValue);
		
		return player;
	}

	public static PlayerAction released(KeyEvent e) {
		
		boolean defaultValue = false;
		
		if(e.getKeyCode() == KeyEvent.VK_W)
			player.setUp(defaultValue);
		
		else if(e.getKeyCode() == KeyEvent.VK_S)
			player.setDown(defaultValue);
		
		if(e.getKeyCode() == KeyEvent.VK_D)
			player.setRight(defaultValue);
		
		else if(e.getKeyCode() == KeyEvent.VK_A)
			player.setLeft(defaultValue);
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) 
			player.setSpacebar(defaultValue);
		
		if(e.getKeyCode() == KeyEvent.VK_Q)
			player.setQ(defaultValue);
		
		else if(e.getKeyCode() == KeyEvent.VK_E)
			player.setE(defaultValue);
		
		return player;
	}
	
	public static PlayerAction mouse_pressed(MouseEvent e,int scale) {
		
		if(e.getButton() == MouseEvent.BUTTON1) 
			player.setMouse1(true);
		
		else if(e.getButton() == MouseEvent.BUTTON3) 
			player.setMouse2(true);
		
		player.setX(e.getX() / scale);
		player.setY(e.getY() / scale);
		
		return player;
	}
	
	public static PlayerAction mouse_released(MouseEvent e,int scale) {
		
		if(e.getButton() == MouseEvent.BUTTON1) 
			player.setMouse1(false);
		
		else if(e.getButton() == MouseEvent.BUTTON2) 
			player.setMouse2(false);
		
		player.setX(e.getX() / scale);
		player.setY(e.getY() / scale);
		
		return player;
	}

}
