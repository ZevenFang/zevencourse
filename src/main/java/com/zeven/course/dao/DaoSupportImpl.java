package com.zeven.course.dao;

import com.zeven.course.util.HibernateSessionFactory;
import com.zeven.course.util.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

@Transactional
@SuppressWarnings("unchecked")
public abstract class DaoSupportImpl<T> implements DaoSupport<T> {

	private Class<T> clazz;

	public DaoSupportImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	protected Session getSession() {
		return HibernateSessionFactory.getSession();
	}

	protected void closeSession(){
		HibernateSessionFactory.closeSession();
	}

	public void save(T entity) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		session.save(entity);
		transaction.commit();
		closeSession();
	}

	public void update(T entity) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		session.update(entity);
		transaction.commit();
		closeSession();
	}

	public void delete(Serializable id) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		Object obj = findById(id);
		if (obj != null) {
			session.delete(obj);
		}
		transaction.commit();
		closeSession();
	}

	public void deleteByIds(Integer[] ids) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		session.createQuery(//
				"DELETE FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")//
				.setParameterList("ids", ids)//
				.executeUpdate();
		transaction.commit();
		closeSession();
	}

	public T findById(Serializable id) {
		Session session = getSession();
		T entity;
		if (id == null) {
			entity = null;
		} else {
			entity = (T) session.get(clazz, id);
		}
		return entity;
	}

	public List<T> findByIds(Integer[] ids) {
		Session session = getSession();
		List<T> list;
		if (ids == null || ids.length == 0) {
			list = Collections.EMPTY_LIST;
		} else {
			list = session.createQuery(//
					"FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")//
					.setParameterList("ids", ids)//
					.list();
		}
		return list;
	}

	public List<T> findAll() {
		Session session = getSession();
		List<T> list = session.createQuery(//
				"FROM " + clazz.getSimpleName())//
				.list();
		return list;
	}

	public boolean isFieldExisted(String field, String value) {
		Session session = getSession();
		List<T> list = session.createQuery(//
				"FROM " + clazz.getSimpleName() + " WHERE "+field+" = (:field)")//
				.setParameter("field", value)//
				.list();
		return 	list.size()!=0;
	}

}
