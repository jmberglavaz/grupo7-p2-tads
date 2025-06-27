package um.edu.uy.adt.list;

import um.edu.uy.excepciones.EmptyStackException;

public interface MyStack<T> extends Iterable<T> {

    public void push(T value);

    public T pop() throws EmptyStackException;

    public T top() throws EmptyStackException;

    public boolean isEmpty();

    public void makeEmpty();

    public int getSize();

}
