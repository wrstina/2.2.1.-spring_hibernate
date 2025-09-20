package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.EntityGraph;
import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
   public void add(User user) {
       sessionFactory.getCurrentSession().save(user);
   }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
   public List<User> listUsers() {
        String hql = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.car";
        TypedQuery <User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        return query.getResultList();
   }

    @Override
    public User findUserByCarModelAndSeries(String model, int series) {
        EntityGraph<User> entityGraph = sessionFactory.getCurrentSession().createEntityGraph((User.class));
        entityGraph.addAttributeNodes("car");

        String hql = "SELECT u FROM User u WHERE u.car.model = :model AND u.car.series = :series";

        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery(hql, User.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .setHint("javax.persistence.fetchgraph",
                        sessionFactory.getCurrentSession().getEntityGraph("User_with_Car"));

        return query.getSingleResult();
    }
}

