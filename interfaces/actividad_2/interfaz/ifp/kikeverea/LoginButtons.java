package ifp.kikeverea;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LoginButtons {
	
	public static void build(JFrame frame, int yPos) {
		try {
			
			BufferedImage myPicture = ImageIO.read(new File("./src/twitter_icon.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setOpaque(false);
			
			picLabel.setBounds(0, 0, 50, 50);
			frame.add(picLabel);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
