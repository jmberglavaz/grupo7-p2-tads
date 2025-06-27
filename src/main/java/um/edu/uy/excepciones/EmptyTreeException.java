package um.edu.uy.excepciones;

public class EmptyTreeException extends RuntimeException {
    public EmptyTreeException() {
        super("The tree is empty");
    }
}
