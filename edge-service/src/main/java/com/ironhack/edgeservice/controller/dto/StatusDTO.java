package com.ironhack.edgeservice.controller.dto;

import java.util.Objects;

public class StatusDTO {

    private String status;

    public StatusDTO() {
    }

    public StatusDTO(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusDTO statusDTO = (StatusDTO) o;
        return status.equals(statusDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }
}
