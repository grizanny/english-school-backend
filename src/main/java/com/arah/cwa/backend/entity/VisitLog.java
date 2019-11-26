package com.arah.cwa.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visit_logs")
public class VisitLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "visit_log_id_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private AppUser student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(name = "absent")
    private Boolean absent;

    @Column(name = "mark")
    private Integer mark;
}
