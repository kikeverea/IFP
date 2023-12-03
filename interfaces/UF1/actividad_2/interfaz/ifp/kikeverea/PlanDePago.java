package ifp.kikeverea;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlanDePago extends CustomComponent {
	
	
	private static final String PLAN = "Rope's end gun dance the hempen\n"
			+ "\n"
			+ "Rigging strike colors \n"
			+ "\n"
			+ "Pillage scurvy long clothes bilge rat \n"
			+ "\n"
			+ "Yellow Jack. Cat o'nine tails skysail \n"
			+ "\n"
			+ "Gabion hail-shot \n"
			+ "\n"
			+ "Bring a spring upon her\n"
			+ "\n"
			+ "Cable chase guns take a caulk";
	
	private final JPanel panel;
	private JLabel precio;
	
	public PlanDePago(JFrame frame, String nombre, double valorPrecio, int x, int y) {
		panel = new RoundedPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(x, y, 186, 372);
		
		JLabel titulo = new JLabel(nombre);
		titulo.setFont(Theme.font(20, Font.BOLD));
		panel.add(Box.createVerticalStrut(19));
		panel.add(titulo);
		
		precio = new JLabel(formatPrecio(valorPrecio));
		precio.setFont(Theme.font(16));
		precio.setForeground(new Color(17, 106, 255));
		panel.add(Box.createVerticalStrut(51));
		panel.add(precio);
		
		String formatted = PLAN.replace("\n", "<br>");
		formatted = "<html>" + formatted + "</html>";
		
		JLabel plan = new JLabel(formatted);
		plan.setFont(Theme.font(8));
		plan.setForeground(new Color(117, 117, 117));
		panel.add(plan);
		
		Button comprar = new Button("COMPRAR", 10);
		comprar.addActionListener(e -> launchPrincipal(frame));
		
		panel.add(Box.createVerticalStrut(200));
		comprar.addtoPanel(panel);
	}
	
	public void cambiarPrecio(double precio) {
		this.precio.setText(formatPrecio(precio));
	}
	
	private String formatPrecio(double precio) {
		precio = Math.round(precio * 100.0) / 100.0;
		return String.valueOf(precio)+"€ / mes";
	}
	
	private static void launchPrincipal(JFrame frame) {
		frame.setVisible(false);
    	JFrame newFrame = Frame.newFrame();
    	PaginaPrincipal.build(newFrame);
    	newFrame.setVisible(true);
	}
	
	@Override
	public JComponent component() {
		return panel;
	}
}
