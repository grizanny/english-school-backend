package com.arah.cwa.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.DayOfWeek;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Lesson {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "lesson_id_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "location")
    private String location;

    @Column(name = "start_time")
    private Time start;

    @Column(name = "end_time")
    private Time end;

    @Column(name = "day_of_week")
    @Enumerated(value = EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;
}
