package um.edu.uy.adt.list;

import um.edu.uy.excepciones.EmptyQueueException;

public interface MyQueue<T> extends Iterable<T>{

    void enqueue (T value);

    void enqueueWithPriority (T value);

    T dequeue () throws EmptyQueueException;

    boolean isEmpty();

    int getSize();


}
