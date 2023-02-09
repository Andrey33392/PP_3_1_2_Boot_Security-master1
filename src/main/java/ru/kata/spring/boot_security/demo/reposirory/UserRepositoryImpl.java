package ru.kata.spring.boot_security.demo.reposirory;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u from User u").getResultList();
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUserById(Long id) {
        Query query = entityManager.createQuery("delete from User user where user.id = :id");
        query.setParameter("id",id);
        query.executeUpdate();

    }

    @Override
    public void updateUser(User user) {

        entityManager.merge(user);

    }

    @Override
    public User findByEmail(String email) {
         TypedQuery <User> query = (entityManager.createQuery("SELECT user FROM  User user Join fetch  user.roles WHERE  user.email=:email", User.class));
                query.setParameter("email", email);
               return query.getSingleResult();


//        TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user where user.email=:email",
//                User.class).setParameter("email", email);
//        return query.getSingleResult();

//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
//        Root<User> itemRoot = criteriaQuery.from(User.class);
//        criteriaQuery.where(criteriaBuilder.equal(itemRoot.get("email"), email));
//        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
