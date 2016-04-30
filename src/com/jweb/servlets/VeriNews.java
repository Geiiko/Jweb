package com.jweb.servlets;

import javax.servlet.http.HttpServletRequest;

import com.jweb.beans.New;
import com.jweb.beans.User;
import com.jweb.dao.NewDao;
import com.jweb.dao.UserDao;

public class VeriNews
{
	public static final String CHAMP_TITLE = "title";
    public static final String CHAMP_CONTENT = "content";
    private String error = "";
    private NewDao      newDao;

    public VeriNews(NewDao newDao)
    {
        this.newDao = newDao;
    }
	public New createNews(HttpServletRequest request)
    {
    	New data = new New();
    	String title = request.getParameter(CHAMP_TITLE);
		String content = request.getParameter(CHAMP_CONTENT);
		String author = "4";
		
		try
		{
			testTitle(title);
			testContent(content);
			data.setTitle(title);
			data.setContent(content);
			data.setAuthor(author);
			newDao.creer(data);
		}
		catch (Exception error)
		{
			this.setError(error.getMessage());
		}
		return data;
    }
	private void testContent(String content) throws Exception
	{
		if (content == null || content.trim().length() == 0)
			throw new Exception("Veuillez rentrer un titre");	
	}
	private void testTitle(String title) throws Exception
	{
		if (title == null || title.trim().length() == 0)
			throw new Exception("Veuillez rentrer un contenu");
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
