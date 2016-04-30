package com.jweb.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jweb.beans.New;
import com.jweb.dao.DAOFactory;
import com.jweb.dao.NewDao;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet
{
	public static final String VIEW = "/WEB-INF/Home.jsp";
	private static final long serialVersionUID = 1L;
    public HomeServlet()
    {
        super();
    }
    private NewDao     newsDao;
    private List<New>  listNews;
    public static final String CONF_DAO_FACTORY = "daofactory";
    
    public void init() throws ServletException
    {
        this.newsDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getNewsDao();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		listNews = newsDao.trouver();
		Collections.reverse(listNews);
		request.setAttribute("listNews", listNews);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
