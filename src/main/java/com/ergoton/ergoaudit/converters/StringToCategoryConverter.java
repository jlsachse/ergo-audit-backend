package com.ergoton.ergoaudit.converters;

import com.ergoton.ergoaudit.model.project.Category;
import org.springframework.core.convert.converter.Converter;

public class StringToCategoryConverter implements Converter<String, Category> {

    @Override
    public Category convert(String source) {
        return Category.valueOf(source.toUpperCase());
    }

}
