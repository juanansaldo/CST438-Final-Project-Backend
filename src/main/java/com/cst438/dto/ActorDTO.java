package com.cst438.dto;

import java.time.LocalDate;

public record ActorDTO(int actorId, String name, LocalDate dob, String portrait, String about) {


}
