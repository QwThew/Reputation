package dev.thew.reputation.model.enums;

import dev.thew.reputation.model.User;
import lombok.NonNull;

public enum PaymentType {

    INCREASE{
        @Override
        public void give(@NonNull int count,@NonNull User user) {
            user.increaseReputation(count);

        }
    }, DECREASE{
        @Override
        public void give(@NonNull int count, @NonNull User user) {
            user.decreaseReputation(count);
        }
    };

    public abstract void give(@NonNull int count,@NonNull User user);
}
