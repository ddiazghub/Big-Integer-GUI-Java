/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lab2.punto2.lists;

import lab2.punto2.AnimationContext;

/**
 *
 * @author USER
 */
public class ListaCircular {
    private Nodo PrimerNodo;
    private Nodo LastNodo;
    private int size = 0;

    public ListaCircular(String number){
        String value = "";
        int base;

        if (number.charAt(0) == '0' && number.length() > 2) {
            String start = number.substring(0, 2);
            value = number.substring(2, number.length());

            base = getBaseFromPrefix(start);
        } else {
            value = number;
            base = 10;
        }

        value = Long.toString(Long.parseLong(value, base));

        for (int i = 0; i < value.length(); i++) {
            int digit = (int) value.charAt(i) - '0';
            this.agregarNodo(digit);    
        }
    }

    public int getSize(){
        return size;
    }

    public boolean estaVacia(){
        return PrimerNodo == null;
    }

    public void agregarNodo(int valor){
        Nodo nuevo = new Nodo();
        nuevo.setValor(valor);
        if (estaVacia()) {
            PrimerNodo = nuevo;
            LastNodo = nuevo;
            LastNodo.setSiguiente(PrimerNodo);
        } else{
            LastNodo.setSiguiente(nuevo);
            nuevo.setSiguiente(PrimerNodo);
            LastNodo = nuevo;
        }
        size++;
    }  

    public long getValue() {
        Nodo current = this.PrimerNodo;
        long sum = 0;
        int i = 1;

        // Se itera através de la lista multiplicando el valor de cada dígito por el signo del número y por una potencia de 10 dependiendo de la posición del dígito con respecto al final de la lista.
        while (current != this.LastNodo) {
            sum += current.getValor() * Math.pow(10, this.size - i);
            current = current.getSiguiente();
            i++;
        }

        sum += this.LastNodo.getValor();

        return sum;
    }

    public Nodo getHead() {
        return this.PrimerNodo;
    }

    public static int getBaseFromPrefix(String prefix) {
        if (prefix.equals("0b")) {
            return 2;
        } else if (prefix.equals("0o")) {
            return 8;
        } else if (prefix.equals("0x")) {
            return 16;
        } else {
            return 10;
        }
    }

    public static String toBase(long value, int base) {
        return Long.toString(value, base);
    }

    public static boolean automorfico(ListaCircular lista, AnimationContext context) {
        long valor = lista.getValue();
        long cuadrado = (long) Math.pow(valor, 2);
        ListaCircular listaCuadrado = new ListaCircular(Long.toString(cuadrado));

        String stringValor = Long.toString(valor);
        int sizeValor = stringValor.length();
        String stringCuadrado = Long.toString(cuadrado);
        int sizeCuadrado = stringCuadrado.length();
        String numero = stringCuadrado.substring((sizeCuadrado - sizeValor) ,sizeCuadrado);
        
        context.lists = new ListaCircular[] {
            lista,
            listaCuadrado
        };

        context.automorfico = stringValor.equals(numero);

        if (stringValor.equals(numero)) {
            System.out.println( stringValor + " es un número automórfico ya que esta contenido al final de su cuadrado: " + stringCuadrado);
            return true;
        }

        return false;
    }
}
