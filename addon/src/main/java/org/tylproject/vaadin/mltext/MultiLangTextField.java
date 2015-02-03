package org.tylproject.vaadin.mltext;

import com.vaadin.ui.TextField;
import org.tylproject.data.mongo.common.LangKey;

/**
 * Created by evacchi on 03/02/15.
 */
public class MultiLangTextField extends AbstractMultiLangTextField<TextField> {
    public MultiLangTextField(LangKey currentLanguage) {
        super(new TextField(), currentLanguage);
    }
}
