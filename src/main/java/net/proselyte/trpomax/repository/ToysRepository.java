package net.proselyte.trpomax.repository;

import net.proselyte.trpomax.entity.Toys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ToysRepository extends JpaRepository<Toys, Integer> {

    @Query("SELECT t FROM Toys t WHERE t.price = (SELECT MAX(t2.price) FROM Toys t2)")
    Toys findMostExpensiveToy();

    @Query("SELECT t FROM Toys t WHERE t.price <= :price AND t.min >= :min AND t.max <= :max")
    List<Toys> findToysByPriceAndAgeRange(double price, int min, int max);

    @Query("SELECT t FROM Toys t WHERE t.min >= :min AND t.max <= :max")
    List<Toys> findToysByAgeRange(int min, int max);
}
