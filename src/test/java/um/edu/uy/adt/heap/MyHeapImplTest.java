package um.edu.uy.adt.heap;

import org.junit.jupiter.api.Test;
import um.edu.uy.excepciones.EmptyHeapException;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapImplTest {

    @Test
    // Prueba la propiedad de Min-Heap (el menor siempre sale primero)
    void propiedadMinHeap() {
        MyHeap<Integer> minHeap = new MyHeapImpl<>(10, true);
        minHeap.insert(5);
        minHeap.insert(2);
        minHeap.insert(8);
        minHeap.insert(1);
        minHeap.insert(10);
        assertEquals(5, minHeap.size());
        assertEquals(1, minHeap.deleteAndObtain());
        assertEquals(2, minHeap.deleteAndObtain());
        assertEquals(5, minHeap.deleteAndObtain());
        assertEquals(8, minHeap.deleteAndObtain());
        assertEquals(10, minHeap.deleteAndObtain());
        assertEquals(0, minHeap.size());
    }

    @Test
    // Prueba la propiedad de Max-Heap (el mayor siempre sale primero)
    void propiedadMaxHeap() {
        MyHeap<Integer> maxHeap = new MyHeapImpl<>(10, false);
        maxHeap.insert(5);
        maxHeap.insert(2);
        maxHeap.insert(8);
        maxHeap.insert(1);
        maxHeap.insert(10);
        assertEquals(5, maxHeap.size());
        assertEquals(10, maxHeap.deleteAndObtain());
        assertEquals(8, maxHeap.deleteAndObtain());
        assertEquals(5, maxHeap.deleteAndObtain());
        assertEquals(2, maxHeap.deleteAndObtain());
        assertEquals(1, maxHeap.deleteAndObtain());
        assertEquals(0, maxHeap.size());
    }

    @Test
    // Prueba que eliminar de un heap vacío lanza excepción
    void eliminarDeHeapVacio_LanzaExcepcion() {
        MyHeap<Integer> heap = new MyHeapImpl<>(5, true);
        assertEquals(0, heap.size());
        assertThrows(EmptyHeapException.class, heap::deleteAndObtain);
    }

    @Test
    // Prueba la expansión del heap al superar la capacidad inicial
    void expansionDeHeap() {
        MyHeap<Integer> minHeap = new MyHeapImpl<>(2, true);
        minHeap.insert(10);
        minHeap.insert(20);
        minHeap.insert(5);
        minHeap.insert(15);
        assertEquals(4, minHeap.size());
        assertEquals(5, minHeap.deleteAndObtain());
        assertEquals(10, minHeap.deleteAndObtain());
    }
}
