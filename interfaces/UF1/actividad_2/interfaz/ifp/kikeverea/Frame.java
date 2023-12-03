package ifp.kikeverea;

import java.awt.Color;

import javax.swing.JFrame;

public class Frame {

	public static JFrame newFrame() {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(73,28,100));
		
		return frame;
	}
}
