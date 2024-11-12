package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PiattoDaoJDBCTest {


    @Test
    void whenCallFindAll_ReturnAllValues(){

        //given

        //when
        PiattoDao piattoDao = DBManager.getInstance().getPiattoDao();
        List<Piatto> all = piattoDao.findAll();

        assertNotNull(all);

        assertEquals(4, all.size());

        for (Piatto piatto : all) {
            System.out.println(piatto);
        }



    }



}
