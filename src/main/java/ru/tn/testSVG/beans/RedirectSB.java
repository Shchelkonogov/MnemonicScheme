package ru.tn.testSVG.beans;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class RedirectSB {

    private static final Logger logger = Logger.getLogger(RedirectSB.class.getName());

    private static final String SQL = "select * from m_adm.get_td_application_url('dNet')";

    @Resource(name = "jdbc/DataSource")
    private DataSource ds;

    public String getRedirectUrl(String object) {
        try (Connection connect = ds.getConnection();
             PreparedStatement stm = connect.prepareStatement(SQL)) {
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return res.getString(1)
                        .replace("[object]", object)
                        .replace("[date]", LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error load redirect url", e);
        }
        return null;
    }
}
