package co.com.bancolombia.jpa.logsrisk;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LogsRiskData {
    @Id
    private Long id;
    private LocalDateTime dateTime;
    private String message;
}
