package lab2.punto1;

import java.util.Scanner;

import lab2.punto1.cli.CommandLineInterface;
import lab2.punto1.gui.GraphicalUserInterface;
import lab2.punto1.lists.Digit;
import lab2.punto1.lists.Number;
/**
* Contiene un conjunto de atributos a los cuales todas las clases en el presente programa deben tener acceso para el correcto funcionamiento. Es la clase encargada de almamcenar el estado del programa para que se de la correcta comunicación entre el hilo de los gráficos y el hilo donde se procesan las operaciones.
* 
* @attribute numbers Arreglo que contiene los 2 números con los cuales se está haciendo la operación actual, y el número donde se está almacenando el resultado.
* @attribute currentDigits Arreglo que contiene los dígitos con los que se está operando actualmente para que sean debidamente resaltados en la interfaz gráfica.
* @attribute carry Contiene el acarreo de la última operación.
* @attribute cellSize El tamaño en pixeles que cada nodo tendrá en la interfaz gráfica.
* @attribute operation El signo de la operación que se está realizando (+ o x)
* @attribute cli La interfaz de línea de comandos del programa
* @attribute gui La interfaz gráfica del programa
* @attribute lock Un objeto para sincronizar el hilo de gráficos con el hilo en el cual se realizan las operaciones y lograr la comunicación entre ambos hilos.
*/
public class AnimationContext {
    public Number[] numbers;
    public Digit[] currentDigits;
    public int carry;
    public int cellSize = 30;
    public int refreshTime = 2000;
    public String operation = "";
    public CommandLineInterface cli;
    public GraphicalUserInterface gui;
    public Scanner in;
    public Object lock;

    public void update(Number[] numbers, Digit[] currentDigits, int carry, String operation) {
        this.numbers = numbers;
        this.currentDigits = currentDigits;
        this.carry = carry;
        this.operation = operation;
    }
}
