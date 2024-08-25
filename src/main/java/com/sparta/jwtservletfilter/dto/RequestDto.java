package com.sparta.jwtservletfilter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 *  * @author JaeHwan Kim
 *  * @version 1.0
 *  * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class RequestDto {
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}
