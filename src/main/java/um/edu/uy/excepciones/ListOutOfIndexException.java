package um.edu.uy.excepciones;

public class ListOutOfIndexException extends RuntimeException {
    public ListOutOfIndexException(String message) {
        super(message);
    }
}
