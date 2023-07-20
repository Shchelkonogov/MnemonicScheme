package ru.tn.testSVG.beans;

import ru.tn.testSVG.model.MnemonicData;

import javax.ejb.Local;
import java.util.List;

/**
 * Локальный интерфейс который описывает бины
 */
@Local
public interface InMDataBeanLocal {

    /**
     * Метод возвращает данные для отображения мнемосхемы
     * @param object объект по которому грузятся данные
     * @return коллекция с данными
     */

    List<MnemonicData> getData(String object, String login);


    /**
     * Метод возвращает имя пользователя
     * @param sessionID объект по которому получаем имя пользователя из базы
     * @return login
     */
    String getUser(String sessionID);
}
