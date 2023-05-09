package lab2.punto1.cli;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
import java.util.Scanner;

import lab2.punto1.AnimationContext;
import lab2.punto1.lists.Number;

/**
* Interfaz de línea de comandos para darle interactividad al presente programa
*/
public class CommandLineInterface extends Thread {

	private Scanner in;
    private AnimationContext context;
    private Boolean isRunning = true;
    public Object lock;

    /**
    * Inicia una interfaz de linea de comandos usando el Scanner suministrado para leer
    *
    * @param in Scanner para lectura de comandos.
    * @param context Objeto que representa el estado actual del programa.
    */
	public CommandLineInterface(Scanner in, AnimationContext context) {
		this.in = in;
        this.context = context;
        this.context.cli = this;
        this.lock = this.context.lock;
    }

    /**
    * Lista los comandos disponibles junto con una breve descripción
    */
	private void help() {
		System.out.println("Bienvenido, los comandos son:\n");
		System.out.println("sumar <n1> <n2>               - Suma ambos números.");
		System.out.println("multiplicar <n1> <n2>         - Multiplica ambos números.");
		System.out.println("ayuda                         - Mostrar comandos.");
		System.out.println("salir                         - Salir del programa.\n");
	}
	
    /**
    * Inicia la interfaz de línea de comandos.
    */
	public void run() {
		help();

		while (this.isRunning) {
            // Lee el comando del usuario
			System.out.print("$ ");
			String input = in.nextLine();

            // Si no se escribió nada, vuelve a leer.
            if (input.length() == 0) {
                continue;
            }

            // Separa el comando por espacios
            String command[] = input.split(" ");

            // Lee la primera palabra del comando para saber qué ejecutar
            if (command[0].equals("sumar") || command[0].equals("multiplicar")) {
                if (command.length != 3) {
                    System.err.println("Error: Por favor introduzca un número válido de argumentos");
                    continue;
                }

                // Se usa un try-catch para validar que ambos argumentos ingresados son valores númericos enteros.
                try {
                    Number number1 = new Number(Long.parseLong(command[1]));
                    Number number2 = new Number(Long.parseLong(command[2]));

                    Number result;

                    // Se muestran ambos números en consola
                    System.out.println(command[1] + ":\n" + number1);
                    System.out.println(command[2] + ":\n" + number2);

                    // Se suma o multiplica dependiendo del comando. Se muestra la animación de la operación en una interfaz gráfica.
                    if (command[0].equals("sumar")) {
                        result = Number.add(number1, number2, context);
                    } else {
                        result = Number.multiply(number1, number2, context);
                    }
    
                    // Se muestra el resultado de la operación
                    System.out.println(result.getValue() + ":\n" + result);
                } catch (NumberFormatException e) {
                    System.err.println("Error: Los argumentos deben ser números enteros");
                }
            } else if (command[0].equals("ayuda")) {
                System.out.println("\n");
                help();
            } else if (command[0].equals("salir")) {
                System.out.println("\nSe ha cerrado el programa.\n");
                this.isRunning = false;

                this.context.gui.stop();
            } else {
                System.err.println("Error: El comando no existe.");
            }
		}

        return;
	}

    public void stopAndWait() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
