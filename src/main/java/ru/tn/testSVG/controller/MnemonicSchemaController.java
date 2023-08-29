package ru.tn.testSVG.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * @author Maksim Shchelkonogov
 * 29.08.2023
 */
@Named("mnemonicAppController")
@ApplicationScoped
public class MnemonicSchemaController {

    private static final String VERSION = "1";

    public String getVersion() {
        return VERSION;
    }
}
