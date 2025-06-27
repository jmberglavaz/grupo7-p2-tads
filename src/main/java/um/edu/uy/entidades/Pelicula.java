package um.edu.uy.entidades;

import um.edu.uy.adt.list.linked.MyLinkedListImpl;
import um.edu.uy.adt.list.MyArrayListImpl;
import um.edu.uy.adt.list.MyList;

/**
 * Representa una película con sus datos principales y listas de evaluaciones.
 */
public class Pelicula implements Comparable<Pelicula> {
    private int id;
    private String title;
    private String releaseDate;
    private long revenue;

    /**
     * Estructura optimizada: una lista de 12 listas. Cada sub-lista interna
     * corresponde a un mes del año (índice 0 = Enero, 1 = Febrero, etc.),
     * y almacena las evaluaciones de ese mes.
     */
    private final MyList<MyList<Evaluacion>> reviewsByMonth;

    /**
     * Constructor para crear una nueva Pelicula.
     * Inicializa la estructura para almacenar evaluaciones por mes.
     */
    public Pelicula(int id, String title, String releaseDate, long revenue) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.reviewsByMonth = new MyArrayListImpl<>(12);
        // Se inicializan las 12 listas internas, una para cada mes
        for (int i = 0; i < 12; i++) {
            reviewsByMonth.add(new MyLinkedListImpl<>());
        }
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getRevenue() {
        return revenue;
    }

    /**
     * Devuelve una lista con TODAS las evaluaciones de la película, juntando
     * las de todos los meses.
     * @return Una lista con todas las evaluaciones.
     */
    public MyList<Evaluacion> getAllReviews() {
        MyList<Evaluacion> resultList = new MyArrayListImpl<>(100);
        for (MyList<Evaluacion> monthlyReviews : this.reviewsByMonth) {
            if (monthlyReviews == null) { continue; }
            for (Evaluacion review : monthlyReviews) {
                resultList.add(review);
            }
        }
        return resultList;
    }

    /**
     * Devuelve la lista de evaluaciones para un mes específico.
     * @param month El mes (1-12).
     * @return La lista de evaluaciones correspondiente a ese mes.
     */
    public MyList<Evaluacion> getReviewsForMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        return reviewsByMonth.get(month - 1);
    }

    /**
     * Calcula y devuelve el número total de evaluaciones que ha recibido la película.
     * @return El total de evaluaciones.
     */
    public int getTotalReviewCount() {
        int size = 0;
        for (MyList<Evaluacion> monthlyReviews : reviewsByMonth) {
            size += monthlyReviews.size();
        }
        return size;
    }

    /**
     * Calcula y devuelve el promedio de todas las calificaciones recibidas.
     * @return El promedio de calificaciones, o 0 si no tiene evaluaciones.
     */
    public float getAverageRating() {
        float totalRatingSum = 0;
        int reviewCount = 0;
        for (MyList<Evaluacion> monthlyReviews : reviewsByMonth) {
            reviewCount += monthlyReviews.size();
            for (Evaluacion review : monthlyReviews)
                totalRatingSum += review.getRating();
        }
        return reviewCount == 0 ? 0 : totalRatingSum / reviewCount;
    }

    /**
     * Agrega una nueva evaluación a la película, colocándola en la lista
     * del mes correspondiente.
     * @param review La evaluación a agregar.
     */
    public void addReview(Evaluacion review) {
        MyList<Evaluacion> monthlyList = reviewsByMonth.get(review.getReviewMonth());
        monthlyList.add(review);
    }

    /**
     * Compara esta película con otra basándose en la cantidad total de evaluaciones.
     * Esto permite ordenar las películas por popularidad (cantidad de votos).
     */
    @Override
    public int compareTo(Pelicula otherMovie) {
        return Integer.compare(this.getTotalReviewCount(), otherMovie.getTotalReviewCount());
    }
}