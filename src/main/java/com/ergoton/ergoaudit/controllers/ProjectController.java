package com.ergoton.ergoaudit.controllers;

import com.ergoton.ergoaudit.assemblers.project.ProjectAssembler;
import com.ergoton.ergoaudit.assemblers.project.ProjectSelectionAssembler;
import com.ergoton.ergoaudit.assemblers.project.ProjectWithPreviewAssembler;
import com.ergoton.ergoaudit.headers.HeaderConverter;
import com.ergoton.ergoaudit.headers.PreferHeader;
import com.ergoton.ergoaudit.model.project.Category;
import com.ergoton.ergoaudit.model.project.Project;
import com.ergoton.ergoaudit.model.dto.project.GetProject;
import com.ergoton.ergoaudit.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Project Controller", description = "This controller provides interfaces to query existing projects.")
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectAssembler projectAssembler;
    private final ProjectSelectionAssembler projectSelectionAssembler;
    private final ProjectWithPreviewAssembler projectWithPreviewAssembler;
    private final PagedResourcesAssembler<Project> pagedResourcesAssembler;


    @GetMapping(value = "/projects/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a single project by providing an id"
            , description = "This interface is responds with a single project matching the id.")
    public GetProject getProjectById(@PathVariable final String projectId) {

        Project projectWithId = projectService.getProjectById(projectId);

        return projectWithPreviewAssembler.toModel(projectWithId);
    }

    @GetMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a paginated list of projects"
             , description = "This interface is responds with a paginated list of existing projects. The list can be " +
            "filtered by specifying a category.")
    public CollectionModel<?> getPaginatedProjects(
                                         @ParameterObject @PageableDefault(value = 100) final Pageable pageable
                                       , @RequestParam(required = false) final Category category
                                       , @RequestHeader(value = "prefer", required = false) final PreferHeader preferFor) {

        Page<Project> projectsWithCategory = projectService.listProjectsByCategory(pageable, category);

        if (preferFor != null) {
            if (preferFor.getRespondFor().equals("selection")) {
                Iterable <Project> projects = projectService.listProjectsByCategory(category);
                return projectSelectionAssembler.toCollectionModel(projects);
            }
        }

        return pagedResourcesAssembler.toModel(projectsWithCategory, projectAssembler);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(PreferHeader.class,
                new HeaderConverter());
    }

}
