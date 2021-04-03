package com.epam.web.localization;

public enum Localization {
    EN, RU, BY;

    public static boolean isValid(String value) {
        Localization[] localizations = values();

        for (Localization localization : localizations) {
            if (localization.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
