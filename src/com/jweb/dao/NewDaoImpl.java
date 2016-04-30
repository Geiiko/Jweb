package com.jweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jweb.beans.New;
import com.jweb.dao.DAOException;

public class NewDaoImpl implements NewDao
{
	private DAOFactory          daoFactory;
	public NewDaoImpl(DAOFactory daoFactory)
	{
		this.daoFactory = daoFactory;
	}
	private static final String SQL_INSERT = "INSERT INTO News (title, content, author) VALUES (?, ?, ?)";
	@Override
	public void creer(New data) throws DAOException
	{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, data.getTitle(), data.getContent(), data.getAuthor());
	        int statut = preparedStatement.executeUpdate();
	        if (statut == 0) {
	            throw new DAOException("Échec de la création de la news, aucune ligne ajoutée dans la table.");
	        }
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if (valeursAutoGenerees.next()) {
	            data.setId(valeursAutoGenerees.getLong(1));
	        } else {
	            throw new DAOException("Échec de la création de la news en base, aucun ID auto-généré retourné.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	    }
	}
	private static final String SQL_SELECT_ALL = "SELECT id, title, content, author FROM news";
	@Override
	public List<New> trouver() throws DAOException
	{
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    New news = null;
	    List<New> res = new ArrayList<New>();

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next())
	        {
	            news = map(resultSet);
	            res.add(news);
	            while (resultSet.next())
	            {
		            news = map(resultSet);
		            res.add(news);
	            }
	        }
	        else
	        {
	        	throw new DAOException("No News available");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	    }
	    return res;
	}
	public static PreparedStatement initialisationRequetePreparee( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
	    PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
	    for ( int i = 0; i < objets.length; i++ ) {
	        preparedStatement.setObject( i + 1, objets[i] );
	    }
	    return preparedStatement;
	}
	private static New map(ResultSet resultSet) throws SQLException {
	    UserDao      utilisateurDao;
	    New data = new New();
	    data.setId(resultSet.getLong("id"));
	    data.setTitle(resultSet.getString("title"));
	    data.setContent(resultSet.getString("content"));
	    data.setAuthor(resultSet.getString("author"));
	    return data;
	}
	public static void fermetureSilencieuse(ResultSet resultSet) {
	    if (resultSet != null) {
	        try {
	            resultSet.close();
	        } catch (SQLException e) {
	            System.out.println("Échec de la fermeture du ResultSet : " + e.getMessage());
	        }
	    }
	}
	public static void fermetureSilencieuse(Statement statement) {
	    if (statement != null) {
	        try {
	            statement.close();
	        } catch (SQLException e) {
	            System.out.println("Échec de la fermeture du Statement : " + e.getMessage());
	        }
	    }
	}
	public static void fermetureSilencieuse(Connection connexion) {
	    if (connexion != null) {
	        try {
	            connexion.close();
	        } catch (SQLException e) {
	            System.out.println("Échec de la fermeture de la connexion : " + e.getMessage());
	        }
	    }
	}
	public static void fermeturesSilencieuses(Statement statement, Connection connexion) {
	    fermetureSilencieuse(statement);
	    fermetureSilencieuse(connexion);
	}
	public static void fermeturesSilencieuses(ResultSet resultSet, Statement statement, Connection connexion) {
	    fermetureSilencieuse(resultSet);
	    fermetureSilencieuse(statement);
	    fermetureSilencieuse(connexion);
	}
}

