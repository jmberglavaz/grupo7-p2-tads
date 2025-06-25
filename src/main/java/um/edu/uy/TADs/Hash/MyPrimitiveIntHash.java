package um.edu.uy.TADs.Hash;

import um.edu.uy.Exceptions.ElementAlreadyExist;

public interface MyPrimitiveIntHash {
    void insert(int clave, int data) throws ElementAlreadyExist;
    boolean contains(int clave);
    void delete(int clave);
    int size();
    int get(int clave);
    int obtain(int index);
    void changeValue(int clave, int newData);
    void clean();
}
