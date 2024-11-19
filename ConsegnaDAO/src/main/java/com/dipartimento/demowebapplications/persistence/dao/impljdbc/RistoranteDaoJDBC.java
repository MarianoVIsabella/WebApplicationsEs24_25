package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RistoranteDaoJDBC implements RistoranteDao {
    Connection connection = null;


    public RistoranteDaoJDBC(Connection conn){

        this.connection = conn;
    }


    @Override
    public List<Ristorante> findAll() {
        List<Ristorante> ristoranti = new ArrayList<Ristorante>();
        String query = "select * from ristorante";

        System.out.println("going to execute:"+query);

        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Ristorante rist = new RistoranteProxy();
                rist.setNome(rs.getString("nome"));
                rist.setDescrizione(rs.getString("descrizione"));
                rist.setUbicazione(rs.getString("ubicazione"));
                ristoranti.add(rist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ristoranti;
    }


    @Override
    public Ristorante findByPrimaryKey(String nome) {


        String query = "SELECT nome, descrizione, ubicazione FROM ristorante WHERE nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String descrizione = resultSet.getString("descrizione");
                String ubicazione = resultSet.getString("ubicazione");
                RistoranteProxy rist = new RistoranteProxy();
                rist.setNome(nome);
                rist.setDescrizione(descrizione);
                rist.setUbicazione(ubicazione);
                return  rist;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public void save(Ristorante ristorante) {
        String query = "INSERT INTO ristorante (nome, descrizione, ubicazione) VALUES (?, ?, ?) " +
                "ON CONFLICT (nome) DO UPDATE SET " +
                "   descrizione = EXCLUDED.descrizione , "+
                "   ubicazione = EXCLUDED.ubicazione ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ristorante.getNome());
            statement.setString(2, ristorante.getDescrizione());
            statement.setString(3, ristorante.getUbicazione());
            statement.executeUpdate();

            List<Piatto> piatti = ristorante.getPiatti();
            if(piatti==null || piatti.isEmpty()){
                //Se entro qui ho appena creato questa riga nel database, SAVE, non faccio altro.
                return;
            }
            for (Piatto tempP: piatti){
                //per ogni piatto associato al ristorante, mi assicuro che questa relazione esista nel join
                insertJoinRistorantePiatto(connection,ristorante.getNome(),tempP.getNome());
            }
            /* CODICE UPDATE VECCHIO
            // reset all relation present in the join table
            restRelationsPResentInTheJoinTable(connection , ristorante.getNome());

            PiattoDao pd = DBManager.getInstance().getPiattoDao();

            for (Piatto tempP : piatti) {
                pd.save(tempP);
                insertJoinRistorantePiatto(connection , ristorante.getNome() , tempP.getNome());
            }
             */



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void restRelationsPResentInTheJoinTable(Connection connection, String nomeRistorante) throws Exception {

        String query="Delete FROM ristorante_piatto WHERE ristorante_nome= ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomeRistorante);


        preparedStatement.execute();

    }

    private void insertJoinRistorantePiatto(Connection connection , String nomeRistorante, String nomePiatto) throws SQLException {

        String query="INSERT INTO ristorante_piatto (ristorante_nome,piatto_nome) VALUES (? , ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomeRistorante);
        preparedStatement.setString(2, nomePiatto);

        preparedStatement.execute();

    }


    @Override
    public void delete(Ristorante ristorante) {
        Ristorante check=findByPrimaryKey(ristorante.getNome());
        if (check == null){
            //se entro qui non esiste il ristorante nel db e dunque non devo eliminare niente
            return;
        }
        List<Piatto> piatti = ristorante.getPiatti(); //prendo tutti i piatti serviti dal ristorante che sto per eliminare
        for (Piatto tempP : piatti) {
            if (tempP.getRistoranti() != null){
                tempP.getRistoranti().remove(ristorante); //rimuovo il ristorante appena cancellato dalla lista se presente
            }
        }
        try {
            //cancella le tuple nella relazione, grazie all' "On Delete Cascade" le cancella anche dal join
            String query="DELETE FROM ristorante WHERE nome = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ristorante.getNome());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
        }

    @Override
    public List<Ristorante> findAllByPiattoName(String name) {

        List<Ristorante> ristoranti = new ArrayList<>();
        String query = "SELECT p.nome, p.ingredienti FROM piatto p " +
                "JOIN ristorante_piatto rp ON p.nome = rp.piatto_nome " +
                "WHERE rp.ristorante_nome = ?";

        System.out.println("going to execute:"+query);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String descrizione = resultSet.getString("descrizione");
                String ubicazione= resultSet.getString("ubicazione");
                ristoranti.add(new Ristorante(nome,ubicazione,descrizione,null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ristoranti;
    }

    public static void main(String[] args) {
        RistoranteDao ristoDao = DBManager.getInstance().getRistoranteDao();
        List<Ristorante> ristoranti = ristoDao.findAll();
        for (Ristorante ristorante : ristoranti) {
            System.out.println(ristorante.getNome());
            System.out.println(ristorante.getDescrizione());
            System.out.println(ristorante.getUbicazione());
        }
    }
}


