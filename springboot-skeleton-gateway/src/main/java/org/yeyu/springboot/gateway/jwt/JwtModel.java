package org.yeyu.springboot.gateway.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtModel {

    private int user_id;

    private long exp;

    private String domain;

}
