package ru.tn.testSVG.beans;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ru.tn.testSVG.model.MnemonicData;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stateless бин для загрузки архивных данных объекта
 * для мнемосхемы объекта
 */
@Stateless(name = "LoadMDataBean")
public class LoadMDataBean implements InMDataBeanLocal {

    private static final Logger logger = Logger.getLogger(LoadMDataBean.class.getName());

    @Resource(name = "jdbc/DataSource")
    private DataSource ds;

    @EJB
    private ParseMDataBean bean;

    private static final String SQL = "select * from mnemo.get_mnemo_hist_data(?)";

    @Override
    public List<MnemonicData> getData(String object, String login) {
        List<MnemonicData> result = new ArrayList<>();
        try(Connection connect = ds.getConnection();
            PreparedStatement stm = connect.prepareStatement(SQL)) {

            stm.setLong(1, Long.parseLong(object));
            ResultSet res = stm.executeQuery();

            bean.parseData(result, res);
        } catch(SQLException e) {
            logger.log(Level.WARNING, "Error get hist data", e);
        }
        return result;
    }
}
