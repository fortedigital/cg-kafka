create table answer (
    id serial primary key,
    team_id             int not null,
    score              int not null,
    message_id varchar(64) not null,
    question_id varchar(64) not null,
    category int not null,
    created varchar(64),

    constraint unique_message_id_per_question_id unique (message_id, question_id),
    constraint fk_answer_team FOREIGN KEY(team_id) REFERENCES "team"(id)
);
