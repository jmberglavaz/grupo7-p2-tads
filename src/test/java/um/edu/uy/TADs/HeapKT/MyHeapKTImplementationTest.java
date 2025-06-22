package um.edu.uy.TADs.HeapKT;

import org.junit.jupiter.api.Test;
import um.edu.uy.Exceptions.EmptyHeapException;
import static org.junit.jupiter.api.Assertions.*;

class MyHeapKTImplementationTest {

    @Test
    // Prueba la propiedad de Min-Heap con clave-valor
    void propiedadMinHeap() {
        MyHeapKT<Integer, String> minHeap = new MyHeapKTImplementation<>(10, true);
        minHeap.insert(5, "DataFor5");
        minHeap.insert(2, "DataFor2");
        minHeap.insert(8, "DataFor8");
        assertEquals(3, minHeap.size());
        assertEquals("DataFor2", minHeap.deleteAndObtain());
        assertEquals("DataFor5", minHeap.deleteAndObtain());
        assertEquals("DataFor8", minHeap.deleteAndObtain());
    }

    @Test
    // Prueba la propiedad de Max-Heap con clave-valor
    void propiedadMaxHeap() {
        MyHeapKT<Integer, String> maxHeap = new MyHeapKTImplementation<>(10, false);
        maxHeap.insert(5, "DataFor5");
        maxHeap.insert(2, "DataFor2");
        maxHeap.insert(8, "DataFor8");
        assertEquals(3, maxHeap.size());
        assertEquals("DataFor8", maxHeap.deleteAndObtain());
        assertEquals("DataFor5", maxHeap.deleteAndObtain());
        assertEquals("DataFor2", maxHeap.deleteAndObtain());
    }

    @Test
    // Prueba que eliminar de un heap vacío lanza excepción
    void eliminarDeHeapVacio_LanzaExcepcion() {
        MyHeapKT<Integer, String> heap = new MyHeapKTImplementation<>(5, true);
        assertEquals(0, heap.size());
        assertThrows(EmptyHeapException.class, heap::deleteAndObtain);
    }

    @Test
    // Prueba la expansión del heap al superar la capacidad inicial
    void expansionDeHeap() {
        MyHeapKT<Integer, String> heap = new MyHeapKTImplementation<>(2, false);
        heap.insert(10, "A");
        heap.insert(20, "B");
        heap.insert(5, "C");
        assertEquals(3, heap.size());
        assertEquals("B", heap.deleteAndObtain());
        assertEquals("A", heap.deleteAndObtain());
    }
}
