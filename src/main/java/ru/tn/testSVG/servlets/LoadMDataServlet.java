package ru.tn.testSVG.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.tn.testSVG.beans.CheckUserSB;
import ru.tn.testSVG.beans.InMDataBeanLocal;

import java.io.IOException;

/**
 * Сервлет загрузки архивных данных
 * Запускается из js
 */
@WebServlet(name = "LoadMDataServlet", urlPatterns = "/load")
public class LoadMDataServlet extends HttpServlet {

    @EJB(beanName = "LoadMDataBean")
    private InMDataBeanLocal bean;

    @EJB
    private CheckUserSB checkUserSB;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = checkUserSB.getUser(req.getParameter("sessionID"));
        ServletLoadDoPostMethod.doPost(req, resp, bean, login);
    }
}
