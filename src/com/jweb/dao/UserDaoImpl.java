package com.jweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jweb.beans.User;
import com.jweb.dao.DAOException;
import com.jweb.dao.UserDao;

public class UserDaoImpl implements UserDao
{
	private DAOFactory          daoFactory;
	public UserDaoImpl(DAOFactory daoFactory)
	{
		this.daoFactory = daoFactory;
	}
	private static final String SQL_INSERT = "INSERT INTO User (email, login, password) VALUES (?, ?, ?)";
	@Override
	public void creer(User utilisateur) throws DAOException
	{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, utilisateur.getEmail(), utilisateur.getLogin(), utilisateur.getPassword());
	        int statut = preparedStatement.executeUpdate();
	        if (statut == 0) {
	            throw new DAOException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
	        }
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if (valeursAutoGenerees.next()) {
	            utilisateur.setId(valeursAutoGenerees.getLong(1));
	        } else {
	            throw new DAOException("Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	    }
	}
	private static final String SQL_SELECT_PAR_EMAIL = "SELECT id, email, login, password, isadm FROM User WHERE email = ?";
	@Override
	public User trouver(String email) throws DAOException
	{
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User utilisateur = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL, false, email );
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            utilisateur = map(resultSet);
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	    }
	    return utilisateur;
	}
	public static PreparedStatement initialisationRequetePreparee( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
	    PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
	    for ( int i = 0; i < objets.length; i++ ) {
	        preparedStatement.setObject( i + 1, objets[i] );
	    }
	    return preparedStatement;
	}
	private static User map(ResultSet resultSet) throws SQLException {
	    User utilisateur = new User();
	    utilisateur.setId(resultSet.getLong("id"));
	    utilisateur.setEmail(resultSet.getString("email"));
	    utilisateur.setPassword(resultSet.getString("password"));
	    utilisateur.setLogin(resultSet.getString("login"));
	    if (resultSet.getLong("isadm") == 1)
	    	utilisateur.setAdm(true);
	    return utilisateur;
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
