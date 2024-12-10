package onl.tran.payload;

import java.time.LocalDateTime;

public record TaskDto(String title, String description, LocalDateTime deadLine) {
}
