package com.ergoton.ergoaudit.controllers;

import com.ergoton.ergoaudit.assemblers.user.UserAssembler;
import com.ergoton.ergoaudit.model.User;
import com.ergoton.ergoaudit.model.dto.user.GetUser;
import com.ergoton.ergoaudit.model.dto.user.PostUser;
import com.ergoton.ergoaudit.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "User Controller", description = "This controller provides interfaces to query existing users.")
public class UserController {

    private ModelMapper modelMapper;

    private final UserService userService;

    private final UserAssembler userAssembler;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;

    @GetMapping(value = "/users/{walletAddress}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a single user by providing the wallet address"
            , description = "This interface is responds with a single user matching the wallet address.")
    public GetUser getUserByWalletAddress(@PathVariable final String walletAddress) {

        User userWithWalletAddress = userService.getUserByWalletAddress(walletAddress);

        return userAssembler.toModel(userWithWalletAddress);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a paginated list of users"
             , description = "This interface is responds with a paginated list of existing users.")
    public CollectionModel<GetUser> getPaginatedUsers(
            @ParameterObject @PageableDefault(value = 100) final Pageable pageable) {

        Page<User> users = userService.listUsers(pageable);

        return pagedResourcesAssembler.toModel(users, userAssembler);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new user"
            , description = "This interface is used to create a new user.")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody PostUser newUser, @AuthenticationPrincipal String walletAddress) {

        System.out.println("walletAddress = " + walletAddress);
        System.out.println("newUserWalletAddress = " + newUser.getWalletAddress());

        if (walletAddress.equals(newUser.getWalletAddress())) {
            User user = modelMapper.map(newUser, User.class);
            User createdUser = userService.createNewUser(user);
            GetUser userDto = userAssembler.toModel(createdUser);
            Optional<GetUser> userOptional = Optional.of(userDto);
            return ResponseEntity.of(userOptional);
        }

        return ResponseEntity.badRequest().build();

    }

}
