package co.com.bancolombia.jpa.logsrisk;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.crudrequest.CustomQuery;
import co.com.bancolombia.model.logsrisk.LogsRisk;
import co.com.bancolombia.model.logsrisk.gateways.LogsRiskRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LogsRiskJPARepositoryAdapter extends AdapterOperations
        <LogsRisk, LogsRiskData, Long, LogsRiskJPARepository>
        implements LogsRiskRepository {

    public LogsRiskJPARepositoryAdapter(LogsRiskJPARepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, LogsRisk.class));
    }

    @Override
    public LogsRisk save(LogsRisk entity) {
        return super.save(entity);
    }

    @Override
    public List<LogsRisk> findAllCustomFilteredIdAsc(CustomQuery customQuery) {
        return super.toList(repository.findAllFilteredIdAsc(customQuery));
    }

    @Override
    public List<LogsRisk> findAllCustomFilteredIdDesc(CustomQuery customQuery) {
        return super.toList(repository.findAllFilteredIdDesc(customQuery));
    }

    @Override
    public List<LogsRisk> findAllCustomFilteredDateTimeAsc(CustomQuery customQuery) {
        return super.toList(repository.findAllFilteredDateTimeAsc(customQuery));
    }

    @Override
    public List<LogsRisk> findAllCustomFilteredDateTimeDesc(CustomQuery customQuery) {
        return super.toList(repository.findAllFilteredDateTimeDesc(customQuery));
    }

    @Override
    public List<LogsRisk> createLogs(List<LogsRisk> logsRiskList) {
        List<LogsRisk> logsRiskListToSave = new ArrayList<>();
        for (LogsRisk logRisk: logsRiskList) {
            logRisk.setDateTime(LocalDateTime.now());
            logsRiskListToSave.add(logRisk);
        }
        return super.saveAllEntities(logsRiskList);
    }
}
