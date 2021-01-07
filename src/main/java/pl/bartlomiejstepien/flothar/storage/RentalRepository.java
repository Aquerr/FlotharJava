package pl.bartlomiejstepien.flothar.storage;

import pl.bartlomiejstepien.flothar.storage.entity.Rental;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalRepository
{
    List<Rental> getRentals(final LocalDateTime startDate, final LocalDateTime endDate);

    Rental getById(final Integer id);

    List<Rental> getByCustomerId(final Integer customerId);

    void save(Rental rental);

    List<Rental> getAllRentals();
}
