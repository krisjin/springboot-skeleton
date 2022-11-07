package org.yeyu.springboot.gateway.provider.service;

/**
 *
 */
public interface TokenService {
    /**
     * 根据星职场userId转为哥伦布token
     */
    String convert2ColumbusToken(String saasUserId);

    /**
     * 根据哥伦布userId转为星职场token
     */
    String convert2SaasToken(String columbusUserId);

}
