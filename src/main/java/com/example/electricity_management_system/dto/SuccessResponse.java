package com.example.electricity_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuccessResponse<T> {
    public String message;
    public T data;
    public SuccessResponse(String message) {
        this.message = message;
    }


}
