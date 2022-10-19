package com.ergoton.ergoaudit.converters;

import com.ergoton.ergoaudit.model.project.Project;
import com.ergoton.ergoaudit.services.ProjectService;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;


@AllArgsConstructor
public class ProjectIdToProjectConverter implements Converter<String, Project> {

    private final ProjectService projectService;

    @Override
    public Project convert(MappingContext<String, Project> mappingContext)
    {

        String projectId = mappingContext.getSource();

        return projectService.getProjectById(projectId);

    }

}