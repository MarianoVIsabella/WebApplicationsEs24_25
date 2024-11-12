package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;

import java.util.ArrayList;
import java.util.List;

public class PiattoProxy extends Piatto {
    @Override
    public List<Ristorante> getRistoranti() {
        if (this.ristoranti==null){
            this.ristoranti = DBManager.getInstance().getRistoranteDao().findAllByPiattoName(this.nome);
        }
        return this.ristoranti;
    }
}
