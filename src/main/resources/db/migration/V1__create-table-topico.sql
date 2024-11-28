create table topico(

    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(500) not null,
    fechaDeCreacion datetime not NULL,
    status tinyint not null,
    autor varchar(100) not null,
    curso varchar(100) not null,

    primary key (id)
);