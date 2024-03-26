create table answer (
    id serial primary key,
    team_id             int not null,
    score              int not null,
    message_id varchar(64) not null,
    question_id varchar(64) not null,
    category int not null,
    created varchar(64),

--     constraint unique_question_id_per_team unique (question_id, team_id),
    constraint fk_answer_team FOREIGN KEY(team_id) REFERENCES "team"(id)
);
