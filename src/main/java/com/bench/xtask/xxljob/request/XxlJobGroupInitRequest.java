package com.bench.xtask.xxljob.request;

import lombok.Data;

/**
 * @className XxlJobGropuInit
 * @autor cold
 * @DATE 2021/6/29 12:13
 **/
@Data
public class XxlJobGroupInitRequest  {
    //执行器的名称
    private String appName;
    //执行器的标题
    private String title;
}
