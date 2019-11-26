package com.arah.cwa.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "group_id_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private AppUser teacher;

    @Column(name = "students_amount")
    private Integer studentsAmount;

    @Column(name = "group_name")
    private String name;
}
