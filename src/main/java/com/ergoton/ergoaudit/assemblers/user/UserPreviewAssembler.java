package com.ergoton.ergoaudit.assemblers.user;

import com.ergoton.ergoaudit.controllers.UserController;
import com.ergoton.ergoaudit.model.User;
import com.ergoton.ergoaudit.model.dto.user.GetUserPreview;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class UserPreviewAssembler implements RepresentationModelAssembler<User, GetUserPreview> {

    private final ModelMapper modelMapper;

    @NotNull
    @Override
    public final GetUserPreview toModel(@NotNull final User user) {

        GetUserPreview userDto = modelMapper.map(user, GetUserPreview.class);

        Link aggregateRoot = linkTo(methodOn(UserController.class)
                .getPaginatedUsers(null))
                .withRel("users");

        Link selfRelation = linkTo(methodOn(UserController.class)
                .getUserByWalletAddress(user.getWalletAddress()))
                .withRel("self");

        userDto.add(selfRelation, aggregateRoot);

        return userDto;

    }
}
