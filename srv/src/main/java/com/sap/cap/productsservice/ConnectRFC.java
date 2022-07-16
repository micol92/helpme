package com.sap.cap.productsservice;



import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;

 
@WebServlet("/ConnectRFC")

public class ConnectRFC extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter responseWriter = response.getWriter();
        System.out.println("Debugging message - 1");

        try {
            System.out.println("Debugging message - 2");

            // access the RFC Destination "Test"
            JCoDestination destination = JCoDestinationManager.getDestination("S4H_RFC");
            System.out.println("Debugging message - 3");

            // make an invocation of STFC_CONNECTION in the backend
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction("STFC_CONNECTION");
            System.out.println("Debugging message - 4");

            JCoParameterList imports = stfcConnection.getImportParameterList();
            imports.setValue("REQUTEXT", "JCO successful");
            System.out.println("Debugging message - 5");

            stfcConnection.execute(destination);

            JCoParameterList exports = stfcConnection.getExportParameterList();
            String echotext = exports.getString("ECHOTEXT");
            String resptext = exports.getString("RESPTEXT");

            responseWriter.println(echotext+":"+resptext);
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}