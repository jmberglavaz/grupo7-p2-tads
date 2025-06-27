package um.edu.uy.excepciones;

public class FullNodeException extends RuntimeException {
    public FullNodeException() {
        super("Nodo already have left and right child");
    }
}
