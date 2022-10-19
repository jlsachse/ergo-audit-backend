package com.ergoton.ergoaudit.assemblers.project;

import com.ergoton.ergoaudit.model.dto.project.GetProjectMinimal;
import com.ergoton.ergoaudit.model.project.Project;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class ProjectSelectionAssembler implements RepresentationModelAssembler<Project, GetProjectMinimal> {

    private final ModelMapper modelMapper;

    @NotNull
    @Override
    public GetProjectMinimal toModel(@NotNull Project project) {

        return modelMapper.map(project, GetProjectMinimal.class);
    }

    @NotNull
    @Override
    public CollectionModel<GetProjectMinimal> toCollectionModel(Iterable<? extends Project> projects) {
        ModelMapper modelMapper = new ModelMapper();

        ArrayList<GetProjectMinimal> projectMinimals = new ArrayList<>();

        for (Project projectMinimal: projects){
            GetProjectMinimal userDto = modelMapper.map(projectMinimal, GetProjectMinimal.class);
            projectMinimals.add(userDto);
        }

        return CollectionModel.of(projectMinimals);
    }

}
