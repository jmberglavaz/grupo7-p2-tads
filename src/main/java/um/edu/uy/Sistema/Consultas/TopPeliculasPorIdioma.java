package um.edu.uy.Sistema.Consultas;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Heap.MyHeap;
import um.edu.uy.TADs.Heap.MyHeapImpl;
import um.edu.uy.entities.Idioma;
import um.edu.uy.entities.Pelicula;

/**
 * Consulta que muestra el top 5 de películas con más calificaciones por idioma.
 * Solo considera los idiomas especificados en LanguageAconyms.
 */
public class TopPeliculasPorIdioma {
    // Lista de acrónimos de idiomas a considerar
    private static final String[] LanguageAconyms = {"en", "fr", "it", "es", "pt"};

    /**
     * Ejecuta la consulta y muestra las películas top por idioma según cantidad de calificaciones.
     * @param languageHash Hash con todos los idiomas del sistema.
     */
    public static void realizarConsulta(MyHash<String, Idioma> languageHash) {
        long startTime = System.currentTimeMillis();

        // Para cada idioma, busca las 5 películas con más calificaciones
        for (int i = 0; i < LanguageAconyms.length; i++) {
            String languageAcronym = LanguageAconyms[i];
            Idioma currentLanguage = languageHash.get(languageAcronym);

            if (currentLanguage == null || currentLanguage.getMovieList().isEmpty()) continue;

            MyHeap<Pelicula> movieHeap = new MyHeapImpl<>(1000, false);
            // Inserta en el heap solo las películas con al menos una calificación
            for (Pelicula currentMovie : currentLanguage.getMovieList()) {
                if (currentMovie.getTotalReviewCount() > 0) movieHeap.insert(currentMovie);
            }

            int count = 0;
            // Imprime las 5 mejores películas por idioma
            while (movieHeap.size() > 0 && count < 5) {
                Pelicula topMovie = movieHeap.deleteAndObtain();
                System.out.println(topMovie.getId() + ", " + topMovie.getTitle() + "," + topMovie.getTotalReviewCount() + ", " + languageAcronym);
                count++;
            }
        }
        System.out.println("Tiempo de ejecucion de la consulta: " + (System.currentTimeMillis() - startTime));
    }
}
