package org.yeyu.springboot.gateway.enums;

/**
 * @author kris
 * @date 2022/9/16
 */
public enum JwtSecret {

    STAR("7U3qi#OQhjzBMrgs"), COLUMBUS("7U3qi#OQhjzBMrgs");

    String secret;

    JwtSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}
