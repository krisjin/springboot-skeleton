package org.yeyu.springboot.gateway.provider.service.impl;

import org.springframework.stereotype.Component;
import org.yeyu.springboot.gateway.provider.service.TokenService;

@Component
public class TokenServiceImpl implements TokenService {

    @Override
    public String convert2ColumbusToken(String saasUserId) {
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiNzE2NDIxIiwiZXhwIjoxNjY0NjcyNTY0LCJkb21haW4iOiJodHRwczpcL1wvY29sdW1idXMtYXBwLWFwaS10ZXN0Lmx1Y2FzZ2Noci5jb21cLyJ9.NptNqWaIvVY0mi7a7q-CzW8b43m1qs0Htt88VMOtREY";
    }

    @Override
    public String convert2SaasToken(String columbusUserId) {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiNzcxIiwiZXhwIjoxNjY0NjcyMTU2LCJkb21haW4iOiJodHRwczpcL1wvc2Fhcy1hcHAtYXBpLXRlc3QubHVjYXNnY2hyLmNvbVwvIn0.GrV5TWmuhbuMHbIMnP_HH_CeDY1s8BQCfpRCeFJZ0_c";
    }


}
