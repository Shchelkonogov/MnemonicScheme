package ru.tn.testSVG.servlets;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Сервлет который загружает нужный svg файл для отображения
 */
@WebServlet(name = "LoadSvgServlet", urlPatterns = "/svg/*")
public class LoadSvgServlet extends HttpServlet {

    private static final String SQL = "select mnemo from admin.dev_mnemo_type where kind = ?";

    @Resource(name = "jdbc/DataSource")
    private DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mnemonicName = req.getPathInfo().substring(1);

        try(Connection connect = ds.getConnection();
                PreparedStatement stm = connect.prepareStatement(SQL)) {
            stm.setString(1, mnemonicName);

            ResultSet res = stm.executeQuery();
            if(res.next()) {
                byte[] content = res.getBytes(1);
                resp.setContentType("image/svg+xml");
                resp.setContentLength(content.length);
                resp.getOutputStream().write(content);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch(SQLException e) {
            throw new ServletException("Something failed at SQL/DB level.", e);
        }
    }
}
