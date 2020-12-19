package entities.player;

public class PlayerAction {

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	private boolean action;
	
	public PlayerAction() {
		this.up = this.down = this.left = this.right = this.action = false;
	}
	
	public boolean getUp() {
		return this.up;
	}
	
	public boolean getDown() {
		return this.down;
	}
	
	public boolean getLeft() {
		return this.left;
	}
	
	public boolean getRight() {
		return this.right;
	}
	
	public boolean getAction() {
		return this.action;
	}
	
	public void setUp(boolean up) {
		this.up = up;
	}
	
	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setAction(boolean action) {
		this.action = action;
	}
	
	public String print() {
		return "up     = " + up     + "\n" +
			   "down   = " + down   + "\n" +
			   "left   = " + left   + "\n" +
			   "right  = " + right  + "\n" +
			   "action = " + action + "\n";
	}
}
