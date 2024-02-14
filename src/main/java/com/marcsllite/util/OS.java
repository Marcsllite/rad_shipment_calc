package com.marcsllite.util;

public enum OS {
    WINDOWS("Windows"),
    MAC("Mac"),
    UNIX("Unix"),
    SOLARIS("Solaris"),
    NOT_SUPPORTED("Not Supported");

    public final String val;

    OS(String val) {
        this.val = val;
    }
}

