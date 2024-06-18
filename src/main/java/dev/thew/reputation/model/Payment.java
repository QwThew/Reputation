package dev.thew.reputation.model;

import dev.thew.reputation.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Payment {
    private int count;
    private PaymentType type;
}
