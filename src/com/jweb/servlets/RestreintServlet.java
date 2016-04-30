package com.jweb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jweb.beans.New;
import com.jweb.beans.User;
import com.jweb.dao.DAOFactory;
import com.jweb.dao.NewDao;
import com.jweb.dao.UserDao;

public class RestreintServlet extends HttpServlet
{
	public static final String VIEW = "/WEB-INF/restreint/Restreint.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_DATA         = "data";
    private NewDao     newDao;
    
	public void init() throws ServletException
	{
		this.newDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getNewsDao();
	}
    public RestreintServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		VeriNews tester = new VeriNews(this.newDao);
		New recup = tester.createNews(request);

		request.setAttribute("error", tester.getError());
        if (tester.getError() == "")
		{
			response.sendRedirect(request.getContextPath());
			return;
		}

        request.setAttribute(ATT_DATA, recup);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
