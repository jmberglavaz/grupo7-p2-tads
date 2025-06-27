package um.edu.uy.adt.list.linked;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.uy.excepciones.EmptyListException;
import um.edu.uy.excepciones.ListOutOfIndexException;
import um.edu.uy.adt.list.MyList;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListImplTest {

    private MyList<Integer> lista;

    @BeforeEach
    void setUp() {
        lista = new MyLinkedListImpl<>();
    }

    @Test
    // Prueba agregar elementos y obtenerlos por índice
    void agregarYObtener() {
        lista.add(10);
        lista.add(20);
        assertEquals(2, lista.size());
        assertEquals(10, lista.get(0));
        assertEquals(20, lista.get(1));
    }

    @Test
    // Prueba agregar un elemento al inicio de la lista
    void agregarAlInicio() {
        lista.add(10);
        lista.addFirst(5);
        assertEquals(2, lista.size());
        assertEquals(5, lista.get(0));
        assertEquals(10, lista.get(1));
    }

    @Test
    // Prueba eliminar el primer elemento de la lista
    void eliminarPrimero() {
        lista.add(10);
        lista.add(20);
        Integer eliminado = lista.deleteFirst();
        assertEquals(10, eliminado);
        assertEquals(1, lista.size());
        assertEquals(20, lista.get(0));
    }

    @Test
    // Prueba eliminar el último elemento de la lista
    void eliminarUltimo() {
        lista.add(10);
        lista.add(20);
        lista.add(30);
        Integer eliminado = lista.deleteLast();
        assertEquals(30, eliminado);
        assertEquals(2, lista.size());
        assertThrows(ListOutOfIndexException.class, () -> lista.get(2));
    }

    @Test
    // Prueba que eliminar de una lista vacía lanza excepción
    void eliminarDeListaVacia_LanzaExcepcion() {
        assertTrue(lista.isEmpty());
        assertThrows(EmptyListException.class, () -> lista.delete(0));
    }

    @Test
    // Prueba si la lista contiene ciertos elementos
    void contieneElemento() {
        lista.add(10);
        lista.add(20);
        assertTrue(lista.contains(10));
        assertTrue(lista.contains(20));
        assertFalse(lista.contains(30));
    }
}
