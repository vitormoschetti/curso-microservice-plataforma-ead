package com.ead.course.adapter.inbound.controller.dto;

import com.ead.course.application.model.ModuleModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID moduleId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    public ModuleDTO(ModuleModel m) {
        moduleId = m.getModuleId();
        title = m.getTitle();
        description = m.getDescription();
    }

    public static ModuleDTO converter(ModuleModel m) {
        return new ModuleDTO(m);
    }

    public static PageImpl<ModuleDTO> converter(Page<ModuleModel> moduleEntities) {
        List<ModuleDTO> moduleDTOS = moduleEntities.stream().map(ModuleDTO::new).collect(Collectors.toList());
        return new PageImpl<ModuleDTO>(moduleDTOS);
    }
}
