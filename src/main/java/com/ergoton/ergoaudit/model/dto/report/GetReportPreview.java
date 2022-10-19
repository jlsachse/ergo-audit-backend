package com.ergoton.ergoaudit.model.dto.report;

import com.ergoton.ergoaudit.model.report.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@Schema(name = "Report Preview")
@EqualsAndHashCode(callSuper = true)
public class GetReportPreview extends RepresentationModel<GetReportPreview> {

    private String id;
    private LocalDate date;
    private State state;

}
