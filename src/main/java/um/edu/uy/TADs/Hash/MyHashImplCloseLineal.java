package um.edu.uy.TADs.Hash;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.Exceptions.ValueNoExist;

import java.util.Iterator;

public class MyHashImplCloseLineal<K,T> implements MyHash<K,T> {
    private HashNode<K,T>[] table;
    private int size;         // Cantidad de elementos insertados
    private int capacity;     // Tamaño actual de la tabla (longitud del array)
    private final HashNode<K,T> deleteNode = new HashNode<>(null, null); //Nodo generico para delete
    private static final double maxFactorDeCarga = 0.85; // Factor de carga máximo

    public MyHashImplCloseLineal(int initialCapacity) {
        this.capacity = findNextPrime(initialCapacity);
        this.table = new HashNode[this.capacity];
        this.size = 0;
    }

    @Override
    public int size(){
        return this.size;
    }

    @Override
    public void insert(K clave, T data) throws ElementAlreadyExist {
        // Verificar si necesitamos rehashing antes de insertar
        if ((double) size / capacity >= maxFactorDeCarga) {
            incrementLength();
        }

        int index = hash(clave);
        int recorrido = 0;

        while ((this.table[index] != null) && recorrido < capacity) {
            if (this.table[index] != deleteNode && this.table[index].getKey().equals(clave)) {
                throw new ElementAlreadyExist("The object already exists");
            }
            index = (index + 1) % capacity;
            recorrido++;
        }

        if (recorrido >= capacity) {
            // Esto no debería pasar con el control de factor de carga
            System.out.println("Error: Tabla llena");
            incrementLength();
            insert(clave, data);
            return;
        }

        this.table[index] = new HashNode<>(data, clave);
        size++;
    }

    @Override
    public boolean contains(K clave) {
        return searchIndex(clave) >= 0;
    }

    @Override
    public void delete(K clave) {
        int index = searchIndex(clave);
        if (index < 0){
            throw new ValueNoExist("This object does not exist");
        }
        this.table[index] = deleteNode;
        size--;
    }

    private void incrementLength() {
        HashNode<K,T>[] oldTable = this.table;
        int oldCapacity = this.capacity;

        // Se duplica el tamaño y se encuentra el siguiente primo
        this.capacity = findNextPrime(this.capacity * 2);

        this.table = new HashNode[this.capacity];
        int oldSize = this.size;
        this.size = 0;

        // Se reinsertan todos los elementos
        for (HashNode<K,T> node : oldTable) {
            if (node != null && node != deleteNode) {
                try {
                    insert(node.getKey(), node.getData());
                } catch (ElementAlreadyExist e) {
                    // Esto no debería pasar durante rehashing
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public T get(K clave) {
        int index = searchIndex(clave);
        if (index < 0){
            return null;
        }
        return table[index].getData();
    }

    @Override
    public T obtain(int index){
        try {
            return table[index].getData();
        } catch (Exception ignored) {
            return null;
        }
    }

    @Override
    public void changeValue(K clave, T newData){
        int index = searchIndex(clave);

        if (index < 0){
            throw new ValueNoExist("The value no exist in the table");
        }

        table[index].setData(newData);
    }

    private boolean isPrime(int number) {
        if (number < 2) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;

        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int findNextPrime(int number) {
        while (!isPrime(number)) {
            number++;
        }
        return number;
    }

    private int searchIndex(K clave) {
        int index = hash(clave);
        int probes = 0;

        while (this.table[index] != null && probes < capacity) {
            if (this.table[index] != deleteNode && this.table[index].getKey().equals(clave)) {
                return index;
            }
            index = (index + 1) % capacity;
            probes++;
        }

        return -1;
    }

    private int hash(K clave) {
        int hash = clave.hashCode();
        hash ^= (hash >>> 16);
        hash *= 73244091;
        hash ^= (hash >>> 16);
        return Math.abs(hash) % capacity;
    }
    // Devolper method

    public void printStats() {
        System.out.println("Hash Table Stats:");
        System.out.println("  Tamano: " + size);
        System.out.println("  Capacidad: " + capacity);
        System.out.println("  Factor de Carga: " + String.format("%.2f", (double) size / capacity));

        int maxProbes = 0;
        int totalProbes = 0;
        int usedSlots = 0;

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && table[i] != deleteNode) {
                usedSlots++;
                int probes = calculateProbes(table[i].getKey());
                totalProbes += probes;
                maxProbes = Math.max(maxProbes, probes);
            }
        }

        if (usedSlots > 0) {
            System.out.println("  Average probes: " + String.format("%.2f", (double) totalProbes / usedSlots));
            System.out.println("  Max probes: " + maxProbes);
        }
        System.out.println("\n");
    }


    private int calculateProbes(K key) {
        int index = hash(key);
        int probes = 1;

        while (table[index] != null && !table[index].getKey().equals(key)) {
            index = (index + 1) % capacity;
            probes++;
        }

        return probes;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyHashIterator<>(table, deleteNode);
    }
}