package um.edu.uy.TADs.Hash;

import org.junit.jupiter.api.Test;
import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.Exceptions.ValueNoExist;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MyHashImplCloseLinealTest {

    @Test
    // Verifica que se puedan insertar y obtener elementos correctamente
    void testInsertAndGet() {
        MyHash<String, String> hash = new MyHashImplCloseLineal<>(10);
        
        hash.insert("key1", "value1");
        hash.insert("key2", "value2");

        assertEquals(2, hash.size());
        assertEquals("value1", hash.get("key1"));
        assertEquals("value2", hash.get("key2"));
    }

    @Test
    // Verifica que insertar una clave existente lanza una excepción
    void testInsert_ExistingKey_ThrowsException() {
        MyHash<String, String> hash = new MyHashImplCloseLineal<>(10);
        hash.insert("key1", "value1");

        assertThrows(ElementAlreadyExist.class, () -> {
            hash.insert("key1", "anotherValue");
        });
    }

    @Test
    // Verifica que obtener una clave inexistente retorna null
    void testGet_NonExistentKey_ReturnsNull() {
        MyHash<String, String> hash = new MyHashImplCloseLineal<>(10);
        hash.insert("key1", "value1");
        
        String value = hash.get("nonExistentKey");

        assertNull(value);
    }

    @Test
    // Verifica que se pueda eliminar un elemento y que realmente desaparezca del hash
    void testDelete() {
        MyHash<String, String> hash = new MyHashImplCloseLineal<>(10);
        hash.insert("key1", "value1");
        assertEquals(1, hash.size());
        
        hash.delete("key1");
        
        assertEquals(0, hash.size());
        assertFalse(hash.contains("key1"));
        assertNull(hash.get("key1"));
    }

    @Test
    // Verifica que eliminar una clave inexistente lanza una excepción
    void testDelete_NonExistentKey_ThrowsException() {
        MyHash<String, String> hash = new MyHashImplCloseLineal<>(10);
        
        assertThrows(ValueNoExist.class, () -> {
            hash.delete("nonExistentKey");
        });
    }

    @Test
    // Verifica que el rehashing funciona correctamente al insertar varios elementos
    void testRehashing() {
        MyHash<Integer, String> hash = new MyHashImplCloseLineal<>(5);
        hash.insert(1, "A");
        hash.insert(2, "B");
        hash.insert(3, "C");
        hash.insert(4, "D");
        
        hash.insert(5, "E");
        
        assertEquals(5, hash.size());
        assertEquals("A", hash.get(1));
        assertEquals("B", hash.get(2));
        assertEquals("C", hash.get(3));
        assertEquals("D", hash.get(4));
        assertEquals("E", hash.get(5));
    }

    @Test
    // Verifica que el iterador recorra correctamente los elementos del hash
    void testIterator() {
        MyHash<String, Integer> hash = new MyHashImplCloseLineal<>(10);
        hash.insert("uno", 1);
        hash.insert("dos", 2);
        hash.insert("tres", 3);
        hash.delete("dos");
        
        Iterator<Integer> iterator = hash.iterator();
        int count = 0;
        boolean foundOne = false;
        boolean foundThree = false;

        while(iterator.hasNext()) {
            Integer val = iterator.next();
            if (val == 1) foundOne = true;
            if (val == 3) foundThree = true;
            count++;
        }
        
        assertEquals(2, hash.size());
        assertEquals(2, count);
        assertTrue(foundOne);
        assertTrue(foundThree);
    }
}