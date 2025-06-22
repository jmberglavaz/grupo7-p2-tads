package um.edu.uy.TADs.HeapKT;

public class HeapNode<K extends Comparable<K>, T> {
    private K key;
    private T data;

    public HeapNode(K key, T data) {
        if (key == null || data == null) {
            throw new IllegalArgumentException();
        }
        this.key = key;
        this.data = data;
    }

    public int compareTo(HeapNode<K, T> node) {
        return key.compareTo(node.key);
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }
}
