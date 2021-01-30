package entities.player;

public class PlayerAction {
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	private int xTarget;
	private int yTarget;
	
	private boolean shoot;
	
	private boolean q;
	private boolean e;
	
	public PlayerAction() {
		this.xTarget = 0;
		this.yTarget = 0;
	
		this.up = this.down = this.left = this.right = this.shoot =
		this.q = this.e = false;
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
	
	public boolean getShoot() {
		return this.shoot;
	}
	
	public int getX() {
		return this.xTarget;
	}
	
	public int getY() {
		return this.yTarget;
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
	
	public void setQ(boolean q) {
		this.q = q;
	}
	
	public void setE(boolean e) {
		this.e = e;
	}
	
	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}
	
	public String print() {
		return "xTarget = " + xTarget + "\n" +
			   "yTarget = " + yTarget + "\n" +
			   "up      = " + up      + "\n" +
			   "down    = " + down    + "\n" +
			   "left    = " + left    + "\n" +
			   "right   = " + right   + "\n" +
			   "shoot   = " + shoot   + "\n";
	}
}
