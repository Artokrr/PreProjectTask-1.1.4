package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try (Session session = factory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("create table if not exists users (\n" +
                    "id int auto_increment not null, name varchar(50) not null, lastname varchar(50)\n" +
                    " not null, age tinyint not null, primary key(id)\n" +
                    ")").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            factory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            factory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            factory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();

        } catch (Exception e) {
            factory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }

    }


    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Session session = factory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            usersList = session.createSQLQuery("SELECT * FROM users").addEntity(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            factory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }
        return usersList;

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("truncate table users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            factory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
