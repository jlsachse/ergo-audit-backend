package com.ergoton.ergoaudit.model.dto.report;

import com.ergoton.ergoaudit.HalRepresentationModel;
import com.ergoton.ergoaudit.model.Section;
import com.ergoton.ergoaudit.model.report.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name = "Report")
@EqualsAndHashCode(callSuper = true)
public class GetReport extends HalRepresentationModel<GetReport> {

    private String id;
    private LocalDate date;
    private Integer percentage;
    private State state;

    private String result;

    private List<Section> sections = new ArrayList<>();
}
