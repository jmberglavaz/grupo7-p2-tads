package um.edu.uy.excepciones;

public class ParentNotFoundException extends RuntimeException {
    public ParentNotFoundException() {
        super("Parent key not found");
    }
}
