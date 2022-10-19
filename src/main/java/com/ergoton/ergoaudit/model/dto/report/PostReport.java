package com.ergoton.ergoaudit.model.dto.report;

import com.ergoton.ergoaudit.model.Section;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Schema(name = "New Report")
public class PostReport {

    // References
    @NotBlank(message = "You need to reference an existing project.") private String projectId;
    @NotBlank(message = "You need to reference an existing user.") private String authorWalletAddress;

    @NotBlank(message = "You need to provide the response JSON.") private String result;
    @NotBlank(message = "You need to provide a percentage value.") private Integer percentage;

    @Size(min = 1, message = "You need to specify at least one section.")
    private List<Section> sections;

}
