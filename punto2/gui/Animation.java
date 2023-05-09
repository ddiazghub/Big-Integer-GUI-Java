package lab2.punto2.gui;

import javax.swing.JPanel;

import lab2.punto2.AnimationContext;
import lab2.punto2.lists.Nodo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;

/**
 *
 * @author david
 */
/**
 * Clase que representa el panel donde se debujará la animación de los procedimientos de operaciones con listas enlazadas.
 */
public class Animation extends JPanel implements Runnable {
	// Dimensiones
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 250;
	
	// Hilo principal, tasa de actualización y tiempo de actualización
	private Thread mainThread;
	private boolean isRunning = false;
	private final int FPS = 60;
	private final long delta = 1000 / this.FPS;

	// Estado actual del programa.
	private AnimationContext context;
			
	public Animation (AnimationContext context) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		this.context = context;
		this.start();
	}
	
	// Inicia la animación
	private void start() {
		this.isRunning = true;
		this.mainThread = new Thread(this);
		this.mainThread.start();
	}
	
	
	@Override
	public void run() {
		// El proceso principal del hilo, solamente se encarga de actualizar los gráficos cada delta de tiempo de manera que se actualizen 60 veces por segundos como está definido en la propiedad FPS
		long lastRender, elapsed, wait;

		while (this.isRunning) {
			lastRender = System.nanoTime();
			
			elapsed = System.nanoTime() - lastRender;
			wait = delta - elapsed / 1000000;
			
			// Método con el cual se actualizan los gráficos, hace un llamado a paintComponent.
			repaint();
			
			if (wait < 0) {
				wait = 5;
			}
			
			try {
				Thread.sleep(wait);
			}	catch(Exception e) {
				e.printStackTrace();
			}
		}

		return;
	}
	
	public void stop() {
		this.isRunning = false;
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Este método se encarga de dibujar la animación en el JPanel.
		
		// Se borran los gráficos que permanencen de la anterior actualización.
		g.clearRect(0, 0, WIDTH, HEIGHT);

		if (this.context.lists == null) {
			return;
		}

		Font defaultFont = g.getFont();

		// Se itera por todos los números con los que se está operando, numero1, numero2 y el resultado.
		for (int j = 0; j < this.context.lists.length; j++) {
            Nodo current = this.context.lists[j].getHead();
			int i = 0;

			g.setFont(defaultFont);

			// Los dígitos con los cuales se está operando actualmente se resaltan con verde.
			if (j == 1 && i > this.context.lists[1].getSize() - this.context.lists[0].getSize()) {
				if (this.context.automorfico) {
					g.setColor(Color.GREEN);
				}

				g.setColor(Color.RED);
			} else {
				g.setColor(Color.CYAN);
			}

			String toDraw = Integer.toString(current.getValor());


			
			// Se dibujan los nodos como cuadrados, dentro de los cuales está el valor del dígito.
			g.fillRect(20 + 2 * this.context.cellSize * i, 20 + 3 * this.context.cellSize * j, this.context.cellSize, this.context.cellSize);
			g.setColor(Color.BLACK);
			g.drawRect(20 + 2 * this.context.cellSize * i, 20 + 3 * this.context.cellSize * j, this.context.cellSize, this.context.cellSize);
			
			g.drawString(toDraw, 20 + this.context.cellSize / 2 + 2 * this.context.cellSize * i - g.getFontMetrics().stringWidth(toDraw) / 2, 20 + this.context.cellSize / 2 + 3 * this.context.cellSize * j + 5);
			
			// Si hay un siguiente dígito se dibuja una flecha.
			if (current.getSiguiente() != this.context.lists[j].getHead()) {
				this.drawArrow(g, 20 + this.context.cellSize + 2 * this.context.cellSize * i, 20 + 3 * this.context.cellSize * j + this.context.cellSize / 2);
			} else {
				g.drawPolyline(new int[] {
					20 + this.context.cellSize / 2 + 2 * this.context.cellSize * i,
					20 + this.context.cellSize / 2 + 2 * this.context.cellSize * i,
					20 + this.context.cellSize / 2,
					20 + this.context.cellSize / 2,
				}, new int[] {
					20 + 3 * this.context.cellSize * j + this.context.cellSize,
					(int) Math.round(20 + 3 * this.context.cellSize * j + (int) this.context.cellSize * 1.5),
					(int) Math.round(20 + 3 * this.context.cellSize * j + (int) this.context.cellSize * 1.5),
					20 + 3 * this.context.cellSize * j + this.context.cellSize,
				}, 4);

				g.fillPolygon(
					new int[] {
						20 + this.context.cellSize / 2 - 4,
						20 + this.context.cellSize / 2 + 4,
						20 + this.context.cellSize / 2
					},
					new int[] {
						20 + 3 * this.context.cellSize * j + this.context.cellSize + 4,
						20 + 3 * this.context.cellSize * j + this.context.cellSize + 4,
						20 + 3 * this.context.cellSize * j + this.context.cellSize
					},
					3
				);
			}

			current = current.getSiguiente();
			i++;

			// Se itera a través de los dígitos del número actual, dibujando cada dígito en los gráficos.
            while (current != this.context.lists[j].getHead()) {
				g.setFont(defaultFont);

				// Los dígitos con los cuales se está operando actualmente se resaltan con verde.
				if (j == 1 && i > this.context.lists[1].getSize() - this.context.lists[0].getSize() - 1) {
					if (this.context.automorfico) {
						g.setColor(Color.GREEN);
					} else {
						g.setColor(Color.RED);
					}
				} else {
					g.setColor(Color.CYAN);
				}

				toDraw = Integer.toString(current.getValor());

				// Se dibujan los nodos como cuadrados, dentro de los cuales está el valor del dígito.
				g.fillRect(20 + 2 * this.context.cellSize * i, 20 + 3 * this.context.cellSize * j, this.context.cellSize, this.context.cellSize);
                g.setColor(Color.BLACK);
				g.drawRect(20 + 2 * this.context.cellSize * i, 20 + 3 * this.context.cellSize * j, this.context.cellSize, this.context.cellSize);
                
				g.drawString(toDraw, 20 + this.context.cellSize / 2 + 2 * this.context.cellSize * i - g.getFontMetrics().stringWidth(toDraw) / 2, 20 + this.context.cellSize / 2 + 3 * this.context.cellSize * j + 5);
				
				// Si hay un siguiente dígito se dibuja una flecha.
				if (current.getSiguiente() != this.context.lists[j].getHead()) {
					this.drawArrow(g, 20 + this.context.cellSize + 2 * this.context.cellSize * i, 20 + 3 * this.context.cellSize * j + this.context.cellSize / 2);
				} else {
					g.drawPolyline(new int[] {
						20 + this.context.cellSize / 2 + 2 * this.context.cellSize * i,
						20 + this.context.cellSize / 2 + 2 * this.context.cellSize * i,
						20 + this.context.cellSize / 2,
						20 + this.context.cellSize / 2,
					}, new int[] {
						20 + 3 * this.context.cellSize * j + this.context.cellSize,
						(int) Math.round(20 + 3 * this.context.cellSize * j + (int) this.context.cellSize * 1.5),
						(int) Math.round(20 + 3 * this.context.cellSize * j + (int) this.context.cellSize * 1.5),
						20 + 3 * this.context.cellSize * j + this.context.cellSize,
					}, 4);

					g.fillPolygon(
						new int[] {
							20 + this.context.cellSize / 2 - 4,
							20 + this.context.cellSize / 2 + 4,
							20 + this.context.cellSize / 2
						},
						new int[] {
							20 + 3 * this.context.cellSize * j + this.context.cellSize + 4,
							20 + 3 * this.context.cellSize * j + this.context.cellSize + 4,
							20 + 3 * this.context.cellSize * j + this.context.cellSize
						},
						3
					);
				}

				current = current.getSiguiente();
                i++;
            }

			g.setFont(defaultFont);
		}
	}
	
	// Dibuja las flechas que conectan los nodos en la lista enlazada.
	public void drawArrow(Graphics g, int x, int y) {
		final int WIDTH = 30;
		g.drawLine(x, y, x + WIDTH, y);
		g.fillPolygon(
			new int[] {
				x + WIDTH - 4,
				x + WIDTH - 4,
				x + WIDTH
			},
			new int[] {
				y + 4,
				y - 4,
				y
			},
			3
		);
	}
}
