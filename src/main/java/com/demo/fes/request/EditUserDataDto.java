package com.demo.fes.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class EditUserDataDto implements Serializable {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String country;
        private String date;
        private String city;
    }


