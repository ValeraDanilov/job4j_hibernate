package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.Candidate;

public class HbnRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        try {
            SessionFactory session = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            create(Candidate.of("Nick", 2, 1000), session);
            create(Candidate.of("Maya", 8, 6000), session);
            create(Candidate.of("Nick", 14, 12000), session);
            findById(2, session);
            findBName("Nick", session);
            replace(2, Candidate.of("Darius", 22, 15000), session);
            findById(2, session);
            System.out.println(delete(3, session));
            findById(3, session);

        } catch (Exception eo) {
            eo.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void create(Candidate candidate, SessionFactory sessions) {
        Session session = sessions.openSession();
        session.beginTransaction();
        session.save(candidate);
        session.getTransaction().commit();
        session.close();
    }

    public static void findById(int id, SessionFactory sessions) {
        Session session = sessions.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Candidate s where s.id = :id")
                .setParameter("id", id);
        for (Object ob : query.list()) {
            System.out.println(ob);
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void findBName(String name, SessionFactory sessions) {
        Session session = sessions.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Candidate s where s.name = :name")
                .setParameter("name", name);
        for (Object ob : query.list()) {
            System.out.println(ob);
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void replace(int id, Candidate candidate, SessionFactory sessions) {
        Session session = sessions.openSession();
        session.beginTransaction();
        session.createQuery("update Candidate s set s.name = :newName, s.experience = :newExperience, s.salary = :newSalary where s.id = :id")
                .setParameter("id", id)
                .setParameter("newName", candidate.getName())
                .setParameter("newExperience", candidate.getExperience())
                .setParameter("newSalary", candidate.getSalary())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public static boolean delete(int id, SessionFactory sessions) {
        Session session = sessions.openSession();
        session.beginTransaction();
        int res = session.createQuery("delete from Candidate s where s.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return res > 0;
    }
}
