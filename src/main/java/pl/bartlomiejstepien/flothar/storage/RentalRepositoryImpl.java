package pl.bartlomiejstepien.flothar.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.bartlomiejstepien.flothar.storage.entity.Rental;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

public class RentalRepositoryImpl implements RentalRepository
{
    private static final Logger LOGGER = LogManager.getLogger(RentalRepositoryImpl.class);

    @Override
    public List<Rental> getRentals(LocalDateTime startDate, LocalDateTime endDate)
    {
        LOGGER.debug("Getting rentals in period from " + startDate + " to " + endDate);

        final EntityManager entityManager = PersistenceHelper.getEntityManager();
        final TypedQuery<Rental> query = entityManager.createQuery("select rental from Rental rental where rental.startDateTime < :startDate and rental.endDateTime < :endDate", Rental.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        final List<Rental> rentals = query.getResultList();

        entityManager.close();
        return rentals;
    }

    @Override
    public Rental getById(Integer id)
    {
        LOGGER.debug("Getting rental with id=" + id);

        final EntityManager entityManager = PersistenceHelper.getEntityManager();
        final Rental rental = entityManager.find(Rental.class, id);
        entityManager.close();
        return rental;
    }

    @Override
    public List<Rental> getByCustomerId(Integer customerId)
    {
        LOGGER.debug("Getting rental by customer id = " + customerId);

        final EntityManager entityManager = PersistenceHelper.getEntityManager();

        final TypedQuery<Rental> query = entityManager.createQuery("select rental from Rental rental where rental.customerId = :customerId", Rental.class);
        query.setParameter("customerId", customerId);

        final List<Rental> rentals = query.getResultList();

        entityManager.close();
        return rentals;
    }

    @Override
    public void save(Rental rental)
    {
        LOGGER.debug("Saving rental " + rental);

        final EntityManager entityManager = PersistenceHelper.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(rental);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Rental> getAllRentals()
    {
        LOGGER.debug("Getting all rentals");

        final EntityManager entityManager = PersistenceHelper.getEntityManager();
        TypedQuery<Rental> query = entityManager.createQuery("select rental from Rental rental", Rental.class);
        List<Rental> rentals = query.getResultList();
        entityManager.close();
        return rentals;
    }
}
