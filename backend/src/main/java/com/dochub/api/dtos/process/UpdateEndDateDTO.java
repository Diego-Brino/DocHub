package com.dochub.api.dtos.process;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NonNull;

import java.util.Date;

public record UpdateEndDateDTO(
    @NonNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date endDate
) {
}