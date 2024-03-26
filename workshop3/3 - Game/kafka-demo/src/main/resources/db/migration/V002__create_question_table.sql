create table question (
    id serial primary key,
    question_id varchar(64) not null,
    question varchar(256) not null,
    category int not null,
    created varchar(64)
);
