package um.edu.uy.excepciones;

public class ElementAlreadyExists extends RuntimeException {
    public ElementAlreadyExists(String message) {
        super(message);
    }
}
