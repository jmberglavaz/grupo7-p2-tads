package um.edu.uy.entities;

import java.util.Date;

/**
 * Representa una evaluación (calificación) de un usuario para una película en una fecha específica.
 */
public class Evaluacion {
    private Integer userId;
    private float rating;
    private Integer reviewMonth; // Mes en que se hizo la reseña (0=Enero, 1=Febrero, etc.)

    /**
     * Constructor para crear una nueva Evaluacion.
     * @param userId El ID del usuario que realiza la evaluación.
     * @param rating La calificación dada (ej. de 1 a 5).
     * @param date La fecha en que se realizó la evaluación.
     */
    @SuppressWarnings("deprecation") // Se usa getMonth() que está deprecado, pero el CSV lo requiere así.
    public Evaluacion(int userId, float rating, Date date) {
        this.userId = userId;
        this.rating = rating;
        this.reviewMonth = date.getMonth(); // Extrae el mes de la fecha
    }

    // GETTERS
    public int getUserId() {
        return userId;
    }

    public float getRating() {
        return rating;
    }

    public int getReviewMonth() {
        return reviewMonth;
    }
}