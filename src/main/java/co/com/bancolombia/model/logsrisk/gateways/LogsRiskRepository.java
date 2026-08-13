package co.com.bancolombia.model.logsrisk.gateways;

import co.com.bancolombia.model.crudrequest.CustomQuery;
import co.com.bancolombia.model.logsrisk.LogsRisk;

import java.util.List;

public interface LogsRiskRepository {
    LogsRisk save(LogsRisk entity);

    List<LogsRisk> findAllCustomFilteredIdAsc(CustomQuery customQuery);

    List<LogsRisk> findAllCustomFilteredIdDesc(CustomQuery customQuery);

    List<LogsRisk> findAllCustomFilteredDateTimeAsc(CustomQuery customQuery);

    List<LogsRisk> findAllCustomFilteredDateTimeDesc(CustomQuery customQuery);

    List<LogsRisk> createLogs(List<LogsRisk> logsRiskList);
}
