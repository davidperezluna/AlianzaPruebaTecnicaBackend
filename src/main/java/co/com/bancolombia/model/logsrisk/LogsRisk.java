package co.com.bancolombia.model.logsrisk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogsRisk {
    private Long id;
    private LocalDateTime dateTime;
    private String message;
}
