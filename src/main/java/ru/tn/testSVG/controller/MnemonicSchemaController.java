package ru.tn.testSVG.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * @author Maksim Shchelkonogov
 * 29.08.2023
 */
@Named("mnemonicAppController")
@ApplicationScoped
public class MnemonicSchemaController implements Serializable {

    private static final String VERSION = "1";

    public String getVersion() {
        return VERSION;
    }
}
