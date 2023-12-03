package ifp.kikeverea;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

public class Button extends CustomComponent {
	
	private final JButton button;
	
	public Button(String label, int fontSize) {
		button = new RoundedButton(label);
        button.setBackground(Theme.colorPrimary());
        button.setForeground(Color.white);
        button.setFont(Theme.font(fontSize));
	}
	
	public Button(String label) {
		this(label, 12);
	}
	
	public void addActionListener(ActionListener listener) {
		button.addActionListener(listener);
	}
	
	@Override
	public JComponent component() {
		return button;
	}
}
