drop procedure if exists sp_user_registration;
delimiter //;
create procedure sp_user_registration(
	stu_code varchar(64), -- 学员代码 
	stu_name varchar(64),-- 学员姓名
	reg_code varchar(128),-- 注册码
	reg_code_limits int,--  注册码有效期
	product_id varchar(64),	-- 产品ID
	channel_id varchar(64) -- 渠道ID
)
	begin
		-- 定义学员ID
		declare user_id varchar(64);
		-- 定义注册码ID
		declare regcode_id varchar(64);
		-- 结果赋值
		declare result int default 0;
		
		-- 查询学员ID
		if ifnull(stu_code,'') = '' then -- 学员代码不存在
			set result = -1;
		elseif exists(select 0 from tbl_examw_test_products_users where `code` = stu_code) then
			-- 查询学员代码
			select `id` 
			into user_id
			from tbl_examw_test_products_users 
			where `code` = stu_code limit 0,1;
		else
			-- 学员ID赋值
			set user_id = uuid();
			-- 插入学员
			insert into tbl_examw_test_products_users(`id`,`code`,`name`,`status`,`createTime`,`lastTime`)
			values(user_id,stu_code,stu_name,1,current_timestamp,current_timestamp);
		end if;
		
		if result = 0 then
			-- 插入注册码
			if ifnull(reg_code,0) = 0 then 
				-- 注册码为空
				set result = -2;
			elseif exists(select 0 from tbl_examw_test_products_registrations where `code` = reg_code) then 
				-- 注册码已存在
				set result = -3;
			elseif not exists(select 0 from tbl_Examw_Test_Products_Products where `id` = product_id) then
				-- 产品ID不存在
				set result = -4;
			elseif not exists(select 0 from tbl_examw_test_products_channels where `id` =  channel_id) then
				-- 渠道ID不存在
				set result = -5;
			else
				-- 注册码ID赋值
				set regcode_id = uuid();
				-- 插入注册码
				insert into tbl_examw_test_products_registrations(`id`,`code`,`limits`,`status`,`product_id`,`channel_id`,`createTime`,`lastTime`)
				values(regcode_id,reg_code,ifnull(reg_code_limits,12),1,product_id,channel_id,current_timestamp,current_timestamp);
			end if;
		end if;
		
		-- 绑定注册码
		if (result = 0) and not exists(select 0 from tbl_examw_test_products_registrationbindings where `register_id` = regcode_id and `user_id` = user_id) then
			-- 插入绑定注册码
			insert into tbl_examw_test_products_registrationbindings(`id`,`register_id`,`user_id`,`createTime`,`lastTime`)
			values(uuid(),regcode_id,user_id,current_timestamp,current_timestamp);
			-- 设置返回
			set result = 1;
		end if;
		
		-- 返回状态
		select result;
	end //;
-- delimiter ;