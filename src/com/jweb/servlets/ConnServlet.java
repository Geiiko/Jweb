package com.jweb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jweb.beans.User;
import com.jweb.dao.DAOFactory;
import com.jweb.dao.UserDao;

public class ConnServlet extends HttpServlet
{
	public static final String VIEW = "/WEB-INF/Connection.jsp";
    private UserDao     utilisateurDao;
    public static final String CONF_DAO_FACTORY = "daofactory";
    
    public void init() throws ServletException {
        this.utilisateurDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
    }
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		VeriConn tester = new VeriConn(this.utilisateurDao);
		User user = tester.ConnectUser(request);

		HttpSession session = request.getSession();
		request.setAttribute("error", tester.getError());
		if (tester.getError() == "")
		{
			session.setAttribute("Utilisateur", user);
			session.setAttribute("admin", user.isAdm());
			response.sendRedirect(request.getContextPath());
			return;
		}
		else
			session.setAttribute("Utilisateur", null);
		
		request.setAttribute("connect", tester);
		request.setAttribute("user", user);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
