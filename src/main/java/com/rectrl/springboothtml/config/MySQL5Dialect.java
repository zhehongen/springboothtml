package com.rectrl.springboothtml.config;

import org.hibernate.dialect.MySQL55Dialect;

/**
 * hibernate自动建表的编码格式
 *
 * @author yangfeng
 * @date 2018年10月22日 下午19:36:25
 * Email: Feng.Yang@things-matrix.com
 */
public class MySQL5Dialect extends MySQL55Dialect {

    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8";
    }
}
