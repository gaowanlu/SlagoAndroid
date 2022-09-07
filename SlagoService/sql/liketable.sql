USE SLAGODATABASE;s
CREATE TABLE likes
(
	userid bigint NOT NULL,--  references user(id)  ON UPDATE cascade ON delete cascade,
    postid bigint NOT NULL,--  references post(id)  ON UPDATE cascade ON delete cascade,
    primary key(userid,postid),
    index(userid),
    index(postid),
	foreign key(userid) references user(id) ON UPDATE cascade ON delete cascade,
	foreign key(postid) references post(id) ON UPDATE cascade ON delete cascade
);