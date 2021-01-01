package entities.player;

public class PlayerAction {
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	private int xTarget;
	private int yTarget;
	
	private boolean mouse1;
	private boolean mouse2;
	
	private boolean spacebar;
	private boolean q;
	private boolean e;
	
	public PlayerAction() {
		this.xTarget = 0;
		this.yTarget = 0;
	
		this.up = this.down = this.left = this.right = this.mouse1 =
		this.mouse2 = this.spacebar = this.q = this.e = false;
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
	
	public boolean getMouse1() {
		return this.mouse1;
	}
	
	public boolean getMouse2() {
		return this.mouse2;
	}
	
	public boolean getSpacebar() {
		return this.spacebar;
	}
	
	public boolean getQ() {
		return this.q;
	}
	
	public boolean getE() {
		return this.e;
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
	
	public void setMouse1(boolean mouse1) {
		this.mouse1 = mouse1;
	}
	
	public void setMouse2(boolean mouse2) {
		this.mouse2 = mouse2;
	}
	
	public void setSpacebar(boolean spacebar) {
		this.spacebar = spacebar;
	}
	
	public void setQ(boolean q) {
		this.q = q;
	}
	
	public void setE(boolean e) {
		this.e = e;
	}
	
	public String print() {
		return "xTarget = " + xTarget + "\n" +
			   "yTarget = " + yTarget + "\n" +
			   "up      = " + up      + "\n" +
			   "down    = " + down    + "\n" +
			   "left    = " + left    + "\n" +
			   "right   = " + right   + "\n" +
			   "action  = " + mouse1  + "\n";
	}
}
