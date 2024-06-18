package dev.thew.reputation.model;

import dev.thew.reputation.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Payment {
    int count;
    PaymentType type;
}
