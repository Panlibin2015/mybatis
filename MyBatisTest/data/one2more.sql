CREATE TABLE TB_STUDENT(
S_ID INT PRIMARY KEY AUTO_INCREMENT,
S_NAME VARCHAR(20),
S_SEX VARCHAR(10),
S_AGE INT,
S_C_ID INT,
FOREIGN KEY(S_C_ID) REFERENCES TB_CLASS(C_ID));

INSERT INTO TB_STUDENT(S_NAME,S_SEX,S_AGE,S_C_ID) VALUES('TOM','MALE',18,1);
INSERT INTO TB_STUDENT(S_NAME,S_SEX,S_AGE,S_C_ID) VALUES('JACK','MALE',19,1);
INSERT INTO TB_STUDENT(S_NAME,S_SEX,S_AGE,S_C_ID) VALUES('ROSE','FEMALE',18,1);


insert into tb_class(c_name) values('Class One');

desc tb_class;

select * from tb_class where c_name='Class One';

		select c.c_id,c.c_name,s.s_id,s.s_name,s.s_sex,s.s_age from
		tb_class c left
		outer join tb_student s on c.c_id = s.s_c_id where
		c.c_id=2