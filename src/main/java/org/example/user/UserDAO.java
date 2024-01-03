package org.example.user;

import org.example.interfaces.BaseDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class UserDAO implements BaseDAO<User> {

	private final SessionFactory sessionFactory;

	public UserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<User> readAll() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM User ", User.class).list();
		}
	}

	@Override
	public User readById(Long id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(User.class, id);
		}
	}

	@Override
	public User createOrUpdate(User entity) {
		return null;
	}

	@Transactional
	public User create(User entity) {

		Session session = sessionFactory.openSession();

		entity.setUsername(generateUsername(entity.getFirstName(), entity.getLastName()));
		entity.setPassword(generatePassword());

		session.saveOrUpdate(entity);

		return entity;

	}

	@Transactional
	public User update(User entity) {

		Session session = sessionFactory.openSession();

		session.update(entity);

		return entity;

	}

	public User createOrUpdateForPassword(User entity) {
		Long id = 0l;

		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();

			id = entity.getId();
			System.out.println("=======================" + entity.getPassword());
			session.update(entity);
			transaction.commit();

		}
		System.out.println("**************************************" + readById(id).getPassword());
		return entity;

	}

	@Transactional
	@Override
	public boolean deleteById(Long id) {
		Session session = sessionFactory.openSession();
		User user = session.get(User.class, id);
		if (user != null) {
			session.remove(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean existById(Long id) {
		return !readById(id).equals(null);
	}

	private String generateUsername(String firstName, String lastName) {
		String username = firstName + "." + lastName;
		List<User> users = readAll();

		for (User user : users) {
			if (user.getUsername().equals(username)) {
				username += user.getId();
				return username;
			}
		}
		return username;

	}

	private String generatePassword() {

		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder password = new StringBuilder();
		Random random = new Random();
		for (int i = 1; i <= 10; i++) {
			password.append(characters.charAt(random.nextInt(characters.length())));
		}
		return password.toString();
	}

	public User readByUsername(String username) {

		try (Session session = sessionFactory.openSession()) {

			return session.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", username)
				.uniqueResult();
		}
		finally {

		}
	}

}
