create schema onthionline;

use onthionline;

create table user (
	id int(11) not null primary key auto_increment,
    user_name varchar(50) not null,
    full_name varchar(255) not null,
    email varchar(50) not null,
    password varchar(100) not null,
	phone varchar(30),
    avatar varchar(255),
    birthday timestamp,
    gender int(1) default 0,
    city varchar(20),
    class varchar(5),
    school varchar(50),
    is_active boolean not null,
    online_time int(255) default 0,
    created_date timestamp not null default current_timestamp,
	last_login timestamp default current_timestamp
);

create table role (
	id int(11) not null primary key auto_increment,
    role_name varchar(50) not null,
	description varchar(100) not null,
    created_date timestamp not null default current_timestamp,
    updated_date timestamp not null default current_timestamp
);

create table user_role (
    user_id int(11) not null,
    role_id int(11) not null
);

create table exam (
	id int(11) not null primary key auto_increment,
    name varchar(255) not null,
    image varchar(255),
    subject varchar(50) not null,
    num_question int(5) not null,
    num_people_did int(5) not null,
    description text,
    is_active boolean not null,
    time int(10),
    created_date timestamp not null default current_timestamp,
    updated_date timestamp default current_timestamp
);

create table exam_question (
	id int(11) not null primary key auto_increment,
    image varchar(255),
    question text not null,
    option1 text,
    option2 text,
    option3 text,
    option4 text,
    suggestion text,
    correct_answer varchar(10),
    exam_id int(11) not null,
    created_date timestamp not null default current_timestamp,
    updated_date timestamp default current_timestamp
);

create table exam_history (
	id int(11) not null primary key auto_increment,
	user_id int(11) not null,
    exam_id int(11) not null,
    created_date timestamp not null default current_timestamp,
    updated_date timestamp default current_timestamp,
    num_correct_ans int(5) not null,
    num_pause int(5) default 0,
    num_remake int(5) default 0,
    time int(10)
);

create table comment (
	id int(11) not null primary key auto_increment,
    user_id int(11) not null,
    exam_id int(11) not null,
    content text not null,
    parent_id int(11),
    created_date timestamp not null default current_timestamp,
    updated_date timestamp default current_timestamp
);

alter table user_role add constraint fk_userid foreign key (user_id) references user(id);
alter table user_role add constraint fk_roleid foreign key (role_id) references role(id);
alter table exam_history add constraint fk_user_history foreign key (user_id) references user(id);
alter table exam_history add constraint fk_exam_history foreign key (exam_id) references exam(id);
alter table exam_question add constraint fk_exam_question foreign key (exam_id) references exam(id);
alter table comment add constraint fk_exam_comment foreign key (exam_id) references exam(id);
alter table comment add constraint fk_user_comment foreign key (user_id) references user(id);



insert into role(role_name, description) values('ROLE_ADMIN','ADMIN');
insert into role(role_name, description) values('ROLE_MANAGER','MANAGER');
insert into role(role_name, description) values('ROLE_USER','USER');
