USE SLAGODATABASE;
CREATE TABLE img
(
	id BIGINT NOT NULL auto_increment primary key,
    img MEDIUMBLOB NOT NULL,
    imgsize long default NULL,
    imgtype varchar(20) NOT NULL CHECK(NOT(imgtype="")),
    postid bigint NOT NULL,
    index(postid),
    foreign key(postid) references post(id) ON UPDATE cascade ON delete cascade
);
-- ALTER TABLE img
-- ADD postid bigint references post(id) ON UPDATE cascade ON delete cascade;