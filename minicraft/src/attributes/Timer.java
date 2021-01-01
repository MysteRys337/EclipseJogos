package attributes;

public class Timer {

	private int numberOfTicks;
	private int time;
	
	public Timer(int numberOfTicks) {
		this.numberOfTicks = numberOfTicks;
		this.time          = this.numberOfTicks;
	}
	
	public Timer() {
		this.numberOfTicks = 60;
		this.time          = 60;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public int getNumberOfTicks() {
		return this.numberOfTicks;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void setNumberOfTicks(int numberOfTicks) {
		this.numberOfTicks = numberOfTicks;
	}
	
	public void tick() {
		if(time > 0)
			time--;
	}
	
	public void reset() {
		this.time = this.numberOfTicks;
	}
	
	public boolean is_stopped() {
		return (this.time == 0);
	}
	
	
}
