package com.dipartimento.demowebapplications.persistence;

import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;
import com.dipartimento.demowebapplications.persistence.dao.impljdbc.PiattoDaoJDBC;
import com.dipartimento.demowebapplications.persistence.dao.impljdbc.RistoranteDaoJDBC;

import java.sql.*;

public class DBManager {
    private static DBManager instance = null;

    private DBManager(){}
    private RistoranteDao ristoranteDao = null;
    private PiattoDao piattoDao = null;

    public static DBManager getInstance(){
        if (instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    Connection con = null;

    public Connection getConnection(){
        if (con == null){
            try {
                //Qui inserita la connessione con il mio database locale
                con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ristorantidao", "postgres", "redgold");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        //System.out.println("Connessione eseguita correttamente!");
        return con;
    }
/*
    public PiattoDao getPiattoDao(){
        return new PiattoDaoPostgres(getConnection());
    }

    public UtenteDao getUtenteDao(){
        return new UtenteDaoPostgres(getConnection());
    }
*/
    public RistoranteDao getRistoranteDao(){
        if (ristoranteDao == null) {
            ristoranteDao = new RistoranteDaoJDBC(getConnection());
        }
        return  ristoranteDao;
    }

    public PiattoDao getPiattoDao(){
        if (piattoDao == null) {
            piattoDao = new PiattoDaoJDBC(getConnection());
        }
        return  piattoDao;
    }




    public static void main(String[] args) {
        Connection con = DBManager.getInstance().getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from ristorante");
            if (rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
