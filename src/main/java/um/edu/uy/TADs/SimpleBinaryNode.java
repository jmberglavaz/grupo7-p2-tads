package um.edu.uy.TADs.Tree;

public class SimpleBinaryNode<K,T> {
    private K key;
    private T data;
    private SimpleBinaryNode<K, T> leftChild;
    private SimpleBinaryNode<K, T> rightChild;

    public SimpleBinaryNode(K key, T data) {
        this.key = key;
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SimpleBinaryNode<K, T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(SimpleBinaryNode<K, T> leftChild) {
        this.leftChild = leftChild;
    }

    public SimpleBinaryNode<K, T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(SimpleBinaryNode<K, T> rightChild) {
        this.rightChild = rightChild;
    }
}
