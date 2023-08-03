package ru.tn.testSVG.controller;

import org.primefaces.PrimeFaces;
import ru.tn.testSVG.beans.CheckUserSB;
import ru.tn.testSVG.beans.LoadSvgBean;
import ru.tn.testSVG.beans.RedirectSB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Контроллер jsf страницы
 */
@ManagedBean
@ViewScoped
@Named("MnemonicC")

public class MnemonicC {

    private static final Logger LOG = Logger.getLogger(MnemonicC.class.getName());

    private String objectId, svgName, objectName, sessionId, login;

    @EJB
    private LoadSvgBean bean;

    @EJB
    private RedirectSB redirectBean;

    @EJB
    private CheckUserSB checkUserSB;

    private String hello;

    /**
     * Инициализация загрузки мнемосхемы
     */
    public void initLoad() {
        System.out.println("initLoad objectId "+ objectId);
        System.out.println("init session ID " + sessionId);
        LOG.log(Level.INFO,"load object: " + objectId);
        if (Objects.isNull(objectId) || Objects.isNull(sessionId)) {
            svgName = "/svg/error.svg";
        } else {
            login = checkUserSB.getUser(sessionId);
            if (login == null) {
                svgName = "/svg/error.svg";
            }
            if (objectId.equals("testNewFitch")) {
                svgName = "/img/testNewFitch.svg?v1";
            } else {
                objectName = bean.getObjectName(objectId);
                if (Objects.nonNull(objectName)) {
                    objectName = " для " + objectName;
                }

                String fileName = bean.loadSvgName(objectId);
                if (Objects.isNull(fileName)) {
                    svgName = "/svg/error.svg";
                } else {
                    svgName = "/svg/" + fileName;
                }
            }
        }
        LOG.log(Level.INFO,"mnemonic file for object: " + objectId + " is " + svgName);
    }

    /**
     * Тестовый метод
     * Загружается из js кода по нажатию на элемент svg
     */
    public void jsCall() {
        LOG.log(Level.INFO,"hello from svg for object " + objectId);
        hello = "Hello from svg!";
    }

    public void redirect() {
        PrimeFaces.current().executeScript("window.open('" + redirectBean.getRedirectUrl(objectId) + "', '_blank')");
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSvgName() {
        return svgName;
    }

    public void setSvgName(String svgName) {
        this.svgName = svgName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
