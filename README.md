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

#update mysql 2014-10-17 
#更新产品用户代码code数据类型 integer => string
1.alter table tbl_Examw_Test_Products_Users modify code varchar(64);

#update mysql 2014-10-17
#删除产品代码Code字段
1.alter table tbl_Examw_Test_Products_Products drop column code;
#删除产品创建时间createTime字段
2.alter table tbl_Examw_Test_Products_Products drop column createTime;
#删除产品最后修改时间lastTime字段。
3.alter table tbl_Examw_Test_Products_Products drop column lastTime;

#update mysql 2014-10-24
#删除考试科目中的area_id字段
1.show create table tbl_Examw_Test_Settings_Subjects;
2.alter table tbl_Examw_Test_Settings_Subjects drop foreign key FK_941rywm7yc06dxpstyj2fm3vc; (删除外键)
3.alter table tbl_Examw_Test_Settings_Subjects drop column area_id;
