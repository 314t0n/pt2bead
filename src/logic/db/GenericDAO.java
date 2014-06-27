package logic.db;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logic.ICrudService;
import logic.IEntity;

public class GenericDAO<T extends IEntity> implements Serializable, ICrudService<T> {

    private final EntityManagerFactory emf;
    private final Class<T> type;

    public GenericDAO(Class<T> type) {
        this.type = type;
        System.out.println(type);
        emf = Persistence.createEntityManagerFactory("progtech2PU");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<T> readAll() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(type));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void create(T t) {

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (read(t.getId().intValue()) != null) {
                System.out.println("Entity " + t + " already exists.");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public T read(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(type, id.longValue());
        } finally {
            em.close();
        }
    }

    @Override
    public void update(T t) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            t = em.merge(t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = t.getId().intValue();
                if (read(id) == null) {
                    System.out.println("The entity with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void delete(T t) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            try {
                t = em.getReference(type, t.getId());
                t.getId();

                em.remove(t);
                em.getTransaction().commit();

            } catch (EntityNotFoundException enfe) {
                System.out.println("The entity no longer exists.");
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public int rowCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<T> rt = cq.from(type);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
