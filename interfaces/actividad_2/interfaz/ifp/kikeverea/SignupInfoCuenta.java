package ifp.kikeverea;

import javax.swing.JFrame;

public class SignupInfoCuenta {
	public static void build(JFrame frame) {
		InputUsuario nombre = new InputUsuario("Nombre");
		nombre.setBounds(202, 82, 186, 54);
		nombre.addToFrame(frame);
		
		InputUsuario apellido = new InputUsuario("Apellido");
		apellido.setBounds(412, 82, 186, 54);
		apellido.addToFrame(frame);
		
		InputUsuario usuario = new InputUsuario("Usuario");
        usuario.setBounds(202, 156, 186, 54);
        usuario.addToFrame(frame);
		
        InputUsuario contrasena = new InputUsuario("Contraseña");
        contrasena.setBounds(202, 230, 186, 54);
        contrasena.addToFrame(frame);
        
        InputUsuario repetirContrasena = new InputUsuario("Repetir Contraseña");
        repetirContrasena.setBounds(412, 230, 186, 54);
        repetirContrasena.addToFrame(frame);
        
        Button login = new Button("SIGN UP");
        login.setBounds(326, 317, 148, 40);
        login.addToFrame(frame);
        login.addActionListener(e -> {
        	if (!nombre.getText().isEmpty() &&
				!apellido.getText().isEmpty() &&
				!usuario.getText().isEmpty() &&
				!contrasena.getText().isEmpty() &&
    			contrasena.getText().equals(repetirContrasena.getText()))
        		launchTerminos(frame);
        });
	}
	
	private static void launchTerminos(JFrame frame) {
		frame.setVisible(false);
    	JFrame newFrame = Frame.newFrame();
    	SignupTerminos.build(newFrame);
    	newFrame.setVisible(true);
	}
}
