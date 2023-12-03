package ifp.kikeverea;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LoginPage {
	
	
	public static void build(JFrame frame) {
		InputUsuario usuario = new InputUsuario("Usuario");
		usuario.setBounds(264, 135, 272, 54);
		usuario.addToFrame(frame);
		
        InputUsuario contrasena = new InputUsuario("Contraseña");
        contrasena.setBounds(264, 209, 272, 54);
        contrasena.addToFrame(frame);
        
        Button login = new Button("LOGIN");
        login.setBounds(326,  294, 148, 40);
        login.addToFrame(frame);
        login.addActionListener(e -> {
        	if (!usuario.getText().isEmpty() && !contrasena.getText().isEmpty())
        		launchPrincipal(frame);
        });
        
        
        JButton signup = new JButton("SIGNUP");
        signup.setFont(Theme.font(14));
        signup.setForeground(Color.WHITE);
        signup.setBounds(350, 500, 100, 20);
        signup.setBackground(null);
        signup.addActionListener(e -> launchSignup(frame));
        frame.add(signup);
	}
	
	private static void launchSignup(JFrame frame) {
		frame.setVisible(false);
    	JFrame newFrame = Frame.newFrame();
    	SignupInfoCuenta.build(newFrame);
    	newFrame.setVisible(true);
	}
	
	private static void launchPrincipal(JFrame frame) {
		frame.setVisible(false);
    	JFrame newFrame = Frame.newFrame();
    	PaginaPrincipal.build(newFrame);
    	newFrame.setVisible(true);
	}
}
