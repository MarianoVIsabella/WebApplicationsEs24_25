package com.dipartimento.demowebapplications.persistence.dao;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;

import java.util.List;

public interface PiattoDao {


    public List<Piatto> findAll();

    public Piatto findByPrimaryKey(String nome);

    public void save(Piatto piatto);

    public void delete(Piatto piatto);

    List<Piatto> findAllByRistoranteName(String name);




}
