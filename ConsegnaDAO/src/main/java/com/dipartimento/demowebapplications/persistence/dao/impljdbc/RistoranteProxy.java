package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public class RistoranteProxy extends Ristorante {


    public List<Piatto> getPiatti() {
        if(this.piatti==null){
            this.piatti= DBManager.getInstance().getPiattoDao().findAllByRistoranteName(this.nome);
        }
        return piatti;
    }

}
