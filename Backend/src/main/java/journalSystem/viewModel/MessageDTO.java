package journalSystem.viewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private int id;
    private String title;
    private String text;
    private LocalDateTime dateTime;
    private boolean isRead;
    private String sender;
    private String receiver;

    public boolean getIsRead(){
        return isRead;
    }
}
