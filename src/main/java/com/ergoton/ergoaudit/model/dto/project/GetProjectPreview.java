package com.ergoton.ergoaudit.model.dto.project;

import com.ergoton.ergoaudit.HalRepresentationModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "Project")
@EqualsAndHashCode(callSuper = true)
public class GetProjectPreview extends HalRepresentationModel<GetProjectPreview> {

    private String id;

    private String name;
    private String category;

}
