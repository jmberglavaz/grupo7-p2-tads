package um.edu.uy.sistema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.uy.adt.hash.MyHash;
import um.edu.uy.adt.hash.MyHashImplCloseLineal;
import um.edu.uy.entidades.*;
import um.edu.uy.sistema.consultas.TopPeliculasPorIdioma;

import java.util.Date;

class TopPeliculasPorIdiomaTest {
    MyHash<String, Idioma> languageHash;
    Idioma es;
    Idioma en;
    Idioma fr;
    Idioma it;
    Idioma pt;
    
    Pelicula pelicula1; Pelicula pelicula2; Pelicula pelicula3; Pelicula pelicula4; Pelicula pelicula5; Pelicula pelicula6;
    Pelicula pelicula7; Pelicula pelicula8; Pelicula pelicula9; Pelicula pelicula10; Pelicula pelicula11; Pelicula pelicula12;
    Pelicula pelicula13; Pelicula pelicula14; Pelicula pelicula15; Pelicula pelicula16; Pelicula pelicula17; Pelicula pelicula18;
    Pelicula pelicula19; Pelicula pelicula20; Pelicula pelicula21; Pelicula pelicula22; Pelicula pelicula23; Pelicula pelicula24;
    Pelicula pelicula25; Pelicula pelicula26; Pelicula pelicula27; Pelicula pelicula28; Pelicula pelicula29; Pelicula pelicula30;

    Evaluacion evaluacion1; Evaluacion evaluacion2; Evaluacion evaluacion3; Evaluacion evaluacion4; Evaluacion evaluacion5;
    Evaluacion evaluacion6; Evaluacion evaluacion7; Evaluacion evaluacion8; Evaluacion evaluacion9; Evaluacion evaluacion10;
    Evaluacion evaluacion11; Evaluacion evaluacion12; Evaluacion evaluacion13; Evaluacion evaluacion14; Evaluacion evaluacion15;
    Evaluacion evaluacion16; Evaluacion evaluacion17; Evaluacion evaluacion18; Evaluacion evaluacion19; Evaluacion evaluacion20;
    Evaluacion evaluacion21; Evaluacion evaluacion22; Evaluacion evaluacion23; Evaluacion evaluacion24; Evaluacion evaluacion25;
    Evaluacion evaluacion26; Evaluacion evaluacion27; Evaluacion evaluacion28; Evaluacion evaluacion29; Evaluacion evaluacion30;
    Evaluacion evaluacion31; Evaluacion evaluacion32; Evaluacion evaluacion33; Evaluacion evaluacion34; Evaluacion evaluacion35;
    Evaluacion evaluacion36; Evaluacion evaluacion37; Evaluacion evaluacion38; Evaluacion evaluacion39; Evaluacion evaluacion40;

    @BeforeEach
    void setUp() {
        es = new Idioma("es");
        en = new Idioma("en");
        fr = new Idioma("fr");
        it = new Idioma("it");
        pt = new Idioma("pt");

        pelicula1 = new Pelicula(1, "Fuego en el Horizonte 1", "2022-08-22", 1839725903L);
        pelicula2 = new Pelicula(2, "Luces de la Ciudad 2", "2017-08-15", 1642545662L);
        pelicula3 = new Pelicula(3, "Sombras del Pasado 3", "2011-07-11", 1417832793L);
        pelicula4 = new Pelicula(4, "El Secreto de Luna 4", "2015-09-30", 1820927531L);
        pelicula5 = new Pelicula(5, "Corazones Perdidos 5", "2015-06-30", 861781966L);
        pelicula6 = new Pelicula(6, "El Secreto de Luna 6", "2024-03-01", 1745872276L);
        pelicula7 = new Pelicula(7, "Caminos Cruzados 7", "2012-01-01", 1971815747L);
        pelicula8 = new Pelicula(8, "El Laberinto Dorado 8", "2021-02-08", 939479212L);
        pelicula9 = new Pelicula(9, "Caminos Cruzados 9", "2019-05-10", 1239412648L);
        pelicula10 = new Pelicula(10, "La Montaña Sagrada 10", "2008-02-07", 1619279672L);
        pelicula11 = new Pelicula(11, "El Secreto de Luna 11", "2022-08-24", 78482773L);
        pelicula12 = new Pelicula(12, "El Laberinto Dorado 12", "2011-08-11", 1049712050L);
        pelicula13 = new Pelicula(13, "Caminos Cruzados 13", "2013-07-30", 1507797828L);
        pelicula14 = new Pelicula(14, "La Montaña Sagrada 14", "2013-03-13", 283543989L);
        pelicula15 = new Pelicula(15, "Sombras del Pasado 15", "2005-01-20", 825657833L);
        pelicula16 = new Pelicula(16, "Fuego en el Horizonte 16", "2012-09-01", 1545906386L);
        pelicula17 = new Pelicula(17, "El Laberinto Dorado 17", "2018-01-30", 982656208L);
        pelicula18 = new Pelicula(18, "Huellas en la Arena 18", "2005-02-05", 181809330L);
        pelicula19 = new Pelicula(19, "La Montaña Sagrada 19", "2006-11-09", 108930427L);
        pelicula20 = new Pelicula(20, "Huellas en la Arena 20", "2005-11-24", 1366894311L);
        pelicula21 = new Pelicula(21, "Danza de Sombras 21", "2018-12-27", 719831560L);
        pelicula22 = new Pelicula(22, "Huellas en la Arena 22", "2011-11-17", 136667159L);
        pelicula23 = new Pelicula(23, "Luces de la Ciudad 23", "2005-03-21", 1384485251L);
        pelicula24 = new Pelicula(24, "La Última Esperanza 24", "2005-01-22", 248432322L);
        pelicula25 = new Pelicula(25, "El Eco del Silencio 25", "2018-04-05", 1264793391L);
        pelicula26 = new Pelicula(26, "La Fortaleza Olvidada 26", "2023-11-23", 376292772L);
        pelicula27 =  new Pelicula(27, "El Laberinto Dorado 27", "2004-08-17", 636051310L);
        pelicula28 = new Pelicula(28, "El Espejo Roto 28", "2009-04-09", 608202852L);
        pelicula29 = new Pelicula(29, "La Fortaleza Olvidada 29", "2009-11-02", 570601893L);
        pelicula30 = new Pelicula(30, "El Río de los Sueños 30", "2006-11-08", 1524923232L);

        pelicula1.addReview(new Evaluacion(89, 9.7f, new Date(2024, 3, 4)));
        pelicula1.addReview(new Evaluacion(100, 3.2f, new Date(2021, 7, 3)));
        pelicula1.addReview(new Evaluacion(79, 8.9f, new Date(2021, 3, 27)));
        pelicula1.addReview(new Evaluacion(56, 1.5f, new Date(2021, 0, 23)));
        pelicula1.addReview(new Evaluacion(168, 9.8f, new Date(2024, 3, 4)));
        pelicula2.addReview(new Evaluacion(39, 6.9f, new Date(2024, 7, 1)));
        pelicula2.addReview(new Evaluacion(32, 4.3f, new Date(2021, 8, 20)));
        pelicula2.addReview(new Evaluacion(90, 8.4f, new Date(2020, 7, 28)));
        pelicula2.addReview(new Evaluacion(61, 8.3f, new Date(2020, 0, 24)));
        pelicula3.addReview(new Evaluacion(54, 3.3f, new Date(2024, 4, 3)));
        pelicula3.addReview(new Evaluacion(100, 4.5f, new Date(2020, 3, 7)));
        pelicula3.addReview(new Evaluacion(38, 5.0f, new Date(2020, 2, 4)));
        pelicula4.addReview(new Evaluacion(3, 8.2f, new Date(2024, 6, 9)));
        pelicula4.addReview(new Evaluacion(89, 6.2f, new Date(2023, 10, 15)));
        pelicula5.addReview(new Evaluacion(92, 8.8f, new Date(2021, 4, 27)));
        pelicula5.addReview(new Evaluacion(12, 2.4f, new Date(2020, 2, 18)));
        pelicula5.addReview(new Evaluacion(98, 8.7f, new Date(2021, 0, 9)));
        pelicula5.addReview(new Evaluacion(70, 8.2f, new Date(2021, 9, 25)));
        pelicula5.addReview(new Evaluacion(76, 4.1f, new Date(2020, 10, 28)));
        pelicula5.addReview(new Evaluacion(79, 6.0f, new Date(2020, 8, 17)));
        pelicula6.addReview(new Evaluacion(34, 3.5f, new Date(2021, 0, 4)));
        pelicula6.addReview(new Evaluacion(89, 6.0f, new Date(2021, 9, 25)));

        en.addMovie(pelicula1);
        en.addMovie(pelicula2);
        en.addMovie(pelicula3);
        en.addMovie(pelicula4);
        en.addMovie(pelicula5);

        es.addMovie(pelicula2);
        es.addMovie(pelicula3);
        es.addMovie(pelicula4);
        es.addMovie(pelicula5);
        es.addMovie(pelicula6);

        fr.addMovie(pelicula1);
        fr.addMovie(pelicula2);
        fr.addMovie(pelicula3);
        fr.addMovie(pelicula4);
        fr.addMovie(pelicula5);
        fr.addMovie(pelicula6);

        languageHash = new MyHashImplCloseLineal<>(100);
        languageHash.insert(en.getAcronym(), en);
        languageHash.insert(es.getAcronym(), es);
        languageHash.insert(fr.getAcronym(), fr);
        languageHash.insert(it.getAcronym(), it);
        languageHash.insert(pt.getAcronym(), pt);
    }

    @Test
    void realizarConsulta() {
        TopPeliculasPorIdioma.realizarConsulta(languageHash);
    }
}