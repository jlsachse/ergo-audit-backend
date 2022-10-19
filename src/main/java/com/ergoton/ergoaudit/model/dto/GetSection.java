package com.ergoton.ergoaudit.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@Schema(name = "Section")
@EqualsAndHashCode(callSuper = true)
public class GetSection extends RepresentationModel<GetSection> {

    private String label;
    private Integer percentage;

}
