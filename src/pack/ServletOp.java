package pack;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletOp
 */
@WebServlet("/ServletOp")
public class ServletOp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Facade facade;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletOp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operation = request.getParameter("op");
		if (operation.equals("questionnaire")){
			String destination= request.getParameter("destination");
			String origine = request.getParameter("origine");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date dateDepart = null;
			Date dateRetour = null;
			try {
				dateDepart = formatter.parse(request.getParameter("dateDebut"));
				dateRetour = formatter.parse(request.getParameter("dateFin"));
			} catch(ParseException e) {
				e.printStackTrace();
			}
			int nbPersonnes= Integer.parseInt(request.getParameter("response5"));
			String budget = request.getParameter("response6");
			int budgetMax;
			if (budget.equals("500+")){
				budgetMax =0;
			}else {
				budgetMax = Integer.parseInt(budget);
			}
			facade.creerVoyage(destination, origine, dateDepart,dateRetour, nbPersonnes, budgetMax);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
