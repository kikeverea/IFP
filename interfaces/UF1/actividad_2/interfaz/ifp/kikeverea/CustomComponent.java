package ifp.kikeverea;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class CustomComponent {

	private int w;
	private int h;
	
	public void addToFrame(JFrame frame) {
		frame.add(component());
	}
	
	public void removeFromFrame(JFrame frame) {
		frame.remove(component());
	}
	
	public void addtoPanel(JPanel panel) {
		panel.add(component());
	}
	
	public abstract JComponent component();
	
	public void setBounds(int x, int y, int w, int h) {
		component().setBounds(x, y, w, h);
	}
	
	public void setSize(int w, int h) {
		JComponent component = component();
		component.setPreferredSize(new Dimension(w, h));
		
		this.w = w;
		this.h = h;
	}
	
	public int width() {
		return w;
	}
	
	public int height() {
		return h;
	}
}
