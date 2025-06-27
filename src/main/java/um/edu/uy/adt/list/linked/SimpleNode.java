package um.edu.uy.adt.list.linked;

public class SimpleNode<T> {
    T data;
    SimpleNode<T> nextNode;

    public SimpleNode(T data) {
        this.data = data;
        this.nextNode = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SimpleNode<T> getNextNode() {
        return nextNode;
    }

    public void setNextNode(SimpleNode<T> nextNode) {
        this.nextNode = nextNode;
    }
}
