
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.xml.crypto.Data;

public class FileHandler {
	private ArrayList<Place> placeList = new ArrayList<>();
	private DataStorage dataStorage = new DataStorage();

	public FileHandler(DataStorage d) {

		this.dataStorage = d;
	}

	public ArrayList<Place> getPlaceleList() {

		return placeList;
	}

	public void emptyPlaceleList() {

		placeList.clear();

	}

	public void fileWriter(File filename) {

		try {
			FileWriter outFile = new FileWriter(filename);
			FileOutputStream fos = new FileOutputStream(filename);
			OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);

			PrintWriter out = new PrintWriter(osw);

			for (Position position : dataStorage.getpositionList()) {
				String storeThisText = "";

				Place place = dataStorage.getPositionStorage().get(position);

				storeThisText += place.getTypeOfPlace() + ",";

				if (place.getCategory().equals("Tag")) {
					storeThisText += "Tåg" + ",";
				} else {
					storeThisText += place.getCategory() + ",";
				}

				storeThisText += (place.getPosition().getX()) + ",";
				storeThisText += (place.getPosition().getY()) + ",";
				storeThisText += place.getName();
				if (place instanceof DescribedPlace) {
					storeThisText += "," + place.getDescription();
				}
				out.println(storeThisText);

			}

			out.close();
			outFile.close();

		} catch (FileNotFoundException e) {

			JOptionPane.showMessageDialog(null, "Fel:" + e.getMessage());

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fel:" + e.getMessage());
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Fel:" + e.getMessage());

		}

	}

	public void fileReader(File file) {

		ArrayList<String> list = new ArrayList<>();

		try {

			FileReader inFile = new FileReader(file);
			FileInputStream reader = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(reader, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr);

			String line;

			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(",");
				list.addAll(Arrays.asList(tokens));

			}
			inFile.close();
			br.close();

		} catch (FileNotFoundException e) {

			JOptionPane.showMessageDialog(null, "Kan inte öppna folk filen");

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fel :" + e.getMessage());
		}
		fileReaderConvert(list);

	}

	public void fileReaderConvert(ArrayList<String> list) {

		Position position;
		String name = "";
		String description = "";
		String categorypick = "";
		int x, y;
		NamedPlace namedPlace = null;
		DescribedPlace describedPlace = null;

		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).contains("Named")) {

				if (list.get(i + 1).equals("Tåg")) {
					categorypick = "Tag";

				} else {
					categorypick = list.get(i + 1);

				}

				x = Integer.parseInt(list.get(i + 2));
				y = Integer.parseInt(list.get(i + 3));

				name = list.get(i + 4);

				position = new Position(x, y);

				namedPlace = new NamedPlace(name, false, false, position, categorypick, x, y);

				i += 4;
				placeList.add(namedPlace);

			} else if (list.get(i).contains("Described")) {

				if (list.get(i + 1).equals("Tåg")) {
					categorypick = "Tag";

				} else {
					categorypick = list.get(i + 1);

				}

				x = Integer.parseInt(list.get(i + 2));
				y = Integer.parseInt(list.get(i + 3));

				name = list.get(i + 4);

				description = list.get(i + 5);

				position = new Position(x, y);

				describedPlace = new DescribedPlace(name, false, false, position, description, categorypick, x, y);

				i += 5;

				placeList.add(describedPlace);

			}

		}

	}
}