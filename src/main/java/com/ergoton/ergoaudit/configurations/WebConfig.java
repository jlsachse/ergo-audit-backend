package com.ergoton.ergoaudit.configurations;

import com.ergoton.ergoaudit.converters.StringToCategoryConverter;
import com.ergoton.ergoaudit.converters.StringToStateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCategoryConverter());
        registry.addConverter(new StringToStateConverter());
    }
}