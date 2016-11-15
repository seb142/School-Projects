
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JOptionPane;

public class DataStorage {

	private HashMap<String, Set<Place>> categoryList = new HashMap<>();
	private ArrayList<Place> markedPlaces = new ArrayList<>();
	private HashMap<String, ArrayList<Place>> nameStorage = new HashMap<>();
	private HashMap<Position, Place> positionStorage = new HashMap<>();
	private ArrayList<Position> positionList = new ArrayList<>();
	private String[] categories = {"None", "Tag", "Tunnelbana", "Buss"}; 
	DataStorage() {

		for (String catName : categories) {
			
			categoryList.put(catName, new HashSet<>());
		}

	}

	public void putInStorage(String s, Place p) {

		if (nameStorage.containsKey(s)) {

			nameStorage.get(s).add(p);

		} else {

			ArrayList<Place> placeArrayList = new ArrayList<>();
			placeArrayList.add(p);
			nameStorage.put(s,placeArrayList);

		}

	}
	
	public void clearCategoryList(){
		categoryList.clear();
		
		for(String catName : categories){
		categoryList.put(catName, new HashSet<>());
		
		}
		
	}

	public HashMap<String, Set<Place>> getcategoryList() {

		return categoryList;

	}

	public ArrayList<Place> getMarkedPlaces() {

		return markedPlaces;

	}

	public void removeFromMarkedPlaces(Place p) {

		markedPlaces.remove(p);

	}

	public void addToMarkedPlaces(Place p) {

		markedPlaces.add(p);

	}

	public HashMap<String, ArrayList<Place>> getStorage() {

		return nameStorage;

	}

	public HashMap<Position, Place> getPositionStorage() {

		return positionStorage;

	}

	public ArrayList<Position> getpositionList() {

		return positionList;

	}

	public void hideCategory(String category) {

		if (categoryList.containsKey(category)) {
			for (Place place : categoryList.get(category)) {

				place.setVisible(false);
				place.setMarkedFalse();
				markedPlaces.remove(place);

			}

		}

	}

	public void hideMarked() {

		for (Place place : markedPlaces) {

			place.setVisible(false);
			place.setMarkedFalse();

		}
		markedPlaces.clear();

	}

	public void getSearchedPos(String searchWord) {
		try {
			markedPlaces.clear();

			for (Place place : nameStorage.get(searchWord)) {

				place.setMarkedTrue();
				place.setVisible(true);
				markedPlaces.add(place);

			}

		} catch (NullPointerException ex1) {

			JOptionPane.showMessageDialog(null, "Sökt namn finns ej som sparad plats ");
		}

	}

	public ArrayList<Place> setCategoryVisible(String categoryMarked) {

		ArrayList<Place> pList = new ArrayList<>();

		if (categoryList.containsKey(categoryMarked)) {

			for (Place place : categoryList.get(categoryMarked)) {

				place.setVisible(true);
				pList.add(place);
			}

		}
		return pList;

	}

	public Place whatIsHere(int x, int y) {
		Place place = null;
		return checkProximity(x, y, positionList, place);

	}

	public Place checkProximity(int x, int y, ArrayList<Position> positionList, Place place) {
		
		for (int i = (y - 10); i < (y + 10); i++) {

			for (int j = (x - 10); j < (x + 10); j++) {

				Position position = new Position(j, i);

				for (Position p : positionList) {
				
					if (p.getX() == j && p.getY() == i) {

						
						place = positionStorage.get(p);
						place.setVisible(true);
						return place;
					}
				}

			}

		}
		return place;
	}

	public void removePlace() {
		
		for (Place place : markedPlaces) {

			categoryList.get(place.getCategory()).remove(place);

			nameStorage.get(place.getName()).remove(place);

			positionStorage.remove(place.position);
			
			positionList.remove(place.position);
			
		
			if(nameStorage.get(place.getName()).isEmpty() ){
				
				nameStorage.remove(place.getName());
				
			}
			
			
		}
		markedPlaces.clear();
		
		System.out.println(categoryList);
		System.out.println(nameStorage);
		System.out.println(positionStorage);
		System.out.println(positionList);
		System.out.println(markedPlaces);

	}

}
