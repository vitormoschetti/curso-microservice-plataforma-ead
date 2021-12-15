package com.ead.course.adapter.outbound.persistence.entity;


import com.ead.course.adapter.inbound.controller.dto.ModuleDTO;
import com.ead.course.application.model.ModuleModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@Table(name = "TB_MODULES")
public class ModuleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.NONE)
    @Column(name = "moduleId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID moduleId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 250)
    private String description;

    @Column(nullable = false, name = "creationDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CourseEntity course;

    @Fetch(FetchMode.SUBSELECT)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    private Set<LessonEntity> lessons;

    private ModuleEntity(ModuleModel moduleModel) {
        moduleId = moduleModel.getModuleId();
        title = moduleModel.getTitle();
        description = moduleModel.getDescription();
        creationDate = moduleModel.getCreationDate();
    }

    public static List<ModuleEntity> converter(List<ModuleModel> modulos) {
        return modulos.stream().map(ModuleEntity::new).collect(Collectors.toList());
    }

    public static ModuleEntity converter(ModuleModel moduleModel) {
        return new ModuleEntity(moduleModel);
    }

    public void atualizar(ModuleDTO moduleDTO) {
        title = moduleDTO.getTitle();
        description = moduleDTO.getDescription();
    }
}
