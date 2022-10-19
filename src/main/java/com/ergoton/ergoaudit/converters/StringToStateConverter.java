package com.ergoton.ergoaudit.converters;

import com.ergoton.ergoaudit.model.report.State;
import org.springframework.core.convert.converter.Converter;

public class StringToStateConverter implements Converter<String, State> {

    @Override
    public State convert(String source) {
        return State.valueOf(source.toUpperCase());
    }

}
