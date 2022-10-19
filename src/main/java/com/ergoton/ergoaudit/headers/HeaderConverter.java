package com.ergoton.ergoaudit.headers;

import java.beans.PropertyEditorSupport;

public class HeaderConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        PreferHeader headers = new PreferHeader();

        if(text.length() == 0) {
            return;
        }

        String[] tokens = text.split(",");

        for(String token: tokens) {
            String[] values = token.split("=");
            if (token.trim().startsWith("respond-for")) {
                String preferType = values[1].trim().toLowerCase();
                headers.setRespondFor(preferType);
            }
        }

        setValue(headers);

    }
}
