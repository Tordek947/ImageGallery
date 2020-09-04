package net.atlassian.cmathtutor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgileEngineAuthResponseData {

    private Boolean auth;
    private String token;
}
