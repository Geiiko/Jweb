package com.jweb.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jweb.beans.User;

public class RestrictFilter implements Filter
{

    public static final String ACCES_PUBLIC = "/Home";
    public static final String ATT_USER = "Utilisateur";
	@Override
	public void destroy()
	{
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException
	{
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(ATT_USER);
        
        if (user == null)
        {
            response.sendRedirect(ACCES_PUBLIC);
        }
        else
        {
        	if (user.isAdm())
        		chain.doFilter(request, response);
        	else
        		response.sendRedirect(ACCES_PUBLIC);
        }
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}
}
