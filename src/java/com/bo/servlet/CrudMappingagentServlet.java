/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

//import com.boa.dao.MappingAgent;
//import com.boa.dao.UserYabes;
import com.boa.model.Mappingagent;
//import com.boa.util.DBUtil;
import com.boa.util.DatabaseProcess;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class CrudMappingagentServlet extends HttpServlet {
    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private MappingAgent dao;
//    private UserYabes dao1;
    private static String INSERT = "add_mapping_agent.jsp";
    private static String UPDATE = "update_mapping_agent.jsp";

    public CrudMappingagentServlet() {
//        dao = new MappingAgent();
//        dao1 = new UserYabes();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            String trancodeid = request.getParameter("trancodeId");
            String agent_id = request.getParameter("agentId");
            String status = dp.deleteMappingagent(trancodeid, agent_id); 
            if (status.equals("Sukses menghapus mapping agent")) {
                HttpSession session = request.getSession(true);
//                session.setMaxInactiveInterval(60);
                String username = session.getAttribute("userlogin").toString();
                String activitas = "menghapus mapping agent " + request.getParameter("trancodeId");
                dp.userYabes(username, activitas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_mapping_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_mapping_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            String trancodeid = request.getParameter("trancodeId");
            String agent_id = request.getParameter("agentId");
            Mappingagent mappingagent = dp.getMappingagentByTrancodeid(trancodeid, agent_id);
            request.setAttribute("mappingagent", mappingagent);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Mappingagent mappingagent = new Mappingagent();
        mappingagent.setAgent_id(request.getParameter("agentId"));
        mappingagent.setTrancodeid(request.getParameter("trancodeId"));
        mappingagent.setFeejual(request.getParameter("feeJual"));
        mappingagent.setHargajual(request.getParameter("hargaJual"));
        mappingagent.setSa_commission(request.getParameter("sa_commission"));
        String status = dp.updateMappingagent(mappingagent);
        if (status.equals("Sukses mengupdate mapping agent")) {
            HttpSession session = request.getSession(true);
            String username = session.getAttribute("userlogin").toString();
            String activitas = "mengupdate mapping agent " + request.getParameter("trancodeId");
            dp.userYabes(username, activitas);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_mapping_agent.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_mapping_agent.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        }
    }
}
