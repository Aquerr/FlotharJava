package pl.bartlomiejstepien.flothar.service;

import pl.bartlomiejstepien.flothar.storage.RentalRepository;
import pl.bartlomiejstepien.flothar.storage.RentalRepositoryImpl;
import pl.bartlomiejstepien.flothar.storage.entity.Rental;

import java.util.List;

public class RentalService
{
    private static final RentalService INSTANCE = new RentalService();

    private final  RentalRepository rentalRepository = new RentalRepositoryImpl();

    public Rental getRentalById(final Integer id)
    {
        return this.rentalRepository.getById(id);
    }

    public void saveRental(Rental newRental)
    {
        this.rentalRepository.save(newRental);
    }

    public List<Rental> getAllRentals()
    {
        return this.rentalRepository.getAllRentals();
    }
}
