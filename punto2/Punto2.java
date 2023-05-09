/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lab2.punto2;

import lab2.punto2.gui.GraphicalUserInterface;

/**
 *
 * @author USER
 */
public class Punto2 {
    // El método en el cual se resolvió el punto es el método ListaCircular.automorfica(ListaCircular lista, AnimationContext context).

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Creación de la lista circular
        
        AnimationContext context = new AnimationContext();
        new GraphicalUserInterface(context);

        //Identificación si el elemento de la lista es un número automorfico
    }

}
