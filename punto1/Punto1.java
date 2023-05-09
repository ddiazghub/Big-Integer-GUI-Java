package lab2.punto1;

import java.util.Scanner;

import lab2.punto1.cli.CommandLineInterface;
import lab2.punto1.gui.GraphicalUserInterface;

public class Punto1 {
    // El punto se resolvió en los siguientes métodos:
    // Inciso a: Number.add(), Number.addRecursively(), Number.substractRecursively().
    // Inciso b: Number.multiply(), Number.multiplyRecursively()
    
    public static void main(String[] args) {
        AnimationContext context = new AnimationContext();
        context.in = new Scanner(System.in);
        context.lock = new Object();
        context.gui = new GraphicalUserInterface(context);
        context.cli = new CommandLineInterface(context.in, context);
        context.cli.start();
    }
}
