package com.diploma.student.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "specialty")
public class StudentGroup extends BaseEntity{

    @NotNull
    @Length(min=5, max=30)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @NotNull
    @Min(value = 1, message = "Год должен быть не меньше 1")
    @Max(value = 6, message = "Год не может быть больше 6")
    private Integer year;

}