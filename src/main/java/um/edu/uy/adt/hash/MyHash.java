package um.edu.uy.adt.hash;

import um.edu.uy.excepciones.ElementAlreadyExists;
import um.edu.uy.adt.list.MyList;

public interface MyHash<K,T> extends Iterable<T>{
    void insert(K clave, T data) throws ElementAlreadyExists;
    boolean contains(K clave);
    void delete(K clave);
    int size();
    T get(K clave);
    T obtain(int index);
    void changeValue(K clave, T newData);
    void clean();
    MyList<T> getValues();
}
