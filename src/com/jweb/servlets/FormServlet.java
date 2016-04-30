package com.jweb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jweb.beans.User;
import com.jweb.dao.DAOFactory;
import com.jweb.dao.UserDao;

public class FormServlet extends HttpServlet
{
	public static final String VIEW = "/WEB-INF/Formulaire.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    private UserDao     utilisateurDao;
    
	    public void init() throws ServletException {
	        this.utilisateurDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
	    }
	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	        this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
	    }
	    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	        VeriForm form = new VeriForm(utilisateurDao);
	        User utilisateur = form.inscrireUser(request);
	        request.setAttribute(ATT_FORM, form);
	        request.setAttribute(ATT_USER, utilisateur);
	        this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
	    }
}
