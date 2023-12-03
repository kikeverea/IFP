package ifp.kikeverea;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputUsuario extends CustomComponent {
	
	private final JPanel panel;
	private final JTextField input;
	
	public InputUsuario(String label) {
		panel = new JPanel();
		panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel inputLabel = new JLabel(label);
        inputLabel.setOpaque(false);
        inputLabel.setFont(Theme.font(10));
        inputLabel.setForeground(Color.WHITE);
        
        input = new RoundTextField();
        input.setFont(Theme.font(10));
        input.setColumns(10);       
		
        panel.add(inputLabel);
		panel.add(Box.createVerticalStrut(5));
		panel.add(input);
	}
	
	public void setText(String text) {
		input.setText(text);
	}
	
	public String getText() {
		return input.getText();
	}
	
	public void addtoFrame(JFrame frame) {
		frame.add(panel);
	}
	
	@Override
	public JComponent component() {
		return panel;
	}
}
