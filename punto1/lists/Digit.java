package lab2.punto1.lists;

/**
* Representa un dígito de un número mediante un nodo de una lista enlazada
* 
* @attribute data: El valor del dígito
* @attribute next: El siguiente dígito del número. Si es el menos significativo este campo es null
*/
public class Digit {
    public int data;
    public Digit next;

    public Digit(int data) {
        this.data = data;
        this.next = null;
    }

    public Digit(int data, Digit next) {
        this.data = data;
        this.next = next;
    }

    @Override
    public String toString() {
        return Integer.toString(this.data);
    }
}
