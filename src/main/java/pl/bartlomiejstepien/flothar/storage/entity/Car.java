package pl.bartlomiejstepien.flothar.storage.entity;

import javax.persistence.*;

@Entity
@Table(name = "car")
public class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "plates", unique = true, nullable = false, length = 12)
    private String plates;

    @Column(name = "vin", unique = true, nullable = false)
    private String vin;

    @Column(name = "has_insurance", nullable = false)
    private boolean hasInsurance;
}
