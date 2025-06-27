package um.edu.uy.adt.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayListIterator<T> implements Iterator<T> {

    private final T[] array;           // Referencia al array interno del ArrayList
    private final int size;            // Tamaño actual del ArrayList
    private int currentIndex;    // Índice actual del iterador

    // Constructor que recibe el array y el tamaño del ArrayList
    public MyArrayListIterator(T[] array, int size) {
        this.array = array;
        this.size = size;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < size;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más elementos en el iterador");
        }
        return array[currentIndex++];
    }
}