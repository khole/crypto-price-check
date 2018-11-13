package sbi.repositories;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import sbi.model.TickerData;

@EnableScan
public interface TickerDataRepository extends CrudRepository<TickerData, String> {
    Optional<TickerData> findByPair(String pair);
}
