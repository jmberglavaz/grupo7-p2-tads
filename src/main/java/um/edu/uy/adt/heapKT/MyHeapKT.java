package um.edu.uy.adt.heapKT;

import um.edu.uy.excepciones.EmptyHeapException;

public interface MyHeapKT<K extends Comparable<K>, T> {
    void insert(K key, T data);
    T deleteAndObtain() throws EmptyHeapException;
    HeapNode<K, T> deleteAndObtainNode() throws EmptyHeapException;
    int size();
    K obtainRootKey();
}
