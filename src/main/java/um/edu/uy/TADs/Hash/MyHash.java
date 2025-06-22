package um.edu.uy.TADs.Hash;

import um.edu.uy.Exceptions.ElementAlreadyExist;

public interface MyHash<K,T> extends Iterable<T>{
    void insert(K clave, T data) throws ElementAlreadyExist;
    boolean contains(K clave);
    void delete(K clave);
    int size();
    T get(K clave);
    T obtain(int index);
    void changeValue(K clave, T newData);
}
