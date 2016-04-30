package com.jweb.dao;

import java.util.List;

import com.jweb.beans.New;

public interface NewDao
{
	void creer(New utilisateur) throws DAOException;
    List<New> trouver() throws DAOException;
}
