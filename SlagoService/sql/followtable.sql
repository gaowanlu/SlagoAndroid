USE SLAGODATABASE;
CREATE TABLE follow
(
	userid bigint NOT NULL,-- references user(id)  ON UPDATE cascade ON delete cascade,
    followed bigint NOT NULL,-- references user(id)  ON UPDATE cascade ON delete cascade,
    primary key(userid,followed),
    index(userid),
    index(followed),
    foreign key(userid) references user(id) ON UPDATE cascade ON delete cascade,
    foreign key(followed) references user(id) ON UPDATE cascade ON delete cascade
);