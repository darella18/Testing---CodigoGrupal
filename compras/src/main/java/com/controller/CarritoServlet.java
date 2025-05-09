import com.utils.ErrorUtil;

public class CarritoServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            // gestionar el carrito
        } catch (Exception e) {
            String mensaje = ErrorUtil.getUserFriendlyMessage("INVALID_INPUT");
            request.setAttribute("errorMessage", mensaje);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            // Lógica de la solicitud POST
        } catch (Exception e) {
            String mensaje = ErrorUtil.getUserFriendlyMessage("INVALID_INPUT");
            request.setAttribute("errorMessage", mensaje);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
