package ifp.kikeverea;

import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class SignupPlanPago {

	private static double PRO = 5.50;
	private static double BUSINESS = 15.99;
	
	
	private double precioPro = PRO;
	private double precioBusiness= BUSINESS;
	
	public void build(JFrame frame) {
		PlanDePago free = new PlanDePago(frame, "Plan Free", 0.0d, 78, 144);
		free.addToFrame(frame);
		
		PlanDePago pro = new PlanDePago(frame, "   Plan Pro   ", precioPro, 307, 118);
		pro.addToFrame(frame);
		
		PlanDePago business = new PlanDePago(frame, "Plan Business", precioBusiness, 536, 144);
		business.addToFrame(frame);
		
		JRadioButton mensual = new JRadioButton();
		mensual.setBackground(null);
		mensual.setBounds(318, 62, 20, 20);
		mensual.setSelected(true);
		mensual.addChangeListener(e -> {
			JRadioButton button = (JRadioButton) e.getSource();
			if (button.isSelected()) {
				pro.cambiarPrecio(PRO);
				business.cambiarPrecio(BUSINESS);
			}
		});
		frame.add(mensual);
		
		JLabel labelMensual = new JLabel("Mensual");
		labelMensual.setFont(Theme.font(12));
		labelMensual.setForeground(Color.WHITE);
		labelMensual.setBounds(350, 62, 80, 20);
		frame.add(labelMensual);
		
		JRadioButton anual = new JRadioButton();
		anual.setBackground(null);
		anual.setBounds(415, 62, 20, 20);
		anual.addChangeListener(e -> {
			JRadioButton button = (JRadioButton) e.getSource();
			if (button.isSelected()) {
				pro.cambiarPrecio(precioAnual(PRO));
				business.cambiarPrecio(precioAnual(BUSINESS));
			}
		});
		frame.add(anual);
		
		JLabel labelAnual= new JLabel("Anual");
		labelAnual.setFont(Theme.font(12));
		labelAnual.setForeground(Color.WHITE);
		labelAnual.setBounds(448, 62, 80, 20);
		frame.add(labelAnual);
		
		ButtonGroup group = new ButtonGroup();
		group.add(anual);
		group.add(mensual);
		
	}
	
	private double precioAnual(double precioBase) {
		double anual = precioBase * 12;
		double oferta = anual * 0.8d;
		
		return oferta / 12;
	}
}
