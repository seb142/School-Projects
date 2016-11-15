

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DescribedPlaceDialog extends JPanel {

	private JTextField nameField = new JTextField(10);
	private JTextField descriptionField = new JTextField(5);

	public DescribedPlaceDialog() {
		JPanel rad1 = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		rad1.add(new JLabel("Namn:"));
		rad1.add(nameField);
		add(rad1);
		JPanel rad2 = new JPanel();
		rad2.add(new JLabel("Beskrivning: "));
		rad2.add(descriptionField);
		add(rad2);

	}

	public String getName() {

		return nameField.getText();

	}

	public String getDescription() {

		String s = descriptionField.getText();

		s = s.substring(0, Math.min(s.length(), 20));

		return s;

	}

}
