package ru.tn.testSVG.beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Stateless
public class RedirectSB {

    private static final String SQL = "select mnemo_ip, mnemo_port from admin.dz_sys_param";

    @Resource(name = "jdbc/DataSource")
    private DataSource ds;

    public String getRedirectUrl(String object) {
        try (Connection connect = ds.getConnection();
             PreparedStatement stm = connect.prepareStatement(SQL)) {
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return "http://" + res.getString(1) + ":" + res.getString(2) + "/dNet/?object=" + object
                        + "&date=" + LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
