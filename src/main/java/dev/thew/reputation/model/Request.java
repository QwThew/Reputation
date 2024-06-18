package dev.thew.reputation.model;

import dev.thew.reputation.model.enums.DataType;
import dev.thew.reputation.model.enums.EventType;
import dev.thew.reputation.model.enums.If;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
public class Request {

    EventType type;
    DataType dataType;
    Object data;
    List<If> ifs;
    Payment payment;
    double random;

    public <Z> boolean isMatched(Z value) {
        return !dataType.check(data, value);
    }

    public boolean randomOff(){
        return random <= 0;
    }
}
