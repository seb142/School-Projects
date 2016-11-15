

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public abstract class Place extends JComponent implements MouseListener {
	private boolean visible = true;
	protected boolean unfolded = false;
	protected boolean marked = false;
	protected String name;
	protected String category;
	protected Position position;
	private int d1 = 22;
	private int d2 = 22;
	private Color color;
	protected int x;
	protected int y;
	private Polygon polygon = new Polygon(new int[] { 0, 10, 20 }, new int[] { 0, 20, 0 }, 3);

	public Place(String name, boolean marked, boolean unfolded, Position position, String category, int x, int y) {
		this.category = category;
		this.name = name;
		this.marked = marked;
		this.unfolded = unfolded;
		this.position = position;
		this.x = x;
		this.y = y;

		if (category.equals("Buss")) {
			this.color = Color.RED;

		} else if (category.equals("Tunnelbana")) {
			this.color = Color.BLUE;

		} else if (category.equals("Tag")) {
			this.color = Color.GREEN;

		} else if (category.equals("None")) {

			this.color = Color.BLACK;
		}

		setBounds(x - 10, y - 20, d1, d2);
		setPreferredSize(new Dimension(50, 100));
		setMaximumSize(new Dimension(50, 100));
		setMinimumSize(new Dimension(50, 100));
		addMouseListener(this);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (getMarked() && !getUnfolded() && visible) {
	
			markTriangle(g);
		}

		if (getUnfolded() && visible) {// fel.
			
			displayInfo(g);
		}

		if (!getUnfolded() && visible) {

			setBounds(x - 10, y - 20, d1, d2);
			g.setColor(color);// denna färg syns bästa mot kartan.
			g.fillPolygon(polygon);
			g.drawPolygon(polygon);
		}


	}

	protected void markTriangle(Graphics g) {
		
		g.drawRect(0, 0, 20, 20);
	}

	public boolean isMarked() {
		return marked;
	}

	public boolean getUnfolded() {

		return unfolded;

	}

	public void setUnfolded(boolean newUnfolded) {

		unfolded = newUnfolded;

	}

	public void setVisible(boolean visible) {
		this.visible = visible;

		this.repaint();

	}

	public boolean isVisible() {
		return visible;
	}

	public abstract String getTypeOfPlace();

	public abstract String getDescription();// Fixa denna så att man slipper ha
											// den som absract.

	abstract protected void displayInfo(Graphics g);

	public void setMarkedTrue() {

		marked = true;
		repaint();

	}

	public void setMarkedFalse() {
		marked = false;
		repaint();

	}

	public boolean getMarked() {

		return marked;
	}

	public Position getPosition() {

		return position;

	}

	public String getCategory() {

		return category;

	}

	public void setCategory(String newCathergory) {

		category = newCathergory;

	}

	public String getName() {

		return name;

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

		// if (SwingUtilities.isLeftMouseButton(e)) {
		//
		// if (!getMarked() && !getUnfolded()) {
		//
		// setMarkedTrue();
		// repaint();
		//
		// } else if (getMarked()) {
		//
		// setMarkedFalse();
		// repaint();
		//
		// }
		// }

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (SwingUtilities.isRightMouseButton(e)) {

			if (getUnfolded()) {

				setUnfolded(false);
				repaint();

			}

			else {

				setUnfolded(true);
				repaint();

			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {

		}
	}

	@Override
	public String toString() {
		return "******* =" + visible + ", " + unfolded + ", " + marked + ", " + name + ", " + category + ", " + position
				+ ", " + d1 + ", " + d2 + ", " + color + ", " + x + ", " + y + ", " + polygon;
	}

}

class DescribedPlace extends Place {

	private String description;

	public DescribedPlace(String name, boolean marked, boolean folded, Position position, String description,
			String category, int x, int y) {
		super(name, marked, folded, position, category, x, y);
		this.position = position;
		this.description = description;

	}

	protected void displayInfo(Graphics g) {

		setBounds(x, y - 30, 100, 28);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 100, 27);
		g.setColor(Color.BLACK);
		g.drawString(getName(), 0, 10);
		g.drawString(getDescription(), 0, 20);

	}

	public String getTypeOfPlace() {
		return "Described";

	}

	public String getDescription() {

		return description;
	}


}

class NamedPlace extends Place {

	public NamedPlace(String name, boolean marker, boolean unfolded, Position position, String category, int x, int y) {
		super(name, marker, unfolded, position, category, x, y);
		this.position = position;

	}

	protected void displayInfo(Graphics g) {
		setBounds(x - 10, y - 20, 70, 13);
		g.setColor(Color.RED);
		g.drawString(getName(), 0, 10);
		

		
	}

	public String getTypeOfPlace() {
		return "Named";

	}

	public String getDescription() {

		return null;
	}

}

