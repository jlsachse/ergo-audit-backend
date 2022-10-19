package com.ergoton.ergoaudit.model.dto.project;

import com.ergoton.ergoaudit.HalRepresentationModel;
import com.ergoton.ergoaudit.model.dto.GetSection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name = "Project")
@EqualsAndHashCode(callSuper = true)
public class GetProject extends HalRepresentationModel<GetProject> {

    private String id;
    private String logo;

    private LocalDate lastUpdated;

    private String name;
    private String website;
    private String category;
    private String description;

    private Integer percentage;

    private List<GetSection> scores = new ArrayList<>();

}
