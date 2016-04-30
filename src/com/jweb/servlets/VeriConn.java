package com.jweb.servlets;

import javax.servlet.http.HttpServletRequest;

import com.jweb.beans.User;
import com.jweb.dao.UserDao;

public class VeriConn
{
	public static final String CHAMP_EMAIL = "email";
    public static final String CHAMP_PASS = "password";
    private String error = "";
    private UserDao      utilisateurDao;
    public VeriConn(UserDao userdao)
    {
    	utilisateurDao = userdao;
    }
	public String getError() {
		return error;
	}
	public User ConnectUser(HttpServletRequest request)
    {
    	User user = new User();
    	String mail = request.getParameter(CHAMP_EMAIL);
		String password = request.getParameter(CHAMP_PASS);
		User usertest = new User();
		
		try
		{
			usertest = utilisateurDao.trouver(mail);
			if (usertest == null)
				throw new Exception("Mail error");
			testMail(mail);
			testPassword(password, usertest.getPassword());
			user.setEmail(mail);
			user.setPassword(password);
			user.setAdm(usertest.isAdm());
			user.setLogin(user.getLogin());
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
		if (!mail.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$"))
			throw new Exception("Adresse mail non valide");
	}
	private void testPassword(String pass, String bdd) throws Exception
	{
		if (pass == null || pass.trim().length() == 0)
			throw new Exception("Le mot de passe est vide");
		if (pass.compareTo(bdd) != 0)
			throw new Exception("Erreur dans le mot de passe");
	}
}
