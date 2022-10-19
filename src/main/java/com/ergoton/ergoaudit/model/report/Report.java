package com.ergoton.ergoaudit.model.report;

import com.ergoton.ergoaudit.model.project.Project;
import com.ergoton.ergoaudit.model.Section;
import com.ergoton.ergoaudit.model.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "report")
public class Report {

    @Id private String id;

    // References
    @DBRef private Project project;
    @DBRef private User author;

    private State state;

    private LocalDate date;
    private Integer percentage;

    private String result;

    private List<Section> sections = new ArrayList<>();

}
