package lab2.punto1.lists;

import lab2.punto1.AnimationContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */


/**
* Representa un número entero mediante una lista enlazada simple.
* 
* @attribute sign: El signo del número y primer nodo de la lista enlaza. Es 1 si es positivo y -1 si es negativo.
* @attribute length: Longitud del número sin contar el signo.
*/
public class Number {
	public Digit sign;
    public int length = 0;

    /**
    * Representa un número entero mediante una lista enlazada simple.
    * 
    * @param value El valor del número entero.
    */
	public Number(long value) {
        // Asigna 1 o -1 al signo dependiendo si el número es positivo o negativo
		if (value >= 0)
            this.sign = new Digit(1);
        else
            this.sign = new Digit(-1);
        
        // Convierte el número a String, saca cada dígito y lo añade a la lista enlazada.
        String number = Long.toString(Math.abs(value));

        for (int i = 0; i < number.length(); i++) {
            int digit = (int) (number.charAt(i) - '0');
            this.push(digit);    
        }
    }
	
    /**
    * Inserta un dígito al final de la lista enlazada.
    * 
    * @param data El valor del dígito a insertar.
    */
    private void push(int data) {
        // Si el signo es nulo no se hace nada, el número está vacío.
        if (this.sign == null) {
            return;
        } else {
            // Sino, se mueve al final de la lista y se añade el nuevo dígito.
            Digit current = this.sign;

            while (current.next != null) {
                current = current.next;
            }

            current.next = new Digit(data);
            this.length++;
        }

    }

    /**
    * Inserta un dígito en un una posición determinada.
    * 
    * @param index La posición en la cual se insertará.
    * @param data El valor del dígito a insertar.
    */
    public void insertAt(int index, int data) {
        // No se puede cambiar el signo del número, si el indice es 0 no se hace nada
        if (index == 0) {
            return;
        } else {
            // Se itera a través de la lista hasta que el nodo actual sea el que esté en la posición buscada
            Digit last = this.sign;
            Digit current = this.sign.next;
            int i = 1;

            while (current.next != null && i < index) {
                last = current;
                current = current.next;
                i++;
            }

            // Si el índice está fuera de la lista, no se hace nada. Sino se inserta el nuevo número en la posición actual.
            if (current == null && i < index) {
                return;
            } else {
                last.next = new Digit(data, current);
                this.length++;
            }
        }
    }

    /**
    * Suma 2 números representados por listas enlazadas, retorna el resultado en otra lista enlazada.
    * 
    * @param number1
    * @param number2
    * @param context El objeto que contiene el estado actual del programa. Este método actualizará continuamente este objeto.
    * @return La suma de los 2 números en una lista enlazada.
    */
    public static Number add(Number number1, Number number2, AnimationContext context) {
        // Resultado de la operación
        Number result = new Number(0);

        // Para facilidad en number1 se tendrá siempre el número con mayor valor absoluto. Si number2 es mayor, entonces se intercambian las referencias.
        if (Math.abs(number1.getValue()) < Math.abs(number2.getValue())) {
            Number temp = number2;
            number2 = number1;
            number1 = temp;
        }

        // El resultado siempre tendrá el signo del mayor número.
        result.sign.data = number1.getSign();

        // Se añaden ceros a las listas para que tengan todas la misma longitud.
        for (int i = 0; i < number1.length - 1; i++) {
            result.push(0);
        }

        // Se añaden ceros a la izquierda al numero menor si es necesario.
        if (number1.length > number2.length) {
            int lengthDifference = number1.length - number2.length;

            for (int i = 0; i < lengthDifference; i++) {
                number2.insertAt(1, 0);
            }
        }

        // Se actualiza context y se espera que se presione el botón de step en la interfaz gráfica
        context.update(new Number[] { number1, number2, result }, null, 0, "+");
        context.cli.stopAndWait();

        // Si los signos de ambos números son iguales se realiza un suma, de lo contrario se realiza una resta.
        if (number1.getSign() == number2.getSign()) {
            int carry = addRecursively(number1.sign.next, number2.sign.next, result.sign.next, context);

            // Si hay acarreo, se añade en un nodo a la izquierda del número.
            if (carry > 0) {
                result.insertAt(1, carry);
            }
        } else {
            substractRecursively(number1.sign.next, number2.sign.next, result.sign.next, context);
        }

        context.currentDigits[2] = result.sign.next;
        
        return result;
    }

    /**
    * Suma recursivamente 2 números dígito por dígito y guarda el resultado en un tercer número. Se asume que los 3 números tienen la misma longitud. Retornar el acarreo de la suma para que sea sumado al dígito anterior.
    * 
    * @param number1
    * @param number2
    * @param number3 Donde se guarda el resultado
    * @param context El objeto que contiene el estado actual del programa. Este método actualizará continuamente este objeto.
    * @return El acarreo de la anterior suma.
    */
    private static int addRecursively(Digit digit1, Digit digit2, Digit digit3, AnimationContext context) {
        // Condición base, como todos los números tienen la misma longitud, si digit1 es nulo, todos son nulos.
        if (digit1 == null)
            return 0;

        // Hace un llamado a que se sumen los siguientes dígitos, cuando se retorne el acarreo de esta operación, lo suma con digit1 y digit2.
        int carry = addRecursively(digit1.next, digit2.next, digit3.next, context);
        
        // Se cambian los dígitos que se están utilizando en el contexto, para que la animación pueda resaltarlos debidamente.
        context.currentDigits = new Digit[] {
            digit1,
            digit2,
            digit3
        };

        digit3.data = carry + digit1.data + digit2.data;

        // Si el valor de digit3 es 10 o más significa que se debe retornar acarreo. Se actualiza el contexto y se detiene la ejecución hasta que se presione el botón de step nuevamente.
        if (digit3.data > 9) {
            int newCarry = digit3.data / 10;
            digit3.data %= 10;
            context.carry = newCarry;
            context.cli.stopAndWait();

            return newCarry;
        }

        context.carry = 0;
        context.cli.stopAndWait();

        return 0;
    }

    /**
    * Resta recursivamente 2 números dígito por dígito y guarda el resultado en un tercer número. Se asume que los 3 números tienen la misma longitud. Retorna lo que se presta en la resta para que sea restado al dígito anterior.
    * 
    * @param number1
    * @param number2
    * @param number3 Donde se guarda el resultado
    * @param context El objeto que contiene el estado actual del programa. Este método actualizará continuamente este objeto.
    * @return 1 si se prestó en la anterior resta, de lo contrario 0.
    */
    private static int substractRecursively(Digit digit1, Digit digit2, Digit digit3, AnimationContext context) {
        // Condición base, como todos los números tienen la misma longitud, si digit1 es nulo, todos son nulos.
        if (digit1 == null)
            return 0;

        // Hace un llamado a que se resten los siguientes dígitos, cuando se retorne lo que se prestó para restar los siguientes dígitos, lo suma con digit2 y el resultado de esto lo resta a digit1.
        int borrow = substractRecursively(digit1.next, digit2.next, digit3.next, context);

        // Se cambian los dígitos que se están utilizando en el contexto, para que la animación pueda resaltarlos debidamente.
        context.currentDigits = new Digit[] {
            digit1,
            digit2,
            digit3
        };

        digit3.data = digit1.data - (borrow + digit2.data);

        // Si el valor de digit3 es negativo es necesario prestar del dígito anterior, se le suma 10 y la función retorna 1. Se actualiza el contexto y se detiene la ejecución hasta que se presione el botón de step nuevamente.
        if (digit3.data < 0) {
            digit3.data += 10;
            context.carry = 1;
            context.cli.stopAndWait();

            return 1;
        }

        context.carry = 0;
        context.cli.stopAndWait();

        return 0;
    }

    /**
    * multiplica 2 números representados por listas enlazadas, retorna el resultado en otra lista enlazada.
    * 
    * @param number1
    * @param number2
    * @param context El objeto que contiene el estado actual del programa. Este método actualizará continuamente este objeto.
    * @return La multiplicación de los 2 números en una lista enlazada.
    */
    public static Number multiply(Number number1, Number number2, AnimationContext context) {
        Number result = new Number(0);

        // Igual que antes se se guarda el mayor en number1.
        if (Math.abs(number1.getValue()) < Math.abs(number2.getValue())) {
            Number temp = number2;
            number2 = number1;
            number1 = temp;
        }

        // Si los signos de ambos números son iguales el resultado será positivo, sino será negativo.
        if (number1.getSign() == number2.getSign()) {
            result.sign.data = 1;
        } else {
            result.sign.data = -1;
        }

        // Se iguala la longitud de los 3 números.
        if (number1.length > number2.length) {
            int lengthDifference = number1.length - number2.length;

            for (int i = 0; i < lengthDifference; i++) {
                number2.insertAt(1, 0);
            }
        }

        for (int i = 0; i < number1.length - 1; i++) {
            result.push(0);
        }

        // Se actualiza context y se espera que se presione el botón de step en la interfaz gráfica
        context.update(new Number[] { number1, number2, result }, null, 0, "x");
        context.cli.stopAndWait();

        // Se itera a través de los dígitos de number 2, desde el menos significativo al mas significativo para multiplicar cada uno con cada dígito de number1, como una multiplicación paso a paso.
        for (int i = 0; i < number2.length; i++) {
            int j = 1;
            Digit current = number2.sign.next;

            // Se busca el i-ésimo digit de number2 de derecha a izquierda.
            while (j < number2.length - i) {
                current = current.next;
                j++;
            }

            // Se multiplica el dígito encontrado de number 2 con cada dígito de number 1, se acumula el resultado en result.
            int carry = multiplyRecursively(number1.sign.next, current, result.sign.next, context);
        
            // Se inserta el acarreo (Sin importar si es 0 o otro número) a la izquierda del resultado, esto también causará que por cada dígito de number2, se realizará un desplazamiento a la izquierda.
            result.insertAt(1, carry);
            context.currentDigits[2] = result.sign.next;
        }

        return result;
    }

    /**
    * Multiplica un todo un número representado por una lista enlazada por un solo dígito, sumando el resultado de esta operación a otro número representado por una lista enlazada.
    * 
    * @param digit1 Primer dígito del número que se quiere multiplicar
    * @param digit2 El dígito por el cual se quiere multiplicar el número
    * @param digit3 Primer dígito del número al cual se le sumará el resultado.
    * @param context El objeto que contiene el estado actual del programa. Este método actualizará continuamente este objeto.
    * @return El acarreo de la anterior multiplicación.
    */
    private static int multiplyRecursively(Digit digit1, Digit digit2, Digit digit3, AnimationContext context) {
        // Condición base
        if (digit1 == null)
            return 0;

        // Hace un llamado a que se multiplique el siguiente dígito del número con digit2. El acarreo de esta operación lo suma a la multiplicación del dígito actual con digit2.
        int carry = multiplyRecursively(digit1.next, digit2, digit3.next, context);

        // Se cambian los dígitos que se están utilizando en el contexto, para que la animación pueda resaltarlos debidamente.
        context.currentDigits = new Digit[] {
            digit1,
            digit2,
            digit3
        };

        digit3.data += carry + (digit1.data * digit2.data);

        // Si el valor de digit3 es 10 o más entonces se debe retornar el acarreo. Se actualiza el contexto y se detiene la ejecución hasta que se presione el botón de step nuevamente.
        if (digit3.data > 9) {
            int newCarry = digit3.data / 10;
            digit3.data %= 10;
            context.carry = newCarry;
            context.cli.stopAndWait();

            return newCarry;
        }

        context.carry = 0;
        context.cli.stopAndWait();

        return 0;
    }

    /**
    * Retorna el valor numérico como un long.
    * 
    * @return Valor numérico del número representado por una lista enlazada
    */
    public long getValue() {
        Digit current = this.sign.next;
        long sum = 0;
        int i = 1;

        // Se itera através de la lista multiplicando el valor de cada dígito por el signo del número y por una potencia de 10 dependiendo de la posición del dígito con respecto al final de la lista.
        while (current != null) {
            sum += (long) this.sign.data * current.data * Math.pow(10, this.length - i);
            current = current.next;
            i++;
        }

        return sum;
    }

    /**
    * Obtiene el valor del signo del número. Equivalente a Number.sign.data
    * 
    * @return El valor del signo del número
    */
    public int getSign() {
        return this.sign.data;
    }

    /**
    * Retorna el número representado por una lista enlazada como una String.
    * 
    * @return La lista enlazada en forma "signo digito1 -> digito2 -> digito3 -> ... -> digiton"
    */
    @Override
    public String toString() {
        String string = this.getSign() == 1 ? "" : "-";
        Digit current = this.sign.next;
        Boolean firstDigit = false;

        while (current != null) {
            if (current.data > 0)
                firstDigit = true;

            if (firstDigit) {
                string += " " + current.data;

                if (current.next != null) {
                    string += " ->";
                }
            }

            current = current.next;
        }

        return string;
    }
}
