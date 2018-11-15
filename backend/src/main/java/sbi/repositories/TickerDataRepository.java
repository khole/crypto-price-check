package sbi.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import sbi.model.TickerData;

@EnableScan
public interface TickerDataRepository extends CrudRepository<TickerData, String> {
   Iterable<TickerData> findByPair(String pair);
   
   TickerData findFirstByPairOrderByTicktimeDesc(String pair);
   
   Iterable<TickerData> findByTicktime(String ticktime);
}
