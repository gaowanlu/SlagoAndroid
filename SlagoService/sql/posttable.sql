USE SLAGODATABASE;
CREATE TABLE post
(
	id BIGINT NOT NULL auto_increment primary key,
    userid BIGINT NOT NULL,
    index(userid),
    foreign key(userid) references user(id) ON UPDATE cascade ON delete cascade,
    posttext VARCHAR(160) NOT NULL,
    postdate datetime NOT NULL
);