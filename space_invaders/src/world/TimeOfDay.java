package world;

public enum TimeOfDay {
	
	Morning((byte)0),
	Afternoon((byte)1),
	Evening((byte)2);
	
	public byte timeOfday;
	
	TimeOfDay(byte timeOfDay) {
		this.timeOfday = timeOfDay;
	}
	
	public byte getTimeOfDay() {
		return this.timeOfday;
	}
	
	public void setTimeOfDay(byte timeOfDay) {
		this.timeOfday = timeOfDay;
	}
	
	public static TimeOfDay nextPhase(TimeOfDay tod) {
		
		if(tod == Morning) 
			tod = Afternoon;
		
		 else if(tod == Afternoon) 
			tod = Evening;
		
		 else
			tod = Morning;
		
		return tod;
		
	}
}
