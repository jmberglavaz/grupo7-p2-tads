package um.edu.uy.adt.heap;

import um.edu.uy.excepciones.EmptyHeapException;
import um.edu.uy.adt.list.MyArrayListImpl;
import um.edu.uy.adt.list.MyList;

public class MyHeapImpl<T extends Comparable<T>> implements MyHeap<T> {
    private final MyList<T> mainArray;
    private final boolean isMin;

    public MyHeapImpl(int size, boolean isMin){
        this.mainArray = new MyArrayListImpl<>(size);
        this.isMin = isMin;
    }

    @Override
    public void insert(T data) {
        this.mainArray.add(data); //Agrega un elemento en el ultimo lugar del heap
        organizeUpHeap(size() - 1); //Organiza el heap para cumplir su condicion (min o max) empezando por el utlimo elemento agregado
    }

    @Override
    public T deleteAndObtain() throws EmptyHeapException {
        if (this.mainArray.isEmpty()){
            throw new EmptyHeapException("Empty Heap");
        }

        T result = this.mainArray.get(0); //Valor de la raiz que se va a devolver
        this.mainArray.intercambiate(0,size()-1); //Intercambia el ultimo elemento del Heap con la raiz
        this.mainArray.delete(size()-1); //Elimina la raiz luego de intercambiarla

        if (this.size()>1){ //Organiza el heap para que cumpla su condicion(min o max) empezando desde la raiz
            organizeDownHeap(0);
        }
        return result;
    }

    @Override
    public int size() {
        return this.mainArray.size();
    }

    @Override
    public String toString(){
        if (this.mainArray.isEmpty()){
            return (this.isMin ? "Min" : "Max") + "Heap";
        }
        StringBuilder result = new StringBuilder();
        result.append(this.isMin ? "Min" : "Max");
        result.append("Heap con: ").append(size()).append(" elementos.").append("\n");
        int ultimoNodo = 0;
        int lastLevel = (int) Math.floor(Math.log(size())/Math.log(2));
        for (int nivel = 0 ; nivel <= lastLevel ; nivel++){
            result.append("Nivel ").append(nivel).append(": ");
            for (int elemento = 0 ; elemento < Math.pow(2,nivel) ; elemento++){
                if (elemento + ultimoNodo >= size()){
                    break;
                }
                result.append("[Dato: ").append(this.mainArray.get(elemento + ultimoNodo));
                if (nivel > 0){
                    result .append(" ; Padre: ").append(this.mainArray.get((int) (elemento + ultimoNodo - 1)/2));
                } else {
                    result.append(" ; Raiz");
                }
                result.append("]");
                }
            if (nivel != lastLevel) {
                result.append("\n");
            }
            ultimoNodo += (int) Math.pow(2,nivel);
        }
        return result.toString();
    }

    private void organizeUpHeap(int index){
        T current = this.mainArray.get(index);
        int parentIndex = (index-1)/2;
        T parent = this.mainArray.get(parentIndex);

        if (compare(parent, current)){
            this.mainArray.intercambiate(index,parentIndex);
            organizeUpHeap(parentIndex);
        }
    }

    private void organizeDownHeap(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int currentIndex = index;

        if (leftChildIndex < size() && compare(mainArray.get(currentIndex), mainArray.get(leftChildIndex))) {
            currentIndex = leftChildIndex;
        }

        if (rightChildIndex < size() && compare(mainArray.get(currentIndex), mainArray.get(rightChildIndex))) {
            currentIndex = rightChildIndex;
        }

        if (currentIndex != index) {
            mainArray.intercambiate(index, currentIndex);
            organizeDownHeap(currentIndex);
        }
    }


    private boolean compare(T dataOne, T dataTwo){
        if (dataOne==null || dataTwo==null){
            throw new IllegalStateException();
        }
        return this.isMin ? dataOne.compareTo(dataTwo) > 0 : dataOne.compareTo(dataTwo) < 0;
    }
}
