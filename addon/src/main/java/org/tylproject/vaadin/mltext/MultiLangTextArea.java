package org.tylproject.vaadin.mltext;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextArea;
import org.tylproject.data.mongo.common.LangKey;

/**
 * Created by evacchi on 03/02/15.
 */
public class MultiLangTextArea extends AbstractMultiLangTextField<TextArea> {
    public MultiLangTextArea(LangKey currentLanguage) {
        super(new TextArea(), currentLanguage);
    }
}
