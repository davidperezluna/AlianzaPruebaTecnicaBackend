package co.com.bancolombia.jpa.logsrisk;

import co.com.bancolombia.model.crudrequest.CustomQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRiskJPARepository extends JpaRepository<LogsRiskData, Long> {
    List<LogsRiskData> findAllFilteredIdAsc(CustomQuery customQuery);

    List<LogsRiskData> findAllFilteredIdDesc(CustomQuery customQuery);

    List<LogsRiskData> findAllFilteredDateTimeAsc(CustomQuery customQuery);

    List<LogsRiskData> findAllFilteredDateTimeDesc(CustomQuery customQuery);
}
