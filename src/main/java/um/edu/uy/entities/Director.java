package um.edu.uy.entities;

import um.edu.uy.TADs.List.MyArrayListImpl;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.TADs.Sorting;

/**
 * Representa a un director de cine, con su nombre y las películas que ha dirigido.
 */
public class Director implements Comparable<Director>{
    private String name;
    private final MyList<Pelicula> movieList;

    // Campos de caché para optimizar el rendimiento y no recalcular valores.
    private float ratingMedian;
    private int reviewCountCache;

    /**
     * Constructor para crear un nuevo Director.
     * @param name El nombre del director.
     */
    public Director(String name) {
        this.name = name;
        this.movieList = new MyArrayListImpl<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Agrega una película a la filmografía del director.
     * @param movie La película a agregar.
     */
    public void addMovie(Pelicula movie) {
        this.movieList.add(movie);
    }

    public int getMovieCount(){
        return movieList.size();
    }

    /**
     * Obtiene el número total de evaluaciones de todas las películas del director.
     * Utiliza un caché para evitar recalcular este valor.
     * @return El número total de evaluaciones.
     */
    public int getTotalReviewCount(){
        // Si el valor ya fue calculado, se devuelve directamente desde el caché
        if (this.reviewCountCache != 0){
            return this.reviewCountCache;
        }

        // Si no, se calcula, se guarda en el caché y se devuelve
        int count = 0;
        for (Pelicula movie : movieList){
            count += movie.getTotalReviewCount();
        }
        this.reviewCountCache = count;
        return reviewCountCache;
    }

    /**
     * Calcula y obtiene la mediana de las calificaciones de todas las películas del director.
     * Solo se calcula si el director tiene más de 1 película y más de 100 evaluaciones en total.
     * Utiliza un caché para evitar recalcular este valor.
     * @return La mediana de las calificaciones, o 0 si no cumple los requisitos.
     */
    public float getRatingMedian(){
        // Si la mediana ya fue calculada, se devuelve desde el caché
        if (this.ratingMedian != 0){
            return ratingMedian;
        }

        int totalReviews = getTotalReviewCount();
        if (movieList.size() <= 1 || totalReviews <= 100){
            return 0; // No cumple las condiciones para calcular la mediana
        }

        // Junta todas las calificaciones en una sola lista
        MyList<Float> allRatings = new MyArrayListImpl<>(totalReviews);
        for (Pelicula movie : movieList){
            if (movie.getTotalReviewCount() == 0){ continue; }
            for (Evaluacion review : movie.getAllReviews()){
                allRatings.add(review.getRating());
            }
        }

        // Ordena la lista para calcular la mediana
        Sorting<Float> sorter = new Sorting<>();
        allRatings = sorter.quickSort(allRatings);

        // Calcula la mediana dependiendo de si el número de elementos es par o impar
        if (totalReviews % 2 == 0) {
            this.ratingMedian = (allRatings.get(totalReviews / 2) + allRatings.get((totalReviews / 2) - 1)) / 2;
        } else {
            this.ratingMedian = allRatings.get(((totalReviews + 1) / 2) - 1);
        }
        return this.ratingMedian;
    }

    /**
     * Compara este director con otro basándose en la mediana de sus calificaciones.
     * Necesario para el ordenamiento en el Heap.
     */
    @Override
    public int compareTo(Director otherDirector) {
        return Float.compare(this.getRatingMedian(), otherDirector.getRatingMedian());
    }
}