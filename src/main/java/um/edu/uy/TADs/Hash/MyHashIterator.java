package um.edu.uy.TADs.Hash;

import java.util.Iterator;

public class MyHashIterator<T> implements Iterator<T> {
    private final HashNode<?, T>[] table;
    private final HashNode<?, T> deleteNode;
    private int currentIndex;

    public MyHashIterator(HashNode<?, T>[] table, HashNode<?, T> deleteNode) {
        this.table = table;
        this.deleteNode = deleteNode;
        this.currentIndex = 0;
        moveToNextValid(); // Mover al primer elemento válido
    }

    public boolean hasNext() {
        return currentIndex < table.length;
    }

    public T next() {
        if (!hasNext()) {
            throw new RuntimeException("No more elements");
        }

        T data = table[currentIndex].getData();
        currentIndex++;
        moveToNextValid();
        return data;
    }

    private void moveToNextValid() {
        while (currentIndex < table.length &&
                (table[currentIndex] == null || table[currentIndex] == deleteNode)) {
            currentIndex++;
        }
    }
}