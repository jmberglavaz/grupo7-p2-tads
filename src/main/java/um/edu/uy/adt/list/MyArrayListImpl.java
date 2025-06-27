package um.edu.uy.adt.list;

import um.edu.uy.excepciones.EmptyListException;
import um.edu.uy.excepciones.ListOutOfIndexException;
import um.edu.uy.excepciones.NonExistentValueException;

import java.util.Iterator;


public class MyArrayListImpl<T> implements MyList<T>{
    private T[] mainArray;
    private int indexLastValue;

    public boolean isEmpty(){
        return this.indexLastValue == 0;
    }

    public MyArrayListImpl(){
        this(10);
    }

    public MyArrayListImpl(int size) {
        this.mainArray = (T[]) new Object[size];
        this.indexLastValue = 0;
    }

    @Override
    public void add(T data) {
        if (this.indexLastValue >= this.mainArray.length){
            incrementLenght();
        }
        this.mainArray[indexLastValue]=data;
        indexLastValue++;
    }

    @Override
    public void add(T data, int index) throws ListOutOfIndexException {
        if (index > indexLastValue){
            throw new ListOutOfIndexException("Maximo lugar " + this.indexLastValue);
        } else {
            if (this.indexLastValue >= this.mainArray.length){
                incrementLenght();
            }
            if (index==0){
                addFirst(data);
            }else if(index==this.indexLastValue){
                this.mainArray[this.indexLastValue] = data;
                this.indexLastValue++;
            }else{
                moveToRight(index);
                this.mainArray[index] = data;
                this.indexLastValue++;
            }
        }
    }

    public void addFirst(T data){
        moveToRight(0);
        this.indexLastValue++;
        this.mainArray[0]=data;
    }

    @Override
    public T delete(int index) throws ListOutOfIndexException, EmptyListException {
        if (isEmpty()){
            throw new EmptyListException("La lista no contiene elementos.");
        }
        if (index >= this.indexLastValue){
            throw new ListOutOfIndexException("Last place " + this.indexLastValue);
        } else if (index == this.indexLastValue-1){
            return deleteLast();
        } else if (index == 0){
            return deleteFirst();
        }
        T eliminatedObject = this.mainArray[index];
        moveToLeft(index);
        this.indexLastValue--;
        return eliminatedObject;
    }

    public T deleteLast() throws EmptyListException {
        if (isEmpty()){
            throw new EmptyListException("Empty list, cannot remove an object");
        }
        T toReturn = this.mainArray[indexLastValue - 1];
        this.mainArray[indexLastValue - 1] = null;
        this.indexLastValue--;
        return toReturn;
    }

    public T deleteFirst() throws EmptyListException {
        if (isEmpty()){
            throw new EmptyListException("Empty list, cannot remove an object");
        }
        T result = get(0);
        moveToLeft(0);
        this.indexLastValue--;
        return result;

    }

    public void deleteValue(T data) throws EmptyListException, ListOutOfIndexException, NonExistentValueException {
        for (int indice = 0; indice < this.indexLastValue ; indice++){
            if(data.equals(this.mainArray[indice])){
                try{
                    delete(indice);
                    return;
                } catch (ListOutOfIndexException e) {
                    throw new ListOutOfIndexException("e");
                } catch (EmptyListException e){
                    throw new EmptyListException("e");
                }
            }
        }
        throw new NonExistentValueException("El valor no existe.");
    }

    @Override
    public int size() {
        return this.indexLastValue;
    }

    public boolean contains(T data) {
        for (T current : this.mainArray){
            if (data.equals(current)){
                return true;
            } else if(current==null){
                break;
            }
        }
        return false;
    }

    public T get(int index) throws ListOutOfIndexException, EmptyListException{
        if (index>=this.indexLastValue || index<0){
            throw new ListOutOfIndexException("Index no valido");
        }
        if (isEmpty()){
            throw new EmptyListException("Empty array");
        }
        return this.mainArray[index];
    }

    public void intercambiate(int indexOne, int indexTwo) throws EmptyListException, ListOutOfIndexException {
        if (indexOne>=this.indexLastValue){
            throw new ListOutOfIndexException("Index no valido");
        }

        T secondData;
        try {
            secondData = get(indexTwo);
        } catch (ListOutOfIndexException e){
            throw new ListOutOfIndexException("Index no valido");
        } catch (EmptyListException e){
            throw new EmptyListException("Empty Array");
        }

        this.mainArray[indexTwo]=this.mainArray[indexOne];
        this.mainArray[indexOne] = secondData;
    }

    private void incrementLenght(){
        T[] newArray = (T[]) new Object[(this.mainArray.length) * 2];
        System.arraycopy(this.mainArray, 0, newArray, 0, this.mainArray.length);
        this.mainArray = newArray;
    }

    private void moveToLeft(int initialPosition) {
        if (indexLastValue - 1 - initialPosition >= 0)
            System.arraycopy(mainArray, initialPosition + 1, mainArray, initialPosition, indexLastValue - 1 - initialPosition);
        mainArray[indexLastValue - 1] = null;
    }

    private void moveToRight(int positionIndex) {
        if (this.indexLastValue == this.mainArray.length){
            incrementLenght();
        }
        if (indexLastValue - positionIndex >= 0)
            System.arraycopy(mainArray, positionIndex, mainArray, positionIndex + 1, indexLastValue - positionIndex);
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator<>(mainArray, size());
    }
}
