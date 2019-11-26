package com.arah.cwa.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "course_id_seq")
    private Integer id;

    @Column(name = "course_name")
    private String name;

    @Column(name = "students_limit")
    private Integer studentsLimit;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Date start;

    @Column(name = "end_date")
    private Date end;

    @Column(name = "language")
    private String language;

    @Column(name = "level")
    private Integer level;
}
