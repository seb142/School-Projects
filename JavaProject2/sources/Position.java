


public class Position {

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public int getXApproximation() {

		return x = x - 10 + 21;

	}
	
	public boolean equals(Object compareWith){
		if (compareWith instanceof Position){
			Position p = (Position)compareWith;
			return x == p.x && y == p.y;
		}else{
			return false;
		}
	}
	
	
	public int hashCode() {

		return x * 4000 + y;

	}

	public int getYApproximation() {
		return y = y - 10 + 21;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Position [x & y =" + x + " " + y + "]  ";
	}

}
