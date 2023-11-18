package ifp.kikeverea;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class SignupInfoUsuario {
	
	public static void build(JFrame frame) {
		InputUsuario edad = new InputUsuario("Edad");
		edad.setBounds(202, 82, 62, 54);
		edad.addtoFrame(frame);
		
		for (int i = 0, x = 300; i < 8; i++, x += (18 + 13)) {
			int valor = (i + 1) * 10;
			JLabel numero = numero(valor);
			numero.setBounds(x, 135, 13, 12);
			frame.add(numero);
		}
		
		JSlider slider = new JSlider(0, 90);
		slider.addChangeListener(e -> {
			 JSlider source = (JSlider) e.getSource();
			 edad.setText(String.valueOf(source.getValue()));
		});
		slider.setBounds(288, 109, 261, 27);
		slider.setBackground(null);
		frame.add(slider);
		
		JLabel generoLabel = new JLabel("Género");
		generoLabel.setFont(Theme.font(10));
		generoLabel.setForeground(Color.WHITE);
		generoLabel.setBounds(206, 159, 87, 13);
		frame.add(generoLabel);
		
		JComboBox<String> genero = new JComboBox();
		genero.setFont(Theme.font(14));
		genero.setBackground(Color.WHITE);
		genero.addItem("Hombre");
		genero.addItem("Mujer");
		genero.addItem("Prefiero no especificar");
		genero.setBounds(202, 177, 185, 36);
		frame.add(genero);
		
		InputUsuario telefono = new InputUsuario("Teléfono");
		telefono.setBounds(202, 233, 186, 54);
		telefono.addtoFrame(frame);
		
		InputUsuario direccion = new InputUsuario("Teléfono");
		direccion.setBounds(202, 307, 372, 54);
		direccion.addtoFrame(frame);
		
		InputUsuario ciudad = new InputUsuario("Ciudad");
		ciudad.setBounds(202, 381, 186, 54);
		ciudad.addtoFrame(frame);
		
		InputUsuario cp = new InputUsuario("Código Postal");
		cp.setBounds(412, 381, 162, 54);
		cp.addtoFrame(frame);
		
		Button siguiente = new Button("SIGUIENTE");
		siguiente.setBounds(326, 484, 148, 40);
		siguiente.addToFrame(frame);
		siguiente.addActionListener(e -> {
			if (!edad.getText().isEmpty() &&
				!telefono.getText().isEmpty() &&
				!direccion.getText().isEmpty() &&
				!ciudad.getText().isEmpty() &&
				!cp.getText().isEmpty())
				launchPlanPago(frame);
			
		});
	}
	
	private static JLabel numero(int numero) {
		JLabel label = new JLabel(String.valueOf(numero));
		label.setFont(Theme.font(10));
		label.setForeground(Color.WHITE);
		return label;
	}
	
	private static void launchPlanPago(JFrame frame) {
		frame.setVisible(false);
    	JFrame newFrame = Frame.newFrame();
    	new SignupPlanPago().build(newFrame);
    	newFrame.setVisible(true);
	}
}
