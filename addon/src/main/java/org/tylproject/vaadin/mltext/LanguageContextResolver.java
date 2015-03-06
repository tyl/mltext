package org.tylproject.vaadin.mltext;

import java.util.Locale;

/**
 * Interface for an object that is able to resolve the currently selected locale
 */
public interface LanguageContextResolver {
    Locale getCurrentLanguageLocale();
}
