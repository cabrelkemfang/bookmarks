package grow.together.io.bookmarks.dtoModel;

import grow.together.io.bookmarks.domain.Subscriber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberDtoOut {
    private String email;
    private String name;

    public SubscriberDtoOut(Subscriber subscriber) {
        this.email = subscriber.getEmail();
        this.name = subscriber.getName();
    }
}
