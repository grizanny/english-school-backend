create sequence user_id_seq;
create table users
(
    id          integer default nextval('user_id_seq'::regclass),
    login       varchar(60)  not null,
    password    varchar(255) not null,
    user_role   varchar(50)  not null,
    email       varchar(100) not null,
    first_name  varchar(100),
    second_name varchar(100),
    age         smallint,

    constraint user_id_pk primary key (id),
    constraint unique_login_constr unique (login),
    constraint unique_email_constr unique (email)
);

create sequence course_id_seq;
create table courses
(
    id             integer default nextval('course_id_seq'::regclass),
    course_name    varchar(255) not null,
    students_limit smallint     not null,
    description    varchar(255) not null,
    start_date     date         not null,
    end_date       date         not null,
    language       varchar(100) not null,
    level          varchar(20)  not null,

    constraint course_id_pk primary key (id)
);

create sequence group_id_seq;
create table groups
(
    id              integer default nextval('group_id_seq'::regclass),
    teacher_id      integer      not null,
    students_amount smallint     not null,
    group_name      varchar(100) not null,

    constraint group_id_pk primary key (id),
    constraint teacher_to_user_id foreign key (teacher_id) references users (id)
);

create sequence course_groups_id_seq;
create table course_groups
(
    id        integer default nextval('course_groups_id_seq'::regclass),
    course_id integer not null,
    group_id  integer not null,

    constraint course_groups_pk primary key (id),
    constraint cg_course_fk foreign key (course_id) references courses (id),
    constraint cg_group_fk foreign key (group_id) references groups (id)
);

create sequence group_students_id_seq;
create table group_students
(
    id         integer default nextval('group_students_id_seq'::regclass),
    group_id   integer not null,
    student_id integer not null,

    constraint group_students_pk primary key (id),
    constraint gs_group_id_fk foreign key (group_id) references groups (id),
    constraint gs_user_id_fk foreign key (student_id) references users (id)
);

create sequence lesson_id_seq;
create table lessons
(
    id          integer default nextval('lesson_id_seq'::regclass),
    group_id    integer      not null,
    location    varchar(255) not null,
    start_time  time         not null,
    end_time    time         not null,
    day_of_week smallint     not null,

    constraint lesson_pk primary key (id),
    constraint l_group_id_fk foreign key (group_id) references groups (id)
);

create sequence group_lessons_id_seq;
create table group_lessons
(
    id        integer default nextval('group_lessons_id_seq'::regclass),
    group_id  integer not null,
    lesson_id integer not null,

    constraint group_lessons_pk primary key (id),
    constraint gl_group_id_fk foreign key (group_id) references groups (id),
    constraint gl_lesson_id_fk foreign key (lesson_id) references lessons (id)
);

create sequence visit_log_id_seq;
create table visit_logs
(
    id         integer          default nextval('visit_log_id_seq'::regclass),
    student_id integer not null,
    lesson_id  integer not null,
    absent     boolean not null default false,
    mark       smallint,

    constraint visit_logs_pk primary key (id),
    constraint vl_student_id_fk foreign key (student_id) references users (id),
    constraint vl_lesson_id_fk foreign key (lesson_id) references lessons (id)
);