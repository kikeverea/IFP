package ifp.kikeverea;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Theme {
	
	public static Font font(int size, int weight) {
		return new Font("Inter", weight, size);
	}
	
	public static Font font(int size) {
		return font(size, Font.PLAIN);
	}
	
	public static Color colorPrimary() {
		return new Color(15, 83, 215);
	}
	
	
	static {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./src/static/Inter-Regular.ttf")));
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./src/static/Inter-Bold.ttf")));
		} 
		catch (IOException|FontFormatException e) {
			e.printStackTrace();
		}
	}

}

