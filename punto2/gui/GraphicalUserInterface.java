package lab2.punto2.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import lab2.punto2.AnimationContext;
import lab2.punto2.lists.ListaCircular;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
* Interfaz de usuario para mostrar de manera gráfica el procedimiento de las operaciones matemáticas con listas enlazadas.
*/
public class GraphicalUserInterface extends JFrame {
	private Animation animation;
	private AnimationContext context;
	public Object lock;

	public GraphicalUserInterface(AnimationContext context) {
		super("Laboratorio 2 Punto 2");
		this.context = context;

		// La interfaz de usuario tendrá un JPanel y JButton
		this.animation = new Animation(this.context);

		JTextArea text = new JTextArea(2, 5);
		text.setText("Consulta si un número natural es automórfico.\nPrefijos: 0b binario, 0o octal, 0x o 0h hexadecimal.");
		text.setVisible(true);

		JPanel panel = new JPanel();
		JTextField textField = new JTextField(25);
		JButton button = new JButton("Consultar");
		button.setVisible(true);
		textField.setVisible(true);
		panel.setLayout(new BorderLayout());
		panel.add(text, BorderLayout.NORTH);
		panel.add(textField, BorderLayout.WEST);
		panel.add(button, BorderLayout.EAST);

		// Se añade un listener al botón que tendrá el propósito de despertar al hilo donde se realizan las operaciones con listas enlazadas para hacer que se de un paso en el procedimiento donde se hacen las operaciones.
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String number = textField.getText();

				if (number.charAt(0) == '0' && number.length() > 2) {
					String start = number.substring(0, 2);
					String value = number.substring(2, number.length());

					if (!start.equals("0b") && !start.equals("0o") && !start.equals("0x") && !start.equals("0h")) {
						JOptionPane.showMessageDialog(null, "Prefijo inválido", "Error", JOptionPane.ERROR_MESSAGE);
						
						return;
					}

					try {
						long longValue = Long.parseLong(value, ListaCircular.getBaseFromPrefix(start));

						if (longValue < 0) {
							JOptionPane.showMessageDialog(null, "Introduzca un número no negativo", "Error", JOptionPane.ERROR_MESSAGE);

							return;
						}

						number = start + Long.toString(longValue, ListaCircular.getBaseFromPrefix(start));
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Introduzca un valor numérico", "Error", JOptionPane.ERROR_MESSAGE);
						
						return;
					}
				} else {
					try {
						long longValue = Long.parseLong(number);

						if (longValue < 0) {
							JOptionPane.showMessageDialog(null, "Introduzca un número no negativo", "Error", JOptionPane.ERROR_MESSAGE);

							return;
						}

						number = Long.toString(longValue);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Introduzca un valor numérico", "Error", JOptionPane.ERROR_MESSAGE);
						
						return;
					}
				}

				if (ListaCircular.automorfico(new ListaCircular(number), context)) {
					JOptionPane.showMessageDialog(null, "Automórfico", "Resultado", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "No automórfico", "Resultado", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		this.add(animation, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void stop() {
		this.animation.stop();
		this.dispose();
	}
}