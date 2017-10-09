
public enum Scoring {
	PERFECT(200),
	GOOD(50),
	OK(20),
	MISS(0),
	EMPTY(-1);
	
	private final int value;

    Scoring(int value) {
        this.value = value;
    }

    public int getNum() {
        return value;
    }
}
