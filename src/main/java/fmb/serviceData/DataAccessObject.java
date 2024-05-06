package fmb.serviceData;

import java.util.List;

public interface DataAccessObject<T> {
    int getNextID();
    void add(T entity);
    void update(T entity);
    void delete(int id);
    List<T> getAll();
}
