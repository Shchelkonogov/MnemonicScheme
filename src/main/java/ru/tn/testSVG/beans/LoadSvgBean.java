package ru.tn.testSVG.beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Stateless бин который выгружает имя мнемосхемы
 * в соответствии с объектом
 */
@Stateless
public class LoadSvgBean {

    private static final String SQL = "select mnemo.get_mnemo_type(?)";
    private static final String SQL_GET_NAME = "select obj_name from admin.obj_object where obj_id = (?)";

    @Resource(name = "jdbc/DataSource")
    private DataSource ds;

    /**
     * Метод определяет имя svg файла для заданного объекта
     * @param object объект для которого определяется svg
     * @return имя svg файла
     */
    public String loadSvgName(String object) {
        return getData(object, SQL);
    }

    /**
     * Метод получает имя объета по его id
     * @param objectId id объекта
     * @return имя объекта
     */
    public String getObjectName(String objectId) {
        return getData(objectId, SQL_GET_NAME);
    }

    /**
     * Метод выполняет запрос в базу по определенному sql и возвращает String
     * @param object объект для поиска
     * @param sql запрос в базу
     * @return результат запроса
     */
    private String getData(String object, String sql) {
        try(Connection connect = ds.getConnection();
            PreparedStatement stm = connect.prepareStatement(sql)) {
            stm.setLong(1, Long.parseLong(object));

            ResultSet res = stm.executeQuery();
            if(res.next()) {
                return res.getString(1);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
