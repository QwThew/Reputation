package dev.thew.reputation.model;

import dev.thew.reputation.model.enums.DataType;
import dev.thew.reputation.model.enums.EventType;
import dev.thew.reputation.model.enums.If;
import dev.thew.reputation.model.enums.PaymentType;
import lombok.*;

import java.util.List;
import java.util.Random;

@Builder
@Getter
@Setter
public class Request {

    private EventType type;
    private DataType dataType;
    private Object data;
    private List<If> ifs;
    private Payment payment;
    private double random;

    public <Z> boolean isMatched(Z value) {
        return !dataType.check(data, value);
    }

    public boolean randomOff(){
        return random <= 0;
    }

    public boolean random(Random random){
        return random.nextDouble() <= this.random;
    }

    public void reward(@NonNull User user) {
        int count = payment.getCount();

        PaymentType paymentType = payment.getType();
        paymentType.give(count, user);
    }



}
