package com.zhuanyi.hexo.core;

import com.zhuanyi.hexo.common.dto.Context;
import com.zhuanyi.hexo.core.dto.GatewayRequest;
import com.zhuanyi.hexo.common.dto.Result;

public interface GatewayService {

    ResourceHandler route(Context context);

    Result<?> invoke(GatewayRequest request);

}
