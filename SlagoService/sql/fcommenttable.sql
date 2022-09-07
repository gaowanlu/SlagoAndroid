USE SLAGODATABASE;
CREATE table fcomment
(
	id bigint NOT NULL auto_increment primary key,
    postid bigint NOT NULL ,-- references post(id)  ON UPDATE cascade ON delete cascade,
    userid bigint NOT NULL ,-- references user(id)  ON UPDATE cascade ON delete cascade,
    content varchar(100) NOT NULL,
    commentdate datetime NOT NULL,
    index(postid),
    index(userid),
	foreign key(userid) references user(id) ON UPDATE cascade ON delete cascade,
	foreign key(postid) references post(id) ON UPDATE cascade ON delete cascade
);