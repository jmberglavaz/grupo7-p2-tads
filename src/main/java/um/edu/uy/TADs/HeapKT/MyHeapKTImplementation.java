package um.edu.uy.TADs.HeapKT;

import um.edu.uy.Exceptions.EmptyHeapException;
import um.edu.uy.TADs.HeapKT.MyHeapKT;

import java.util.Arrays;

public class MyHeapKTImplementation<K extends Comparable<K>, T> implements MyHeapKT<K, T> {
    private HeapNode<K, T>[] heap;
    private int size;
    private final boolean esMinHeap;

    public MyHeapKTImplementation(int tamanioInicial, boolean esMinHeap) {
        if (tamanioInicial < 1) {
            throw new IllegalArgumentException("El tamaño inicial tiene que ser mayor o igual a 1");
        }
        this.heap = (HeapNode<K, T>[]) new HeapNode[tamanioInicial];
        this.size = 0;
        this.esMinHeap = esMinHeap;
    }

    @Override
    public void insert(K key, T data) {
        if (key == null || data == null) {
            throw new IllegalArgumentException("Key y data no pueden ser nulos");
        }

        if (size == heap.length) {
            expandirCapacidad();
        }

        HeapNode<K, T> nuevoNodo = new HeapNode<>(key, data);
        heap[size] = nuevoNodo;
        size++;
        subirNodo(size - 1);
    }

    @Override
    public HeapNode<K, T> deleteAndObtainNode() throws EmptyHeapException {
        if (isEmpty()) {
            throw new EmptyHeapException("Heap vacío");
        }

        HeapNode<K, T> raiz = heap[0];

        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        if (size > 0) {
            bajarNodo(0);
        }

        return raiz;
    }

    @Override
    public T deleteAndObtain() throws EmptyHeapException {
        if (isEmpty()) {
            throw new EmptyHeapException("Heap vacío");
        }

        T dataRaiz = heap[0].getData();
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        if (size > 0) {
            bajarNodo(0);
        }

        return dataRaiz;
    }

    @Override
    public int size() {
        return size;
    }

    private void subirNodo(int posicion) {
        while (posicion > 0) {
            int posicionPadre = (posicion - 1) / 2;

            if (debeIntercambiar(heap[posicionPadre], heap[posicion])) {
                intercambiarNodos(posicionPadre, posicion);
                posicion = posicionPadre;
            } else {
                break;
            }
        }
    }

    private void bajarNodo(int posicion) {
        while (true) {
            int hijoIzquierdo = 2 * posicion + 1;
            int hijoDerecho = 2 * posicion + 2;
            int candidato = posicion;

            // Comparar con hijo izquierdo
            if (hijoIzquierdo < size && debeIntercambiar(heap[candidato], heap[hijoIzquierdo])) {
                candidato = hijoIzquierdo;
            }

            // Comparar con hijo derecho
            if (hijoDerecho < size && debeIntercambiar(heap[candidato], heap[hijoDerecho])) {
                candidato = hijoDerecho;
            }

            // Si no hay intercambio necesario, terminar
            if (candidato == posicion) {
                break;
            }

            // Intercambiar y continuar
            intercambiarNodos(posicion, candidato);
            posicion = candidato;
        }
    }

    private boolean debeIntercambiar(HeapNode<K, T> padre, HeapNode<K, T> hijo) {
        if (padre == null || hijo == null) {
            throw new IllegalArgumentException("El nodo no puede ser vacío");
        }
        if (esMinHeap) {
            return padre.getKey().compareTo(hijo.getKey()) > 0;
        } else {
            return padre.getKey().compareTo(hijo.getKey()) < 0;
        }
    }

    private void intercambiarNodos(int i, int j) {
        HeapNode<K, T> temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void expandirCapacidad() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Método auxiliar para debugging
    public void mostrarHeap() {
        if (isEmpty()) {
            System.out.println("Heap vacío");
            return;
        }

        System.out.println("Heap (" + (esMinHeap ? "Min" : "Max") + "):");
        for (int i = 0; i < size; i++) {
            System.out.print("[" + heap[i].getKey() + ":" + heap[i].getData() + "] ");
        }
        System.out.println();
    }
}