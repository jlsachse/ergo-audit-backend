package com.ergoton.ergoaudit.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@Schema(name = "User")
@EqualsAndHashCode(callSuper = true)
public class GetUserPreview extends RepresentationModel<GetUserPreview> {

    private String id;
    private String name;

}
