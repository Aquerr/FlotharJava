package pl.bartlomiejstepien.flothar.storage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceHelper
{
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pl.bartlomiejstepien.flothar");

    public static EntityManager getEntityManager()
    {
        return entityManagerFactory.createEntityManager();
    }
}
