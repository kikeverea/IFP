package ifp.kikeverea;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

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

			System.out.println(PaginaPrincipal.class.getProtectionDomain().getCodeSource().getLocation());

			BufferedImage myPicture = ImageIO.read(Path.of("hide_the_pain_harold.jpeg").toFile());
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(45, 109, 700, 400);
			frame.add(picLabel);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
