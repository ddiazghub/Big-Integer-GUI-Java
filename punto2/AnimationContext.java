package lab2.punto2;

import lab2.punto2.lists.ListaCircular;

/**
* Contiene un conjunto de atributos a los cuales todas las clases en el presente programa deben tener acceso para el correcto funcionamiento. Es la clase encargada de almamcenar el estado del programa para que se de la correcta comunicación entre el hilo de los gráficos y el hilo donde se procesan las operaciones.
*/
public class AnimationContext {
    public ListaCircular[] lists;
    public Boolean automorfico = false;
    public int cellSize = 30;
}
