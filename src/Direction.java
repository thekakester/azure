
public enum Direction {
	UP,DOWN,LEFT,RIGHT,NONE;
	static Direction getDirection(int deltaX, int deltaY) {
		if (deltaX < 0) { return LEFT; }
		if (deltaX > 0) { return RIGHT; }
		if (deltaY < 0) { return UP; }
		return DOWN;
	}
}
