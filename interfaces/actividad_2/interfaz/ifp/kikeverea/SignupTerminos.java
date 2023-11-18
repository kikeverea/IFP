package ifp.kikeverea;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SignupTerminos {

	private static final String TERMS = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed volutpat risus et\n"
			+ "lacinia venenatis. Suspendisse id pharetra tellus, interdum fermentum dolor.\n"
			+ "Pellentesque nec facilisis risus. Morbi condimentum tristique facilisis. Nullam at\n"
			+ "augue vitae eros pharetra bibendum. Sed nec fermentum libero, quis gravida orci.\n"
			+ "Nulla at lorem mauris. Integer fermentum, mi ut rhoncus euismod, velit nisl eleifend\n"
			+ "odio, eu maximus metus nulla ut dui. Suspendisse potenti. Fusce id ligula sed elit\n"
			+ "porttitor mattis id ac justo. Vestibulum laoreet auctor elit a tincidunt. Fusce neque\n"
			+ "elit, elementum eget turpis eu, consectetur accumsan sapien. Vivamus sagittis\n"
			+ "laoreet ex sed venenatis. Integer volutpat vitae nunc quis porta. Ut cursus mauris\n"
			+ "sed arcu tincidunt consectetur.\n"
			+ "\n"
			+ "Pellentesque eget tincidunt magna. Sed consequat tincidunt augue at consequat.\n"
			+ "Morbi faucibus libero ut nisl hendrerit aliquet. Donec porta nisi in fringilla\n"
			+ "habitasse platea dictumst.\n"
			+ "Mauris accumsan libero lectus, quis ullamcorper ligula suscipit vel. Integer ac orci\n"
			+ "et ligula pharetra euismod eu ut enim. Praesent aliquet risus tortor, ac porttitor\n"
			+ "velit pharetra sit amet.\n"
			+ "Integer arcu augue, ornare sed pulvinar in, volutpat ut libero. Nunc imperdiet\n"
			+ "facilisis...";
	
	
	public static void build(JFrame frame) {
		JLabel titulo = new JLabel("Términos de Uso y Política de Privacidad");
		titulo.setBounds(115, 55, 408, 32);
		titulo.setFont(Theme.font(20, Font.BOLD));
		titulo.setForeground(Color.WHITE);
		frame.add(titulo);
		
		String formatted = TERMS.replace("\n", "<br>");
		formatted = "<html>" + formatted + "</html>";
		
		JLabel terms = new JLabel(formatted);
		terms.setBounds(125, 112, 547, 342);
		terms.setFont(Theme.font(14));
		frame.add(terms);
		
		JPanel termsContainer = new RoundedPanel();
		termsContainer.setBackground(Color.WHITE);
		termsContainer.setBounds(102, 96, 596, 372);
		frame.add(termsContainer);
		
		JCheckBox aceptarTeminos = new JCheckBox();
		aceptarTeminos.setBounds(115, 484, 20, 20);
		frame.add(aceptarTeminos);
		
		JCheckBox aceptarPrivacidad = new JCheckBox();
		aceptarPrivacidad.setBounds(115, 512, 20, 20);
		frame.add(aceptarPrivacidad);
		
		JLabel aceptarTerminosLabel = new JLabel("Acepto términos y condiciones de uso");
		aceptarTerminosLabel.setBounds(145, 488, 278, 13);
		aceptarTerminosLabel.setForeground(Color.WHITE);
		aceptarTerminosLabel.setFont(Theme.font(14));
		frame.add(aceptarTerminosLabel);
		
		JLabel aceptarPrivacidadLabel = new JLabel("Acepto política de privacidad de datos");
		aceptarPrivacidadLabel.setBounds(145, 516, 278, 13);
		aceptarPrivacidadLabel.setForeground(Color.WHITE);
		aceptarPrivacidadLabel.setFont(Theme.font(14));
		frame.add(aceptarPrivacidadLabel);
		
        Button acpetar = new Button("SIGUIENTE");
        acpetar.setBounds(550, 494, 148, 40);
        acpetar.addToFrame(frame);
        acpetar.addActionListener(e -> {
        	if (aceptarTeminos.isSelected() && aceptarPrivacidad.isSelected())
        		launchInfoUsuario(frame);
        });
	}
	
	private static void launchInfoUsuario(JFrame frame) {
		frame.setVisible(false);
    	JFrame newFrame = Frame.newFrame();
    	SignupInfoUsuario.build(newFrame);
    	newFrame.setVisible(true);
	}
	
}
