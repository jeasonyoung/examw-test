#examw-test
==========
#update mysql 2014-09-16
1.alter table tbl_Examw_Test_Library_Structures modify column title varchar(512);
2.alter table tbl_Examw_Test_Library_Structures drop foreign key FK_2jm5becjnsebrron6plvd9c2s;(删除外键)
show create table tbl_Examw_Test_Library_Structures;
3. alter table tbl_Examw_Test_Library_Structures drop column pid;

#update mysql 2014-09-19
show create table tbl_Examw_Test_Settings_Exams;
1.alter table tbl_Examw_Test_Settings_Exams drop foreign key FK_5h95ggk5t4aqbiqp23fvxyttt;(删除外键)
2.alter table tbl_Examw_Test_Settings_Exams drop column area_id;

