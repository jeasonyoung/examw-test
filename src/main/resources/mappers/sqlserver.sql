/*
 * 产品表
 **/
if exists(select 0 from sysobjects where xtype = 'u' and name='tbl_ExamProducts')
begin
	print 'drop table tbl_ExamProducts'
	drop table tbl_ExamProducts
end
go
	print 'create table tbl_ExamProducts'
go
create table tbl_ExamProducts
(
	ID 			nvarchar(64),--产品ID
	Name		nvarchar(512),--产品名称
	ExamID	nvarchar(64),--所属考试ID
	
	ViewUrl	nvarchar(1024),--产品展示URL
	EnterUrl	nvarchar(1024),--产品入口URL
	
	OriginalPrice	float default(0),--原价
	DiscountPrice	float default(0),--优惠价
	
	items		int default(0),--试题数
	
	OrderNo	int default(0),--排序号
	
	constraint PK_tbl_ExamProducts primary key(ID)--主键约束
)
go
/*
 * 试卷表
 * */
if exists(select 0 from sysobjects where xtype = 'u' and name='tbl_ExamPapers')
begin
	print 'drop table tbl_ExamPapers'
	drop table tbl_ExamPapers
end 
go
	print 'create table tbl_ExamPapers'
go
create table tbl_ExamPapers
(
	ID				nvarchar(64),--试卷ID
	Name		nvarchar(512),--试卷名称
	ExamID	nvarchar(64),--所属考试ID
	
	ViewUrl		nvarchar(1024),--试卷展示URL
	RealUrl			nvarchar(1024),--全真模考URL
	NormalUrl	nvarchar(1024),--普通模考URL
	
	Price			float default(0),--考试币
	Time			datetime default(getdate()),--时间
	
	constraint PK_tbl_ExamPapers primary key(ID)--主键约束
)
go