package com.ead.course.application.model;

import com.ead.course.adapter.repository.entity.ModuleEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    public ModuleDTO(ModuleEntity m) {
        title = m.getTitle();
        description = m.getDescription();
    }

    public static ModuleDTO convert(ModuleEntity m) {
        return new ModuleDTO(m);
    }

    public static List<ModuleDTO> convert(Page<ModuleEntity> moduleEntities) {
        return moduleEntities.stream().map(ModuleDTO::new).collect(Collectors.toList());
    }
}
