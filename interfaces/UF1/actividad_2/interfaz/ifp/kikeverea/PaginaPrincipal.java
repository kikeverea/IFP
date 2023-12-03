package ifp.kikeverea;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PaginaPrincipal {

	public static void build(JFrame frame) {
		try {
			
			JLabel titulo = new JLabel("Página Principal");
			titulo.setOpaque(false);
			titulo.setFont(Theme.font(32, Font.BOLD));
			titulo.setForeground(Color.WHITE);
			titulo.setBounds(271, 38, 408, 40);
			frame.add(titulo);
			
			BufferedImage myPicture = ImageIO.read(new File("./src/hide_the_pain_harold.jpeg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(45, 109, 700, 400);
			frame.add(picLabel);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
