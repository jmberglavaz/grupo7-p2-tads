package um.edu.uy.TADs.Hash;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.Exceptions.ValueNoExist; // Asumo que esta excepción existe en tu proyecto

public class MyPrimitiveIntHashImpl implements MyPrimitiveIntHash {

    private int[] keys;
    private int[] values;
    private int size;
    private int capacity;
    private static final double MAX_LOAD_FACTOR = 0.85;

    // Valores equivalentes a tener un deletedNode
    private static final int EMPTY_KEY = 0;
    private static final int DELETED_KEY = -1;

    public MyPrimitiveIntHashImpl(int initialCapacity) {
        this.capacity = findNextPrime(initialCapacity);
        this.keys = new int[this.capacity];
        this.values = new int[this.capacity];
        this.size = 0;
    }

    private int searchIndex(int key) {
        if (key == EMPTY_KEY || key == DELETED_KEY) {
            return -1;
        }
        int index = hash(key);
        int probes = 0;

        while (probes < capacity) {
            if (keys[index] == EMPTY_KEY) {
                return -1;
            }
            if (keys[index] == key) {
                return index;
            }
            index = (index + 1) % capacity;
            probes++;
        }
        return -1;
    }

    @Override
    public void insert(int key, int data) throws ElementAlreadyExist {
        if (key == EMPTY_KEY || key == DELETED_KEY) {
            throw new IllegalArgumentException("La clave no puede ser 0 o -1.");
        }

        if ((double) size / capacity >= MAX_LOAD_FACTOR) {
            rehash();
        }

        int index = hash(key);
        while (keys[index] != EMPTY_KEY && keys[index] != DELETED_KEY) {
            if (keys[index] == key) {
                throw new ElementAlreadyExist("La clave ya existe.");
            }
            index = (index + 1) % capacity;
        }

        keys[index] = key;
        values[index] = data;
        size++;
    }

    @Override
    public boolean contains(int key) {
        return searchIndex(key) != -1;
    }

    @Override
    public void delete(int key) {
        int index = searchIndex(key);
        if (index == -1) {
            throw new ValueNoExist("La clave a eliminar no existe.");
        }
        keys[index] = DELETED_KEY;
        values[index] = 0;
        size--;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int get(int key) {
        int index = searchIndex(key);
        if (index == -1) {
            throw new ValueNoExist("La clave no fue encontrada.");
        }
        return values[index];
    }

    @Override
    public int obtain(int index) {
        if (index < 0 || index >= capacity) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }

        if (keys[index] != EMPTY_KEY && keys[index] != DELETED_KEY) {
            return values[index];
        }
        return 0;
    }

    @Override
    public void changeValue(int key, int newData) {
        int index = searchIndex(key);
        if (index == -1) {
            throw new ValueNoExist("La clave no fue encontrada para cambiar el valor.");
        }
        values[index] = newData;
    }

    @Override
    public void clean() {

        for (int i = 0; i < capacity; i++) {
            keys[i] = EMPTY_KEY;
        }
        this.size = 0;
    }



    private void rehash() {
        int[] oldKeys = this.keys;
        int[] oldValues = this.values;
        int oldCapacity = this.capacity;

        this.capacity = findNextPrime(oldCapacity * 2);
        this.keys = new int[this.capacity];
        this.values = new int[this.capacity];
        this.size = 0;

        for (int i = 0; i < oldCapacity; i++) {
            if (oldKeys[i] != EMPTY_KEY && oldKeys[i] != DELETED_KEY) {
                try {
                    insert(oldKeys[i], oldValues[i]);
                } catch (ElementAlreadyExist e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int hash(int clave) {
        clave ^= (clave >>> 16);
        clave *= 73244091;
        clave ^= (clave >>> 16);
        return Math.abs(clave) % capacity;
    }
    private boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number <= 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;
        for (int i = 5; i * i <= number; i = i + 6) {
            if (number % i == 0 || number % (i + 2) == 0) return false;
        }
        return true;
    }

    private int findNextPrime(int number) {
        if (number <= 1) return 2;
        while (!isPrime(number)) {
            number++;
        }
        return number;
    }
}