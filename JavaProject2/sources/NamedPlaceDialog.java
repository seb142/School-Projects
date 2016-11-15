

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NamedPlaceDialog extends JPanel {

	private JTextField nameField = new JTextField(10);
	private JTextField descriptionField = new JTextField(5);

	public NamedPlaceDialog() {
		JPanel rad1 = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		rad1.add(new JLabel("Namn:"));
		rad1.add(nameField);
		add(rad1);
		JPanel rad2 = new JPanel();

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
