package caixeiroviajante.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import caixeiroviajante.core.Controller;

/**
 * Servlet implementation class ServletCaixeiroViajante
 */
@WebServlet("/ServletCaixeiroViajante")
public class ServletCaixeiroViajante extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCaixeiroViajante() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String adresses[] = request.getParameterValues("adress");
		
		Controller.factoryMethod(request, response, adresses);

	}


}
