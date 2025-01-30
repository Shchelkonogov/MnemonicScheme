package ru.tn.testSVG.beans;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class RedirectSB {

    private static final Logger logger = Logger.getLogger(RedirectSB.class.getName());

    private static final String SQL = "select * from m_adm.get_td_application_url(?)";
    private static final String SELECT_ASOT_MUID = "select muid from asot.ots_heat_points " +
            "where name = (select obj_name from admin.obj_object where obj_id = ?) limit 1";

    @Resource(name = "jdbc/DataSource")
    private DataSource ds;

    public String getRedirectUrl(String name) {
        try (Connection connect = ds.getConnection();
             PreparedStatement stm = connect.prepareStatement(SQL)) {
            stm.setString(1, name);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return res.getString(1);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error load redirect url", e);
        }
        return null;
    }

    public String getMuid(String id) {
        try (Connection connect = ds.getConnection();
             PreparedStatement stm = connect.prepareStatement(SELECT_ASOT_MUID)) {
            stm.setInt(1, Integer.parseInt(id));
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return res.getString(1);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error load asot muid by id: " + id, e);
        }
        return null;
    }
}
