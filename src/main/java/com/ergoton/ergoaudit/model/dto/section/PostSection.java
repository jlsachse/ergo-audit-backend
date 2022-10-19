package com.ergoton.ergoaudit.model.dto.section;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(name = "New Section")
public class PostSection {

    @NotBlank(message = "You need to provide a label.") String label;
    private Integer percentage;

}
