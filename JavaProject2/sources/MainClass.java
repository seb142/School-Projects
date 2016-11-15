

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.WindowListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainClass extends JFrame {
	private boolean pPickNP = false;
	private boolean pPickDP = false;
	private String categoryPick = "None";
	private boolean whatIsHereClicked = false;

	private String[] sakKategori = { "Namngiven plats", "Beskriven plats" };
	private JComboBox<String> placeCategory = new JComboBox<String>(sakKategori);
	private String[] listAlternatives = { "Buss", "Tunnelbana", "Tåg" };
	private JPanel north = new JPanel();
	private JPanel east = new JPanel();
	private JPanel west = new JPanel();
	private JPanel south = new JPanel();
	private JPanel center = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("Arkiv");
	private JMenuItem newMap, loadPlaces, save, exit;
	private JTextField searchField = new JTextField(7);
	private JList list = new JList(listAlternatives);
	private JButton search = new JButton("Search");
	private JButton hide = new JButton("Hide");
	private JButton remove = new JButton("Remove");
	private JButton whatIsHere = new JButton("What is here?");
	private JButton hideCategory = new JButton("Hide cathegory");
	private JLabel categories = new JLabel("Categories");
	private JPanel mapPanel = new JPanel();
	private Box box = Box.createVerticalBox();
	private JScrollPane scroll = new JScrollPane();
	private int x;
	private int y;
	private MarkerListener marked = new MarkerListener();
	private JLabel imgLabel = new JLabel();
	private DataStorage dataStorage = new DataStorage();
	private int lastSelectedIndex;
	private FileHandler filehandler = new FileHandler(dataStorage);
	private boolean choiceMadeMap = false;
	private boolean choiceMadeSave = false;

	public MainClass() {
		super("Inlupp2");
		setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		add(west, BorderLayout.WEST);
		add(east, BorderLayout.EAST);
		add(south, BorderLayout.SOUTH);
		add(center, BorderLayout.CENTER);
		add(north, BorderLayout.NORTH);

		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		newMap = new JMenuItem("New Map");
		loadPlaces = new JMenuItem("Load Places");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");
		menu.add(newMap);
		menu.add(loadPlaces);
		menu.add(save);
		menu.add(exit);

		this.addWindowListener(new Windowd());

		north.add(placeCategory);
		placeCategory.addActionListener(new ComboListener());
		north.add(searchField);
		search.addActionListener(new ButtonListener());
		north.add(search);
		hide.addActionListener(new ButtonListener());
		north.add(hide);
		remove.addActionListener(new ButtonListener());
		north.add(remove);
		whatIsHere.addActionListener(new ButtonListener());
		north.add(whatIsHere);
		center.add(mapPanel);
		mapPanel.setLayout(null);
		mapPanel.addMouseListener(new MouseListenerImage());

		JScrollPane scroll = new JScrollPane(mapPanel);
		scroll.setViewportBorder(null);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		box.add(categories);
		hideCategory.addActionListener(new ButtonListener());
		box.add(hideCategory);
		Dimension d = list.getPreferredSize();
		d.width = 100;

		list.setPreferredSize(d);
		list.addListSelectionListener(new ListListener());
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		box.add(list);

		east.add(box);

		newMap.addActionListener(new MenuListener());
		save.addActionListener(new MenuListener());
		loadPlaces.addActionListener(new MenuListener());
		exit.addActionListener(new MenuListener());
		add(scroll, BorderLayout.CENTER);
		setVisible(true);
		pack();

	}

	
	public void clearAllListsAndPanel(){
		
		imgLabel.removeAll();
		dataStorage.getStorage().clear();
		dataStorage.getpositionList().clear();
		dataStorage.getPositionStorage().clear();
		dataStorage.getMarkedPlaces().clear();
		dataStorage.clearCategoryList();
		
	}
	
	public void changeToCrosshairCursor() {
		mapPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

	}

	public void changeToDefaultCursor() {
		mapPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

	}

	public void rePaint() {

		repaint();

	}

	class MenuListener implements ActionListener {

		public void actionPerformed(ActionEvent ave) {

			JFileChooser fc = new JFileChooser();

			if (ave.getSource() == newMap) {

				if (choiceMadeMap && !dataStorage.getStorage().isEmpty()) {

					Object[] options = { "JA", "NEJ" };

					int choice = JOptionPane.showOptionDialog(null,
							"Vill du ladda upp en ny karta utan att spara ändrigarna?", "Varning",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

					if (choice == 0) {

						mapPanel.removeAll();
						dataStorage.getStorage().clear();
						dataStorage.getpositionList().clear();
						dataStorage.getPositionStorage().clear();
						dataStorage.getMarkedPlaces().clear();
						dataStorage.clearCategoryList();

						repaint();
						validate();

					} else {
						return;

					}

				}

				int answer = fc.showOpenDialog(MainClass.this);

				if (answer == JFileChooser.APPROVE_OPTION) {

					try {

						File map = fc.getSelectedFile();
						Image img = ImageIO.read(map);

						String mapPath = map.getAbsolutePath();

						ImageIcon displayMap = new ImageIcon(img);
						mapPanel.removeAll();
						repaint();
						imgLabel = new JLabel(new ImageIcon(mapPath));

						imgLabel.setLayout(null);
						imgLabel.setSize(displayMap.getIconWidth(), displayMap.getIconHeight());

						mapPanel.setPreferredSize(new Dimension(displayMap.getIconWidth(), displayMap.getIconHeight()));

						mapPanel.add(imgLabel);
						repaint();
						validate();
						choiceMadeMap = true;

					} catch (IOException ex1) {
						JOptionPane.showMessageDialog(null, "Fel:" + ex1.getMessage());

					} catch (NullPointerException ex1) {

						JOptionPane.showMessageDialog(null, "Fel typ av fil ");
					}

				} else {

					return;
				}
			}
			if (ave.getSource() == loadPlaces) {
				try {

					if (!dataStorage.getStorage().isEmpty() && !choiceMadeSave) {

						Object[] options = { "JA", "NEJ" };
						int choice = JOptionPane.showOptionDialog(null,
								"Vill du ladda upp nya platser utan att spara de gamla?", "Varning",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

						if (choice == 0) {
							
							clearAllListsAndPanel();
							repaint();
						} else {
							return;
						}

					}
					int answer = fc.showOpenDialog(MainClass.this);
					if (answer == JFileChooser.APPROVE_OPTION) {
						fc.setDialogTitle("Välj fil att lada data från");
						
						File file = fc.getSelectedFile();

						filehandler.fileReader(file);
						clearAllListsAndPanel();

						for (Place place : filehandler.getPlaceleList()) {
			//Lös detta!! täm alla listor fråg platser för att bereda plats för de som ska laddas in																
																	
							imgLabel.add(place);
							place.addMouseListener(marked);

							dataStorage.putInStorage(place.getName(), place);
							dataStorage.getcategoryList().get(place.getCategory()).add(place);

							dataStorage.getpositionList().add(place.getPosition());

							dataStorage.getPositionStorage().put(place.getPosition(), place);

						}
						repaint();
						filehandler.emptyPlaceleList();

					}
				} catch (NullPointerException ex1) {

					JOptionPane.showMessageDialog(null, "Fel typ av fil ");
				}

			}
			if (ave.getSource() == save) {

				fc.setDialogTitle("Välj fil att spara data i");
				int answer = fc.showOpenDialog(MainClass.this);

				if (answer == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();

					filehandler.fileWriter(file);
					choiceMadeSave = true;
				} else {

					return;

				}
			}
			if (ave.getSource() == exit) {

				if (dataStorage.getStorage().isEmpty()) {
					System.exit(0);

				} else {

					Object[] options = { "AVSLUTA", "CANCEL" };
					int choice = JOptionPane.showOptionDialog(null, "Vill du avsluta utan att spara ändringar?",
							"Varning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);

					if (choice == 0) {

						System.exit(0);

					}
				}
			}

		}

	}

	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == hide) {

				for (Place place : dataStorage.getMarkedPlaces()) {

					imgLabel.remove(place);

				}
				dataStorage.hideMarked();

			}
			repaint();

			if (e.getSource() == search) {

				String searchWord = searchField.getText();

				for (Place place : dataStorage.getMarkedPlaces()) {

					place.setMarkedFalse();

				}

				dataStorage.getSearchedPos(searchWord);

				for (Place place2 : dataStorage.getMarkedPlaces()) {

					imgLabel.add(place2);

				}
				repaint();

			}
			if (e.getSource() == remove) {

				for (Place p : dataStorage.getMarkedPlaces()) {
					imgLabel.remove(p);

				}

				dataStorage.removePlace();
				choiceMadeSave = false;

				repaint();

			}
			if (e.getSource() == hideCategory) {

				if (!categoryPick.equals("None")) {
					dataStorage.hideCategory(categoryPick);

					for (Place place : dataStorage.getcategoryList().get(categoryPick)) {

						imgLabel.remove(place);

					}

				}
				repaint();

			}

			if (e.getSource() == whatIsHere) {

				whatIsHereClicked = true;

			}

		}

	}

	class ListListener implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {

			boolean adjust = e.getValueIsAdjusting();

			if (!adjust) {
				JList list = (JList) e.getSource();
				int selections[] = list.getSelectedIndices();
				int i = 5;

				if (selections.length > 0) {
					i = selections[0];
				}

				if (i == 0) {

					categoryPick = "Buss";
					if (!pPickDP) {

						imgLabelAdd(dataStorage.setCategoryVisible(categoryPick));

						repaint();
						validate();
					}

					lastSelectedIndex = i;

				} else if (i == 1) {

					categoryPick = "Tunnelbana";
					if (!pPickDP) {

						imgLabelAdd(dataStorage.setCategoryVisible(categoryPick));
						repaint();
						validate();
					}

				} else if (i == 2) {

					categoryPick = "Tag";
					if (!pPickDP) {

						imgLabelAdd(dataStorage.setCategoryVisible(categoryPick));
						repaint();
						validate();
					}

				} else {

					categoryPick = "None";
				}

			}
		}

		public void imgLabelAdd(ArrayList<Place> placeList) {

			for (Place p : placeList) {
				p.setVisible(true);
				imgLabel.add(p);

			}
			repaint();
		}
	};

	class ComboListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			String alternative = (String) placeCategory.getSelectedItem();

			if (alternative.equals("Namngiven plats")) {
				pPickDP = false;
				pPickNP = true;

			}
			if (alternative.equals("Beskriven plats")) {
				pPickNP = false;
				pPickDP = true;

			}

		}

	}

	class Windowd implements WindowListener {

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent e) {

		}

		@Override
		public void windowClosing(WindowEvent e) {
			if (dataStorage.getStorage().isEmpty()) {
				System.exit(0);

			} else {

				Object[] options = { "AVSLUTA", "CANCEL" };
				int choice = JOptionPane.showOptionDialog(null, "Vill du avsluta utan att spara ändringar?", "Varning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				if (choice == 0) {

					System.exit(0);

				}
			}

		}

		@Override
		public void windowDeactivated(WindowEvent e) {

		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class MarkerListener extends MouseAdapter {
		public void mouseClicked(MouseEvent ev) {

			Place p = (Place) ev.getSource();
			if (ev.getButton() == MouseEvent.BUTTON1) {

				if (p.getMarked()) {

					p.setMarkedFalse();
					boolean found = false;
					for (Place pl : dataStorage.getMarkedPlaces()) {
						if (pl.getPosition().getX() == p.getPosition().getX()
								&& pl.getPosition().getY() == p.getPosition().getY()) {
							found = true;

						}
					}
					if (found == true) {

						dataStorage.removeFromMarkedPlaces(p);

					}
				} else {

					p.setMarkedTrue();
					dataStorage.addToMarkedPlaces(p);

				}
			}
			repaint();
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
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	class MouseListenerImage implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			int x = e.getX();
			int y = e.getY();

			if (whatIsHereClicked) {

				Place place = dataStorage.whatIsHere(x, y);
				if (place != null) {

					place.setVisible(true);
					imgLabel.add(place);
					repaint();

				}

			}
			whatIsHereClicked = false;

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (pPickNP || pPickDP) {
				changeToCrosshairCursor();
				validate();
			} else {
				changeToDefaultCursor();
				validate();
			}

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {

			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {

			int x = e.getX();
			int y = e.getY();

			if (SwingUtilities.isLeftMouseButton(e)) {

				if (pPickNP || pPickDP) {

					if (pPickDP) {

						DescribedPlaceDialog d = new DescribedPlaceDialog();
						int svar = JOptionPane.showConfirmDialog(MainClass.this, d, "Indata",
								JOptionPane.OK_CANCEL_OPTION);

						if (svar == JOptionPane.OK_OPTION) {

							Position position = new Position(x, y);

							DescribedPlace dp = new DescribedPlace(d.getName(), false, false, position,
									d.getDescription(), categoryPick, x, y);
							dp.addMouseListener(marked);

							imgLabel.add(dp);

							dataStorage.putInStorage(dp.getName(), dp);

							dataStorage.getcategoryList().get(categoryPick).add(dp);

							dataStorage.getpositionList().add(position);

							dataStorage.getPositionStorage().put(position, dp);

							pPickDP = false;
							choiceMadeSave = false;

						} else {
							pPickDP = false;
							changeToDefaultCursor();
							return;
						}

					} else if (pPickNP) {

						NamedPlaceDialog n = new NamedPlaceDialog();
						int svar = JOptionPane.showConfirmDialog(MainClass.this, n, "Indata",
								JOptionPane.OK_CANCEL_OPTION);

						if (svar == JOptionPane.OK_OPTION) {

							Position position = new Position(x, y);

							NamedPlace np = new NamedPlace(n.getName(), false, false, position, categoryPick, x, y);

							np.addMouseListener(marked);

							imgLabel.add(np);

							dataStorage.putInStorage(np.getName(), np);
							dataStorage.getcategoryList().get(categoryPick).add(np);

							dataStorage.getpositionList().add(position);

							dataStorage.getPositionStorage().put(position, np);

							pPickNP = false;
							choiceMadeSave = false;
						} else {
							pPickNP = false;
							changeToDefaultCursor();
							return;
						}
						pPickDP = false;
						pPickNP = false;
					}
					changeToDefaultCursor();
					repaint();

				}

			}

		}

	}

	public static void main(String[] args) {

		new MainClass();

	}
}