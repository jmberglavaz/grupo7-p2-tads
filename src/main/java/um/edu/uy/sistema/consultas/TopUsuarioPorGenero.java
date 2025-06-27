package um.edu.uy.sistema.consultas;

import um.edu.uy.excepciones.ElementAlreadyExists;
import um.edu.uy.excepciones.NonExistentValueException;
import um.edu.uy.adt.hash.MyHash;
import um.edu.uy.adt.hash.MyPrimitiveIntHash;
import um.edu.uy.adt.hash.MyPrimitiveIntHashImpl;
import um.edu.uy.adt.heapKT.MyHeapKT;
import um.edu.uy.adt.heapKT.MyHeapKTImplementation;
import um.edu.uy.adt.list.MyList;
import um.edu.uy.entidades.Evaluacion;
import um.edu.uy.entidades.Genero;
import um.edu.uy.entidades.Pelicula;

/**
 * Consulta que muestra el usuario con más calificaciones por cada género.
 * Para cada género, identifica el usuario que más veces calificó películas de ese género.
 */
public class TopUsuarioPorGenero {
    /**
     * Ejecuta la consulta y muestra el usuario top por género según cantidad de calificaciones.
     * @param genreHash Hash con todos los géneros del sistema.
     */
    public static void realizarConsulta(MyHash<Integer, Genero> genreHash) {
        long startTime = System.currentTimeMillis();
        MyList<Genero> genreList = genreHash.getValues();
        MyHeapKT<Integer, Genero> genreHeap = new MyHeapKTImplementation<>(genreList.size(), false);

        // Inserta en el heap solo los géneros con al menos una calificación
        for (Genero currentGenre : genreList) {
            if (currentGenre != null) {
                int totalGenreReviews = currentGenre.getTotalReviewCount();
                if (totalGenreReviews > 0) genreHeap.insert(totalGenreReviews, currentGenre);
            }
        }

        MyPrimitiveIntHash userReviewCounts = new MyPrimitiveIntHashImpl(150000);

        // Para cada género top, busca el usuario con más reviews
        for (int i = 0; i < 10 && genreHeap.size() > 0; i++) {
            Genero topGenre = genreHeap.deleteAndObtain();
            int[] topUser = {-1, 0}; 

            // Recorre todas las películas del género y cuenta reviews por usuario
            for (Pelicula movie : topGenre.getMovieList()) {
                for (Evaluacion review : movie.getAllReviews()) {
                    int userId = review.getUserId();
                    if (userId == 0) continue;

                    int newCount;
                    try {
                        int currentCount = userReviewCounts.get(userId);
                        newCount = currentCount + 1;
                        userReviewCounts.changeValue(userId, newCount);
                    } catch (NonExistentValueException e) {
                        newCount = 1;
                        try { userReviewCounts.insert(userId, newCount); } catch (ElementAlreadyExists ignored) {}
                    }
                    if (newCount > topUser[1]) {
                        topUser[1] = newCount;
                        topUser[0] = userId;
                    }
                }
            }
            // Imprime el usuario top del género
            System.out.println(topUser[0] + ", " + topGenre.getName() + ", " + topUser[1]);
            userReviewCounts.clean();
        }
        System.out.println("Tiempo de ejecución de la consulta: " + (System.currentTimeMillis() - startTime));
    }
}
