package um.edu.uy.TADs;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;

public class QuickSort<T extends Comparable<T>> {

    public QuickSort() {
    }

    // Ej 8: QuickSort
    // a. Es el más utilizado porque, cuando se implementa correctamente, es el más rápido para el caso promedio. También ocupa menos espacio que MergeSort, por ejemplo, porque no requiere un array extra.
    public void quickSort(MyLinkedListImpl<T> lista) {
        if (lista != null && lista.size() > 1) {
            quickSort(lista, 0, lista.size() - 1);
        }
    }
    public void quickSort(MyLinkedListImpl<T> lista, int inicio, int fin) {
        if (inicio < fin) {
            int indicePivote = partition(lista, inicio, fin);
            quickSort(lista, inicio, indicePivote - 1);
            quickSort(lista, indicePivote + 1, fin);
        }
    }

    private int partition(MyLinkedListImpl<T> lista, int inicio, int fin) {
        T pivote = lista.get(fin);
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (lista.get(j).compareTo(pivote) <= 0) {
                i++;
                lista.intercambiate(i, j);
            }
        }
        lista.intercambiate(i + 1, fin);
        return i + 1;
    }
}
