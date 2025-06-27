package um.edu.uy.adt.list.linked;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedListIterator<T> implements Iterator<T> {
    private SimpleNode<T> current;

    public MyLinkedListIterator(SimpleNode<T> head) {
        current = head;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements");
        }
        T data = current.getData();
        current = current.getNextNode();
        return data;
    }
}