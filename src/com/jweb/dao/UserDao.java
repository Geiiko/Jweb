package com.jweb.dao;

import com.jweb.beans.User;

public interface UserDao
{
	void creer(User utilisateur) throws DAOException;
    User trouver(String email) throws DAOException;
}
