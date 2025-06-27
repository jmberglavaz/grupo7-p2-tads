package um.edu.uy.adt.hash;

import um.edu.uy.excepciones.ElementAlreadyExists;

public interface MyPrimitiveIntHash {
    void insert(int clave, int data) throws ElementAlreadyExists;
    boolean contains(int clave);
    void delete(int clave);
    int size();
    int get(int clave);
    int obtain(int index);
    void changeValue(int clave, int newData);
    void clean();
}
