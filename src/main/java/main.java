import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.entities.UMovie;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws CsvValidationException, IOException {
        UMovie plataforma = new UMovie();
        System .out.println(plataforma.cantPeliculas());
        System.out.println("plataforma creada con exito");
    }
}
