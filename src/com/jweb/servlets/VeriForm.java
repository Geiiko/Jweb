package com.jweb.servlets;

import javax.servlet.http.HttpServletRequest;
import com.jweb.beans.*;
import com.jweb.dao.DAOException;
import com.jweb.dao.UserDao;

public class VeriForm
{
	public static final String CHAMP_EMAIL = "email";
    public static final String CHAMP_PASS = "password";
    public static final String CHAMP_CONF = "passwdconfirm";
    public static final String CHAMP_LOGIN = "login";
    private String error;
    private UserDao      utilisateurDao;

    public VeriForm(UserDao utilisateurDao)
    {
        this.utilisateurDao = utilisateurDao;
    }
	public User inscrireUser(HttpServletRequest request)
    {
    	User user = new User();
    	String mail = request.getParameter(CHAMP_EMAIL);
		String password = request.getParameter(CHAMP_PASS);
		String conf = request.getParameter(CHAMP_CONF);
		String login = request.getParameter(CHAMP_LOGIN);
		
		try
		{
			testLogin(login);
			testMail(mail);
			testPassword(password, conf);
			user.setEmail(mail);
			user.setLogin(login);
			user.setPassword(password);
			utilisateurDao.creer(user);
		}
		catch (Exception error)
		{
			this.error = error.getMessage();
		}
		return user;
    }
	private void testMail(String mail) throws Exception
	{
		if (mail == null || mail.trim().length() == 0)
			throw new Exception("Veuillez rentrer une adresse mail");
		else if (!mail.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$"))
			throw new Exception("Adresse mail non valide");
		else if (utilisateurDao.trouver(mail) != null) {
            throw new Exception("Cette adresse email est déjà utilisée, merci d'en choisir une autre.");
        }
	}
	private void testPassword(String pass, String conf) throws Exception
	{
		if (pass == null || pass.trim().length() == 0 || conf == null || conf.trim().length() == 0)
			throw new Exception("Un des champs mot de passe est vide");
		if (!pass.equals(conf))
			throw new Exception("Mot de passe différents");
	}
	private void testLogin(String login) throws Exception
	{
		if (login != null && login.trim().length() < 4)
			throw new Exception("Le login doit contenir au minimum 4 caractères");
	}
	public String getError() {
		return error;
	}
}
