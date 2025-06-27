package um.edu.uy.adt.hash;

public class HashNode<K,T> {
    private T data;
    private K key;

    public HashNode(T data, K key) {
        this.data = data;
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }
}
