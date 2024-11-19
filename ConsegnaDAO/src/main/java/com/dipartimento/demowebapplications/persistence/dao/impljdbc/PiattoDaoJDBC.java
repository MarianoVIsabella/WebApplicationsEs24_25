package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PiattoDaoJDBC implements PiattoDao {

    Connection connection;


    public PiattoDaoJDBC(Connection conn){
        this.connection = conn;
    }

    @Override
    public List<Piatto> findAll() {
        List<Piatto> piatti = new ArrayList<Piatto>();
        String query = "select * from piatto";
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Piatto piatto = new PiattoProxy();

                piatto.setNome(rs.getString("nome"));
                piatto.setIngredienti(rs.getString("ingredienti"));

                piatti.add(piatto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return piatti;
    }

    @Override
    public Piatto findByPrimaryKey(String nome) {
        String query = "SELECT nome, ingredienti FROM piatto WHERE nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Piatto piatto = new PiattoProxy();
                piatto.setNome(resultSet.getString("nome"));
                piatto.setIngredienti(resultSet.getString("ingredienti"));
                return piatto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void save(Piatto piatto) {
        /*
        INSERT INTO t VALUES (1,'foo updated'),(3,'new record')
ON CONFLICT (id) DO UPDATE SET txt = EXCLUDED.txt;
         */
        String query = "INSERT INTO piatto (nome, ingredienti) VALUES (?, ?) " +
                "ON CONFLICT (nome) DO UPDATE SET ingredienti = EXCLUDED.ingredienti";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, piatto.getNome());
            statement.setString(2, piatto.getIngredienti());
            statement.executeUpdate();
            List<Ristorante> ristoranti= piatto.getRistoranti();
            if (ristoranti==null || ristoranti.isEmpty()){
                //se entro qui nuova riga di piatto, SAVE, non faccio altro
                return;
            }
            //se sono qui, il piatto ha qualche ristorante associato, mi assicuro di inserirli nella relazione
            for (Ristorante ristorante : ristoranti){
                insertJoinRistorantePiatto(connection, ristorante.getNome(), piatto.getNome());
            }

            /* VECCHIO UPDATE
            //resetto tutte le tuple che legano il piatto che sto salvando a un ristorante per poterle
            //reinserire
            resetRelationsPresentInTheJoinTable(connection,piatto.getNome());
            RistoranteDao rd=DBManager.getInstance().getRistoranteDao();
            for (Ristorante r : ristoranti){
                rd.save(r);
                insertJoinRistorantePiatto(connection,r.getNome(),piatto.getNome());
            }
             */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetRelationsPresentInTheJoinTable(Connection connection, String nomePiatto) throws Exception {

        String query="Delete FROM ristorante_piatto WHERE piatto_nome= ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomePiatto);
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
    public void delete(Piatto piatto) {
        Piatto check=findByPrimaryKey(piatto.getNome());
        if (check==null){
            //se sono qui non esiste il piatto e non faccio niente
            return;
        }
        List<Ristorante> ristoranti=piatto.getRistoranti(); //lista dei Ristoranti con il piatto da eliminare
        for (Ristorante tempR : ristoranti) {
            if (tempR.getPiatti() != null){
                tempR.getPiatti().remove(piatto); //rimuovo il piatto appena cancellato dalla lista se presente
            }
        }
        try {
            //cancella le tuple nella relazione e dal join grazie alla "On delete cascade" del db
            String query="DELETE FROM ristorante WHERE nome = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, piatto.getNome());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Piatto> findAllByRistoranteName(String ristoranteNome) {



        List<Piatto> piatti = new ArrayList<>();
        String query = "SELECT p.nome, p.ingredienti FROM piatto p " +
                "JOIN ristorante_piatto rp ON p.nome = rp.piatto_nome " +
                "WHERE rp.ristorante_nome = ?";

        System.out.println("going to execute:"+query);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ristoranteNome);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String ingredienti = resultSet.getString("ingredienti");
                piatti.add(new Piatto(nome, ingredienti,null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return piatti;
    }

    public static void main(String[] args) {
        PiattoDao piattoDao = DBManager.getInstance().getPiattoDao();
        List<Piatto> piatti = piattoDao.findAll();
        for (Piatto piatto : piatti) {
            System.out.println(piatto.getNome());
            System.out.println(piatto.getIngredienti());

        }
    }
}
