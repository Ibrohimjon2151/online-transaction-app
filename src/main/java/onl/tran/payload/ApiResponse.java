package onl.tran.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
 private String message;

 private boolean success;

 private Object data;

 public ApiResponse(boolean success, Object data) {
  this.success = success;
  this.data = data;
 }

 public ApiResponse(String message, boolean success) {
  this.message = message;
  this.success = success;
 }
}
