package com.examw.test.dao;

import java.io.Serializable;
/**
 * 数据操作接口。
 * @author yangyong.
 * @since 2014-04-28.
 */
public interface IBaseDao<T> {
	/**
	 * 加载数据对象。
	 * @param c
	 * 	数据对象类型。
	 * @param id
	 * 	数据对象主键。
	 * @return 对象。
	 * */
	T load(Class<T> c,Serializable id);
	/**
	 * 保存数据对象。
	 * @param data
	 * 	数据对象。
	 * @return 主键值。
	 * */
	Serializable save(T data);
	/**
	 * 更新数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void update(T data);
	/**
	 * 保存或更新数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void saveOrUpdate(T data);
	/**
	 * 删除数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void delete(T data);
	/**
	 * 清理缓存。
	 */
	void flush();
	/**
	 * 手动清除实体对象的二级缓存。
	 * @param clazz
	 * 实体对象。
	 */
	void evict(Class<?> clazz);
	/**
	 * 对象状态融合。
	 * @param object
	 */
	 void merge(Object object);
}