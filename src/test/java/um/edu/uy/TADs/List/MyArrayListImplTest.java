package um.edu.uy.TADs.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.uy.Exceptions.EmptyListException;
import um.edu.uy.Exceptions.ListOutOfIndex;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListImplTest {
    private MyList<String> lista;

    @BeforeEach
    void setUp() {
        lista = new MyArrayListImpl<>(5);
    }

    @Test
    // Prueba agregar elementos y obtenerlos por índice
    void agregarYObtener() {
        lista.add("A");
        lista.add("B");
        assertEquals(2, lista.size());
        assertEquals("A", lista.get(0));
        assertEquals("B", lista.get(1));
    }

    @Test
    // Prueba agregar un elemento en un índice específico
    void agregarEnIndice() {
        lista.add("A");
        lista.add("C");
        lista.add("B", 1);
        assertEquals(3, lista.size());
        assertEquals("A", lista.get(0));
        assertEquals("B", lista.get(1));
        assertEquals("C", lista.get(2));
    }

    @Test
    // Prueba eliminar un elemento por índice
    void eliminarPorIndice() {
        lista.add("A");
        lista.add("B");
        lista.add("C");
        String eliminado = lista.delete(1);
        assertEquals("B", eliminado);
        assertEquals(2, lista.size());
        assertEquals("C", lista.get(1));
    }

    @Test
    // Prueba que obtener un índice fuera de rango lanza excepción
    void obtenerFueraDeRango_LanzaExcepcion() {
        lista.add("A");
        assertThrows(ListOutOfIndex.class, () -> lista.get(1));
        assertThrows(ListOutOfIndex.class, () -> lista.get(-1));
    }

    @Test
    // Prueba que eliminar de una lista vacía lanza excepción
    void eliminarDeListaVacia_LanzaExcepcion() {
        assertTrue(lista.isEmpty());
        assertThrows(EmptyListException.class, () -> lista.delete(0));
    }

    @Test
    // Prueba la expansión de capacidad del array interno
    void expansionDeCapacidad() {
        MyList<Integer> listaEnteros = new MyArrayListImpl<>(2);
        listaEnteros.add(1);
        listaEnteros.add(2);
        listaEnteros.add(3);
        listaEnteros.add(4);
        assertEquals(4, listaEnteros.size());
        assertEquals(3, listaEnteros.get(2));
    }
}
