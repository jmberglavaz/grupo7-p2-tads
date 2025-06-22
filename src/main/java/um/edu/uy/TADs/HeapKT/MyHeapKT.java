package um.edu.uy.TADs.HeapKT;

import um.edu.uy.Exceptions.EmptyHeapException;

public interface MyHeapKT<K extends Comparable<K>, T> {
    void insert(K key, T data);
    T deleteAndObtain() throws EmptyHeapException;
    HeapNode<K, T> deleteAndObtainNode() throws EmptyHeapException;
    int size();
}
