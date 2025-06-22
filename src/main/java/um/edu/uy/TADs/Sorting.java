package um.edu.uy.TADs;

import um.edu.uy.TADs.Heap.MyHeapImpl;
import um.edu.uy.TADs.Heap.MyHeap;
import um.edu.uy.TADs.List.MyArrayListImpl;
import um.edu.uy.Exceptions.EmptyListException;
import um.edu.uy.Exceptions.ListOutOfIndex;
import um.edu.uy.TADs.List.MyList;

public class Sorting<T extends Comparable<T>> {

    public MyArrayListImpl<T> bubbleSort(MyArrayListImpl<T> list) throws ListOutOfIndex, EmptyListException {
        for (int extIter = list.size() - 1; extIter > 0; extIter--) {
            for (int interIter = 0; interIter < extIter; interIter++) {
                if (list.get(interIter).compareTo(list.get(interIter + 1)) > 0) {
                    list.intercambiate(interIter, interIter + 1);
                }
            }
        }
        return list;
    }

    public MyArrayListImpl<T> bubbleSortMejorado(MyArrayListImpl<T> lista) throws ListOutOfIndex, EmptyListException {
        boolean huboIntercambio;
        for (int iteracionExterna = lista.size() - 1; iteracionExterna > 0; iteracionExterna--) {
            huboIntercambio = false;
            for (int iteracionInterna = 0; iteracionInterna < iteracionExterna; iteracionInterna++) {
                if (lista.get(iteracionInterna).compareTo(lista.get(iteracionInterna + 1)) > 0) {
                    lista.intercambiate(iteracionInterna, iteracionInterna + 1);
                    huboIntercambio = true;
                }
            }
            // Si no hubo intercambios, la lista ya está ordenada
            if (!huboIntercambio) {
                break;
            }
        }
        return lista;
    }

    public MyArrayListImpl<T> ordenamientoParImpar(MyArrayListImpl<T> lista) throws ListOutOfIndex, EmptyListException {
        int longitudLista = lista.size();

        for (int pasada = 0; pasada < longitudLista; pasada++) {
            for (int indice = 1; indice < longitudLista - 1; indice += 2) {
                if (lista.get(indice).compareTo(lista.get(indice + 1)) > 0) {
                    lista.intercambiate(indice, indice + 1);
                }
            }

            for (int indice = 0; indice < longitudLista - 1; indice += 2) {
                if (lista.get(indice).compareTo(lista.get(indice + 1)) > 0) {
                    lista.intercambiate(indice, indice + 1);
                }
            }
        }
        return lista;
    }

    public MyArrayListImpl<T> selectionSort(MyArrayListImpl<T> list) throws ListOutOfIndex, EmptyListException {
        for (int externalIter = 0; externalIter < list.size(); externalIter++) {
            T tempObject = list.get(externalIter);
            int indexToChange = externalIter;
            for (int internalIter = externalIter; internalIter < list.size(); internalIter++) {
                if (tempObject.compareTo(list.get(internalIter)) > 0) {
                    tempObject = list.get(internalIter);
                    indexToChange = internalIter;
                }
            }
            if (indexToChange != externalIter) {
                list.intercambiate(externalIter, indexToChange);
            }
        }
        return list;
    }

    public MyArrayListImpl<T> insercionSort(MyArrayListImpl<T> list) throws ListOutOfIndex, EmptyListException {
        for (int externalIter = 0; externalIter < list.size(); externalIter++) {
            for (int internalIter = externalIter; internalIter < list.size() - 1; internalIter++) {
                if (list.get(internalIter).compareTo(list.get(internalIter + 1)) > 0) {
                    list.intercambiate(internalIter, internalIter + 1);
                }
            }
        }
        return list;
    }

    public MyArrayListImpl<T> heapSort(MyArrayListImpl<T> list) throws ListOutOfIndex, EmptyListException {
        MyHeap<T> tempHeap = new MyHeapImpl<>(list.size(), true);

        // Insertar todos los elementos en el heap
        for (int i = 0; i < list.size(); i++) {
            tempHeap.insert(list.get(i));
        }

        // Crear una nueva lista para el resultado
        MyArrayListImpl<T> sortedList = new MyArrayListImpl<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            sortedList.add(tempHeap.deleteAndObtain());
        }

        return sortedList;
    }

    public MyArrayListImpl<T> mergeSort(MyArrayListImpl<T> list) throws ListOutOfIndex, EmptyListException {
        if (list.size() < 2) {
            return list;
        }

        int midIndex = list.size() / 2;

        MyArrayListImpl<T> tempList1 = new MyArrayListImpl<>(midIndex);
        MyArrayListImpl<T> tempList2 = new MyArrayListImpl<>(list.size() - midIndex);

        // Dividir la lista
        for (int i = 0; i < midIndex; i++) {
            tempList1.add(list.get(i));
        }
        for (int i = midIndex; i < list.size(); i++) {
            tempList2.add(list.get(i));
        }

        tempList1 = mergeSort(tempList1);
        tempList2 = mergeSort(tempList2);

        return merge(list, tempList1, tempList2);
    }

    private MyArrayListImpl<T> merge(MyArrayListImpl<T> originalList, MyArrayListImpl<T> tempList1, MyArrayListImpl<T> tempList2) throws ListOutOfIndex, EmptyListException {
        MyArrayListImpl<T> result = new MyArrayListImpl<>(originalList.size());
        int i = 0, j = 0;

        while (i < tempList1.size() && j < tempList2.size()) {
            if (tempList1.get(i).compareTo(tempList2.get(j)) <= 0) {
                result.add(tempList1.get(i++));
            } else {
                result.add(tempList2.get(j++));
            }
        }

        while (i < tempList1.size()) {
            result.add(tempList1.get(i++));
        }

        while (j < tempList2.size()) {
            result.add(tempList2.get(j++));
        }

        return result;
    }

    public MyList<T> quickSort(MyList<T> lista) throws ListOutOfIndex, EmptyListException {
        if (lista.size() < 2) {
            return lista;
        }
        quickSortAuxiliar(lista, 0, lista.size() - 1);
        return lista;
    }

    private void quickSortAuxiliar(MyList<T> lista, int indiceBajo, int indiceAlto) throws ListOutOfIndex, EmptyListException {
        if (indiceBajo < indiceAlto) {
            int indicePivote = particion(lista, indiceBajo, indiceAlto);

            quickSortAuxiliar(lista, indiceBajo, indicePivote - 1);
            quickSortAuxiliar(lista, indicePivote + 1, indiceAlto);
        }
    }

    private int particion(MyList<T> lista, int indiceBajo, int indiceAlto) throws ListOutOfIndex, EmptyListException {
        T pivote = lista.get(indiceAlto);
        int indiceMenor = indiceBajo - 1;

        for (int indiceActual = indiceBajo; indiceActual < indiceAlto; indiceActual++) {
            if (lista.get(indiceActual).compareTo(pivote) <= 0) {
                indiceMenor++;
                lista.intercambiate(indiceMenor, indiceActual);
            }
        }

        lista.intercambiate(indiceMenor + 1, indiceAlto);
        return indiceMenor + 1;
    }
}