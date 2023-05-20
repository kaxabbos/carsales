package com.carsales.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    ADMIN("Управляющий"),
    USER("Заказчик"),
    MANAGER("Менеджер");

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}
