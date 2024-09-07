package onl.tran.payload.sms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

 private List<Destination> destinations;

 private String from;

 private String text;
}