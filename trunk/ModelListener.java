public interface ModelListener {

	public void attackSquare(int x, int y) throws IOException;
	
	public void processResult(boolean hit) throws IOException;

}
