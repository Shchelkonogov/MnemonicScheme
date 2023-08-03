package ru.tn.testSVG.beans;

import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Stateless
public class CheckUserSB {

    private static final Logger LOG = Logger.getLogger(CheckUserSB.class.getName());

    private static final String CHECK_SESSION = "select td_adm.get_active_session_login(?)";

    @Resource(name = "jdbc/DataSource")
    private DataSource ds;


    public String getUser(String sessionID) {
        try (Connection connection = ds.getConnection();
             PreparedStatement stm = connection.prepareStatement(CHECK_SESSION)) {
            stm.setString(1, sessionID);

            ResultSet res = stm.executeQuery();
            if (res.next() && (res.getString(1) != null)) {
                return res.getString(1);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "check session error: ", e);
        }
        return null;
    }

}
