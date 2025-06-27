package um.edu.uy.adt.list;

import um.edu.uy.excepciones.EmptyListException;
import um.edu.uy.excepciones.ListOutOfIndexException;
import um.edu.uy.excepciones.NonExistentValueException;

public interface MyList<T> extends Iterable<T> {
    void add(T data);
    void add(T data, int index) throws ListOutOfIndexException;
    void addFirst(T data);

    T delete(int index) throws ListOutOfIndexException, EmptyListException;
    T deleteLast() throws EmptyListException;
    T deleteFirst() throws EmptyListException;
    void deleteValue(T data) throws EmptyListException, ListOutOfIndexException, NonExistentValueException;

    int size();

    boolean contains(T data);

    T get(int index) throws ListOutOfIndexException, EmptyListException;
    void intercambiate(int firstIndex, int secondIndex) throws EmptyListException, ListOutOfIndexException;
    boolean isEmpty();
}