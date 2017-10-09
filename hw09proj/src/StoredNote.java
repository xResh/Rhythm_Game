
public class StoredNote {
	private int starttime;
	private int endtime;
	private char key;
	public StoredNote(int st, int et, char k) {
		key = k;
		starttime = st;
		endtime = et;
	}
	
	public int getStartTime(){
		return this.starttime;
	}
	
	public int getEndTime(){
		return this.endtime;
	}
	
	public char getKey(){
		return this.key;
	}
	
	public boolean isHold(){
		return !(this.endtime == -1);
	}
}
