package um.edu.uy.adt.heap;

import um.edu.uy.excepciones.EmptyListException;

public interface MyHeap<T extends Comparable<T>> {
    void insert (T data);
    T deleteAndObtain() throws EmptyListException;
    int size();
}
