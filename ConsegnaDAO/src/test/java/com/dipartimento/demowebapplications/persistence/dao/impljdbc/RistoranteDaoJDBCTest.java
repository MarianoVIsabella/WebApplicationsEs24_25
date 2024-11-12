package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RistoranteDaoJDBCTest {


    @Test
    void whenFindAll_thenRetrieveAll(){

        RistoranteDao ristoranteDao = DBManager.getInstance().getRistoranteDao();

        List<Ristorante> all = ristoranteDao.findAll();

        assertNotNull(all);

        assertEquals(3,all.size());

        for (Ristorante ristorante : all) {

            System.out.println(ristorante);

        }


    }





    @Test
    void whenTryToSaveANewRistorante_Then_saveItCorrectly(){

        Ristorante r= new Ristorante();
        r.setNome("Ristorante5");
        r.setDescrizione("Desc5");
        r.setUbicazione("Ub5");

        r.setPiatti( Arrays.asList(
                new Piatto("PIATTO10", "ing10",null),
                new Piatto("PIATTO11", "ing11",null)

        ));


        DBManager.getInstance().getRistoranteDao().save(r);



    }




}
