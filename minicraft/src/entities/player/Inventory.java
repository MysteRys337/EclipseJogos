package entities.player;

import java.awt.Color;
import java.awt.Graphics;

import graphics.UI;
import items.Item;

public class Inventory extends UI {
	
	private byte selectionIndex;
	
	private final byte boxSize;
	
	private int    initialPosition;
	private Item[] items;
	private byte numberOfItems;

	public Inventory(int width, int height, int scale) {
		super(width, height, scale);
		
		int n = 6;
		
		this.numberOfItems = 0;
		
		this.boxSize = 16;
		this.items = new Item[n];
		
		this.initialPosition = (width/scale) + (scale*scale);
		
		this.selectionIndex = 0;
	}
	
	public boolean isInventoryFull() {
		return this.numberOfItems == items.length -1;
	}
	
	public Item getCurrentItem() {
		return items[selectionIndex];
	}
	
	public void addItem(Item i) {
		for (int j = 0; j < items.length-1; j++) {
			
			if(items[j] == null) {
				items[j] = i;
				numberOfItems++;
				break;
			}
	
			if(items[j].getClass() == i.getClass()) 
				break;
		}
	}
	
	public void tick(PlayerAction action) {
		
		if(action.getQ()) {
			
			action.setQ(false);
			
			if(selectionIndex == 0)
				selectionIndex = (byte)(items.length -1);
			else 
				selectionIndex--;
			
		} else if (action.getE()) {
			
			action.setE(false);
			
			if(selectionIndex == items.length -1)
				selectionIndex = 0;
			else 
				selectionIndex++;
			
		} else if(action.getMouse1() && isInsideInventoryBox(action)) {
			action.setMouse1(false);
			
			selectionIndex = (byte)((action.getX()-initialPosition)/boxSize);
		}
	}
	
	private boolean isInsideInventoryBox(PlayerAction action) {
		//System.out.println(action.getX() + "|" + action.getY());
		return (action.getX() >= initialPosition && action.getX() < initialPosition + (boxSize*items.length) &&
				action.getY() >= boxSize  && action.getY() < boxSize+boxSize);
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < items.length; i++) {
			g.setColor(Color.gray);
			g.fillRect(initialPosition + (i*boxSize), boxSize, boxSize, boxSize);

			if(items[i] != null) {
				g.drawImage(items[i].getSprite().getBufferedImage(),initialPosition + (i*boxSize), boxSize, boxSize, boxSize,null);
			}
			
			g.setColor(Color.black);
			g.drawRect(initialPosition + (i*boxSize), boxSize, boxSize, boxSize);
	
		}
		
		g.setColor(Color.red);
		g.drawRect(initialPosition + (selectionIndex*boxSize), boxSize, boxSize, boxSize);
		
	}

}
