CREATE TABLE TB_HEAD_TEACHER(
HT_ID INT PRIMARY KEY AUTO_INCREMENT,
HT_NAME VARCHAR(20),
HT_AGE INT);

INSERT INTO TB_HEAD_TEACHER(HT_NAME,HT_AGE) VALUES('ZHANGSAN',40);

create table tb_class(
c_id int primary key auto_increment,
c_name varchar(20),
c_ht_id int unique,
foreign key(c_ht_id) references tb_head_teacher(ht_id));

insert into tb_class(c_name,c_ht_id) values('Class One',1);

show tables;