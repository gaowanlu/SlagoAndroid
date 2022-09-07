USE SLAGODATABASE;
CREATE TABLE scomment
(
	id bigint NOT NULL auto_increment primary key,
    fcommentid bigint NOT NULL,--  references fcomment(id)  ON UPDATE cascade ON delete cascade,
    userid bigint NOT NULL ,-- references user(id) ON UPDATE cascade ON delete cascade,
    content varchar(100) NOT NULL,
    commentdate datetime NOT NULL,
    index(userid),
    index(fcommentid),
	foreign key(userid) references user(id) ON UPDATE cascade ON delete cascade,
	foreign key(fcommentid) references fcomment(id) ON UPDATE cascade ON delete cascade
);