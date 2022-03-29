package edu.agh.sharedshoppinglist.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginResponse {
    String sessionId;
}
