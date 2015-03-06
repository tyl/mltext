package org.tylproject.vaadin.mltext;

import com.vaadin.server.VaadinSession;

import java.util.Locale;

/**
 * A language resolver that uses Vaadin's Sessione
 *
 * @see com.vaadin.server.VaadinSession
 */
public class VaadinLanguageContextResolver implements LanguageContextResolver {
    @Override
    public Locale getCurrentLanguageLocale() {
        Locale selectedLocale = VaadinSession.getCurrent().getLocale();
        return Locale.forLanguageTag(selectedLocale.getLanguage());
    }
}
