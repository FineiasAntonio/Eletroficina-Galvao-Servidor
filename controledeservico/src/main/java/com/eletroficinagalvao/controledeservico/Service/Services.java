package com.eletroficinagalvao.controledeservico.Service;

import java.util.List;

import org.springframework.stereotype.Service;

public interface Services<T> {
    public List<T> getAll();
    public T getById(String id);
    public List<T> getByLikeThisName(String name);
    public void create(T t);
    public void update(String id, T t);
    public void delete(String id);
}
