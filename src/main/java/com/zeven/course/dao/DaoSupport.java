package com.zeven.course.dao;

import java.io.Serializable;
import java.util.List;

public interface DaoSupport<T> {

	/**
	 * 保存实体
	 * @param entity 实体
	 */
	void save(T entity);
	/**
	 * 通过id删除实体
	 * @param id 存入的id
	 */
	void delete(Serializable id);
	/**
	 * 修改实体
	 * @param entity 实体
	 */
	void update(T entity);
	/**
	 * 通过id查找一个实体
	 * @param id
	 * @return
	 */
	T findById(Serializable id);
	/**
	 * 通过ids查找多个实体
	 * @param ids
	 * @return
	 */
	List<T> findByIds(Integer[] ids);
	/**
	 * 查找全部实体
	 * @return
	 */
	List<T> findAll();
	
}
