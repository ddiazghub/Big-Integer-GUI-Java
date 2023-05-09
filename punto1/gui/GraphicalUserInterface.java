package lab2.punto1.gui;

import java.awt.BorderLayout;

import javax.crypto.spec.PBEKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;

import lab2.punto1.AnimationContext;

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
		super("Laboratorio 2");
		this.context = context;
		this.lock = this.context.lock;

		// La interfaz de usuario tendrá un JPanel y JButton
		this.animation = new Animation(this.context);
		JButton button = new JButton("Siguiente paso");

		// Se añade un listener al botón que tendrá el propósito de despertar al hilo donde se realizan las operaciones con listas enlazadas para hacer que se de un paso en el procedimiento donde se hacen las operaciones.
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.add(animation, BorderLayout.CENTER);
		this.add(button, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void stop() {
		this.animation.stop();
		this.dispose();
	}
}