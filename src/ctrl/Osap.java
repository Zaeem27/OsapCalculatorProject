package ctrl;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CalculationComposite;
import model.CalculationHandler;

/**
 * Servlet implementation class Osap
 */
@WebServlet("/Osap/*")
public class Osap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Osap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = getServletContext();
		CalculationComposite calcComp = new CalculationComposite();
		context.setAttribute("calcParams", calcComp);
		context.setAttribute("calcHandler", new CalculationHandler(calcComp));
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	Boolean error = false;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		CalculationComposite parameters = (CalculationComposite) context.getAttribute("calcParams");
		CalculationHandler handler = (CalculationHandler) context.getAttribute("calcHandler");
		String resultPage = "/Results.jspx";
		String target = "/UI.jspx";
		// Boolean error = false;
		Double graceInterest = 0.0;
		Double monthlyPayment = 0.0;
		try {
			setCalculationParameters(request, context, parameters);
		} catch (Exception e1) {
			error = true;
		}

		if (request.getParameter("calculate") == null || error
				|| isParamNegative(request) &&  !(request.getRequestURI().equals("/OsapCalc-v4/Osap/Ajax/"))) {
			setErrorResponse(request);
			request.getRequestDispatcher(target).forward(request, response);
			request.getSession().setAttribute("errorMessage", "");
			error = false;
		}

		else if (request.getParameter("calculate") != null
				&& !(request.getRequestURI().equals("/OsapCalc-v4/Osap/Ajax/"))) {
			// System.out.println("1");
			if (!error) {
				// System.out.println("1");
				graceInterest = handler.calcGraceInterest();
				monthlyPayment = handler.calcMonthlyPayment();

				Double fixedRate = parameters.getFixedInterest();
				request.setAttribute("graceInterest", graceInterest);
				request.setAttribute("payment", monthlyPayment);
				request.setAttribute("fixedRate", fixedRate);

				request.getRequestDispatcher(resultPage).forward(request, response);

			}

		}

		else if (request.getParameter("ajax") != null) {
			setErrorResponse(request);
			request.getSession().setAttribute("errorMessage", "");
			error = false;
		} else if (request.getParameter("ajax") == null && !isParamNegative(request)) {
			graceInterest = handler.calcGraceInterest();
			monthlyPayment = handler.calcMonthlyPayment();
			request.setAttribute("graceInterest", graceInterest);
			request.setAttribute("payment", monthlyPayment);
			response.getWriter().write("Grace Period Interest: " + request.getAttribute("graceInterest") + "\n"
					+ "Monthly Payment: " + request.getAttribute("payment"));
			response.getWriter().flush();
		}
		saveSessionData(request, parameters);

	}

	private void setErrorResponse(HttpServletRequest request) {
		if (request.getParameter("principal") != null && (request.getParameter("period") != null)
				&& (request.getParameter("interest") != null)) {

			if (Double.parseDouble(request.getParameter("principal")) <= 0) {
				request.getSession().setAttribute("errorMessage", "Principal muse be greater than 0.");
			}
			if (Double.parseDouble(request.getParameter("interest")) <= 0) {
				request.getSession().setAttribute("errorMessage", "Interest must be greater than 0.");
			}
			if (Double.parseDouble(request.getParameter("period")) <= 0) {
				request.getSession().setAttribute("errorMessage", "Period must be greater than 0.");
			}

		}
	}

	private boolean isParamNegative(HttpServletRequest request) {
		if (Double.parseDouble(request.getParameter("principal")) <= 0
				|| Double.parseDouble(request.getParameter("interest")) <= 0
				|| Double.parseDouble(request.getParameter("period")) <= 0) {
			return true;
		}
		return false;
	}

	private void setCalculationParameters(HttpServletRequest request, ServletContext context,
			CalculationComposite parameters) throws Exception {

		if (request.getParameter("principal") != null) {
			parameters.setPrincipal(Double.parseDouble(request.getParameter("principal")));
		} else {
			// parameters.setPrincipal(Double.parseDouble((context.getInitParameter("principal"))));
		}
		if (request.getParameter("interest") != null) {
			parameters.setInterest(Double.parseDouble(request.getParameter("interest")));
		} else {
			// parameters.setInterest(Double.parseDouble((context.getInitParameter("interest"))));
		}
		if (request.getParameter("period") != null) {
			parameters.setPeriod(Double.parseDouble(request.getParameter("period")));
		} else {
			// parameters.setPeriod(Double.parseDouble((context.getInitParameter("period"))));
		}
		parameters.setFixedInterest(Double.parseDouble(context.getInitParameter("fixedInterest")));
		parameters.setGracePeriod(Integer.parseInt(context.getInitParameter("gracePeriod")));
		if (request.getParameter("grace") != null) {
			parameters.setGracePeriodUsed(request.getParameter("grace"));
		} else {
			parameters.setGracePeriodUsed(context.getInitParameter("grace"));
		}
	}
	
	private void saveSessionData(HttpServletRequest request, CalculationComposite parameters) {
		request.getSession().setAttribute("interest", parameters.getInterest());
		request.getSession().setAttribute("principal", parameters.getPrincipal());
		request.getSession().setAttribute("period", parameters.getPeriod());
		request.getSession().setAttribute("checked", parameters.isGracePeriodUsed());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
