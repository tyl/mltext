package org.tylproject.vaadin.mltext;

import com.vaadin.server.VaadinSession;

import java.util.Locale;

/**
 * Created by evacchi on 05/02/15.
 */
public class VaadinLanguageContextResolver implements LanguageContextResolver {
    @Override
    public Locale getCurrentLanguageLocale() {
        Locale selectedLocale = VaadinSession.getCurrent().getLocale();
        return Locale.forLanguageTag(selectedLocale.getLanguage());
    }
}
