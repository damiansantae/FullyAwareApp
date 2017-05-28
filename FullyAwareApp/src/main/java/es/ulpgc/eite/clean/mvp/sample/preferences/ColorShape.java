package es.ulpgc.eite.clean.mvp.sample.preferences;

import java.io.Serializable;

/**
 * Methods that creates a color shape to select it on preferences.
 */
public enum ColorShape implements Serializable {
    CIRCLE(1), SQUARE(2);

    private final int value;

    ColorShape(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ColorShape getShape(int num) {
        switch (num) {
            case 1:
                return CIRCLE;
            case 2:
                return SQUARE;
            default:
                return CIRCLE;
        }
    }
}