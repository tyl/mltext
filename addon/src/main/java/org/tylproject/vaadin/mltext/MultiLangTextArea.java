package org.tylproject.vaadin.mltext;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextArea;
import org.tylproject.data.mongo.common.LangKey;
import org.tylproject.data.mongo.config.Context;

/**
 * Created by evacchi on 03/02/15.
 */
public class MultiLangTextArea extends AbstractMultiLangTextField<TextArea> {
    public MultiLangTextArea(Context tylContext) {
        super(new TextArea(), tylContext);
    }
    public MultiLangTextArea(Context tylContext, String caption) {
        this(tylContext);
        this.setCaption(caption);
    }
}
