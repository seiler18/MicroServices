package cl.jesusseiler.store.shopping.exception;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter 
@Setter 
@Builder
public class ErrorMessage {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String code;
    private List<Map<String, String>> messages;
    private String path;
    
}
