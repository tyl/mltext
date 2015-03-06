package org.tylproject.vaadin.mltext;

import com.vaadin.data.util.converter.Converter;
import org.tylproject.data.mongo.common.LangKey;
import org.tylproject.data.mongo.common.MlText;

import java.util.Locale;

/**
 * Converts an MlText to the current locale string
 */
public class MlTextToStringConverter implements Converter<String, MlText> {
    @Override
    public MlText convertToModel(String value, Class<? extends MlText> targetType, Locale locale) throws ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(MlText value, Class<? extends String> targetType, Locale locale) throws ConversionException {

        final String country = locale.getCountry().toLowerCase();
        if (country.length() > 2) {
            throw new ConversionException(country +": Unsupported Country Code -- Locale " + locale);
        }
        return value.getText(LangKey.valueOf(country));
    }

    @Override
    public Class<MlText> getModelType() {
        return MlText.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
