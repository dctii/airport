package com.solvd.airport.persistence;

public interface AbstractDAO<T> {
    int create(T t);

    T getById(int id);

    void update(T t);

    void delete(int id);

}
