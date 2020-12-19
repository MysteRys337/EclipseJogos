package controls;

import java.awt.event.KeyEvent;

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
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			player.setAction(true);
		
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
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			player.setAction(false);
		
		return player;
	}

}
