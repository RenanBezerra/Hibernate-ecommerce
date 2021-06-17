package com.estudo.hibernate.works.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estudo.hibernate.works.model.Produto;

@Repository
public class Produtos {

    @PersistenceContext
    private EntityManager entityManager;

    public Produto buscar(Integer id) {
        return entityManager.find(Produto.class, id);
    }

    public Produto salvar(Produto produto) {
        return entityManager.merge(produto);
    }

    public List<Produto> listar() {
        return entityManager
                .createQuery("select p from Produto p", Produto.class)
                .getResultList();
    }
}