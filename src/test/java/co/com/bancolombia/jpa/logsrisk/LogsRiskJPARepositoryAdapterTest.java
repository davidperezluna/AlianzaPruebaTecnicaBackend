package co.com.bancolombia.jpa.logsrisk;

import co.com.bancolombia.model.crudrequest.CustomQuery;
import co.com.bancolombia.model.logsrisk.LogsRisk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogsRiskJPARepositoryAdapterTest {

    @Mock
    private LogsRiskJPARepository repository;

    private LogsRiskJPARepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new LogsRiskJPARepositoryAdapter(repository, new LogsRiskObjectMapper());
    }

    @Test
    void saveShouldReturnMappedEntityNotNull() {
        LogsRisk entity = LogsRisk.builder()
                .id(1L)
                .message("riesgo-save")
                .dateTime(LocalDateTime.of(2026, 1, 1, 10, 0))
                .build();
        LogsRiskData persisted = toData(entity);
        when(repository.save(persisted)).thenReturn(persisted);

        LogsRisk result = adapter.save(entity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("riesgo-save", result.getMessage());
        assertEquals(entity.getDateTime(), result.getDateTime());
    }

    @Test
    void findAllCustomFilteredIdAscShouldReturnMappedListNotEmpty() {
        CustomQuery query = CustomQuery.builder().filter("id-asc").build();
        LogsRiskData data = data(11L, "asc-id");
        when(repository.findAllFilteredIdAsc(query)).thenReturn(List.of(data));

        List<LogsRisk> result = adapter.findAllCustomFilteredIdAsc(query);

        assertMappedSingleResult(result, 11L, "asc-id");
    }

    @Test
    void findAllCustomFilteredIdDescShouldReturnMappedListNotEmpty() {
        CustomQuery query = CustomQuery.builder().filter("id-desc").build();
        LogsRiskData data = data(22L, "desc-id");
        when(repository.findAllFilteredIdDesc(query)).thenReturn(List.of(data));

        List<LogsRisk> result = adapter.findAllCustomFilteredIdDesc(query);

        assertMappedSingleResult(result, 22L, "desc-id");
    }

    @Test
    void findAllCustomFilteredDateTimeAscShouldReturnMappedListNotEmpty() {
        CustomQuery query = CustomQuery.builder().filter("date-asc").build();
        LogsRiskData data = data(33L, "asc-date");
        when(repository.findAllFilteredDateTimeAsc(query)).thenReturn(List.of(data));

        List<LogsRisk> result = adapter.findAllCustomFilteredDateTimeAsc(query);

        assertMappedSingleResult(result, 33L, "asc-date");
    }

    @Test
    void findAllCustomFilteredDateTimeDescShouldReturnMappedListNotEmpty() {
        CustomQuery query = CustomQuery.builder().filter("date-desc").build();
        LogsRiskData data = data(44L, "desc-date");
        when(repository.findAllFilteredDateTimeDesc(query)).thenReturn(List.of(data));

        List<LogsRisk> result = adapter.findAllCustomFilteredDateTimeDesc(query);

        assertMappedSingleResult(result, 44L, "desc-date");
    }

    @Test
    void createLogsShouldSetDateTimeAndReturnPersistedLogs() {
        LogsRisk first = LogsRisk.builder().id(100L).message("log-1").build();
        LogsRisk second = LogsRisk.builder().id(200L).message("log-2").build();
        LocalDateTime before = LocalDateTime.now().minusSeconds(2);

        when(repository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        List<LogsRisk> result = adapter.createLogs(List.of(first, second));
        LocalDateTime after = LocalDateTime.now().plusSeconds(2);

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(100L, result.get(0).getId());
        assertEquals("log-1", result.get(0).getMessage());
        assertEquals(200L, result.get(1).getId());
        assertEquals("log-2", result.get(1).getMessage());
        assertDateTimeWasAssigned(result.get(0).getDateTime(), before, after);
        assertDateTimeWasAssigned(result.get(1).getDateTime(), before, after);
        assertDateTimeWasAssigned(first.getDateTime(), before, after);
        assertDateTimeWasAssigned(second.getDateTime(), before, after);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<LogsRiskData>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).saveAll(captor.capture());
        List<LogsRiskData> persisted = captor.getValue();
        assertEquals(2, persisted.size());
        assertDateTimeWasAssigned(persisted.get(0).getDateTime(), before, after);
        assertDateTimeWasAssigned(persisted.get(1).getDateTime(), before, after);
        assertEquals(100L, persisted.get(0).getId());
        assertEquals(200L, persisted.get(1).getId());
    }

    private static void assertMappedSingleResult(List<LogsRisk> result, Long expectedId, String expectedMessage) {
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertNotNull(result.get(0));
        assertEquals(expectedId, result.get(0).getId());
        assertEquals(expectedMessage, result.get(0).getMessage());
    }

    private static void assertDateTimeWasAssigned(LocalDateTime dateTime, LocalDateTime before, LocalDateTime after) {
        assertNotNull(dateTime);
        assertFalse(dateTime.isBefore(before.truncatedTo(ChronoUnit.SECONDS)));
        assertFalse(dateTime.isAfter(after));
    }

    private static LogsRiskData data(Long id, String message) {
        return LogsRiskData.builder()
                .id(id)
                .message(message)
                .dateTime(LocalDateTime.of(2026, 8, 13, 12, 0))
                .build();
    }

    private static LogsRiskData toData(LogsRisk entity) {
        return LogsRiskData.builder()
                .id(entity.getId())
                .message(entity.getMessage())
                .dateTime(entity.getDateTime())
                .build();
    }

    private static final class LogsRiskObjectMapper implements ObjectMapper {
        @Override
        @SuppressWarnings("unchecked")
        public <T> T map(Object source, Class<T> target) {
            if (source instanceof LogsRiskData data && target == LogsRisk.class) {
                return (T) LogsRisk.builder()
                        .id(data.getId())
                        .dateTime(data.getDateTime())
                        .message(data.getMessage())
                        .build();
            }
            if (source instanceof LogsRisk entity && target == LogsRiskData.class) {
                return (T) LogsRiskData.builder()
                        .id(entity.getId())
                        .dateTime(entity.getDateTime())
                        .message(entity.getMessage())
                        .build();
            }
            throw new IllegalArgumentException("Unsupported mapping from " + source + " to " + target);
        }
    }
}
