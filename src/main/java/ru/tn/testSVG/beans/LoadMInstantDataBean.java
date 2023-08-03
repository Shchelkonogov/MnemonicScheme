package ru.tn.testSVG.beans;

import ru.tn.testSVG.model.MnemonicData;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stateless бин для загрузки мгновенных данных объекта
 * для мнемосхемы объекта
 */
@Stateless(name = "LoadMInstantDataBean")
public class LoadMInstantDataBean implements InMDataBeanLocal {
    private static final Logger LOG = Logger.getLogger(LoadMInstantDataBean.class.getName());


    private static final String GET_MUID_SQL = "select * from mnemo.set_mnemo_async_request(?, ?)";
    private static final String GET_STATUS_SQL = "select mnemo.get_mnemo_async_status(?)";
    private static final String GET_DATA_SQL = "select * from mnemo.get_mnemo_async_data(?)";


    @Resource(name = "jdbc/DataSource")
    private DataSource ds;

    @EJB
    private ParseMDataBean bean;


    @Override
    public List<MnemonicData> getData(String object, String login) {
        List<MnemonicData> result = new ArrayList<>();
        String muid = null;

        try(Connection connect = ds.getConnection();
                CallableStatement stm = connect.prepareCall(GET_MUID_SQL)) {
            stm.setLong(1, Long.parseLong(object));
            stm.setString(2, login);

            ResultSet res = stm.executeQuery();
            res.next();
            muid = res.getString(1);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        LOG.log(Level.INFO,"LoadMInstantDataBean.getData muid: " + muid + " for object: " + object + " load instant data");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LOG.log(Level.INFO, "LoadMInstantDataBean.getData waiting: " + ((i + 1) * 6000) + " ms for object: " + object);

            try(Connection connect = ds.getConnection();
                    PreparedStatement stmGetStatus = connect.prepareStatement(GET_STATUS_SQL);
                    PreparedStatement stmGetData = connect.prepareStatement(GET_DATA_SQL)) {

                stmGetStatus.setString(1, muid);

                ResultSet res = stmGetStatus.executeQuery();
                while(res.next()) {
                    if (Objects.nonNull(res.getString(1))) {
                        stmGetData.setString(1, muid);

                        ResultSet resData = stmGetData.executeQuery();
                        bean.parseData(result, resData);

                        if(result.isEmpty()) {
                            result.add(new MnemonicData("time", null, "Невозможно получить данные с объекта", null));
                        }

                        LOG.log(Level.INFO,"LoadMInstantDataBean.getData data load for object: " + object);
                        return result;
                    } else {
                        LOG.log(Level.INFO,"LoadMInstantDataBean.getData waiting data for object: " + object);
                    }
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        if(result.isEmpty()) {
            result.add(new MnemonicData("time", null, "Превышено время ожидания данных", null));
        }
        LOG.log(Level.WARNING,"LoadMInstantDataBean.getData no data load for object: " + object);
        return result;
    }
}
