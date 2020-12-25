package entities.player;

public class PlayerAction {
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	private int xTarget;
	private int yTarget;
	
	private boolean action;
	
	public PlayerAction() {
		this.xTarget = 0;
		this.yTarget = 0;
	
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
	
	public int getX() {
		return this.xTarget;
	}
	
	public int getY() {
		return this.yTarget;
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
	
	public void setX(int xTarget) {
		this.xTarget = xTarget;
	}
	
	public void setY(int yTarget) {
		this.yTarget = yTarget;
	}
	
	public void setAction(boolean action) {
		this.action = action;
	}
	
	public String print() {
		return "xTarget = " + xTarget + "\n" +
			   "yTarget = " + yTarget + "\n" +
			   "up      = " + up      + "\n" +
			   "down    = " + down    + "\n" +
			   "left    = " + left    + "\n" +
			   "right   = " + right   + "\n" +
			   "action  = " + action  + "\n";
	}
}
