package com.ergoton.ergoaudit.converters;

import com.ergoton.ergoaudit.model.User;
import com.ergoton.ergoaudit.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;


@AllArgsConstructor
public class WalletAddressToUserConverter implements Converter<String, User> {

    private final UserService userService;

    @Override
    public User convert(MappingContext<String, User> mappingContext)
    {

        String walletAddress = mappingContext.getSource();

        return userService.getUserByWalletAddress(walletAddress);

    }

}