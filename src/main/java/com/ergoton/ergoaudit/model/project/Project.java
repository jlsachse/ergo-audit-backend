package com.ergoton.ergoaudit.model.project;

import com.ergoton.ergoaudit.model.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Document(collection = "project")
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id private String id;

    private String logo;

    private LocalDate lastUpdated;

    private String name;
    private String website;
    private Category category;
    private String description;

    private Integer percentage;

    private List<Section> scores = new ArrayList<>();

}
