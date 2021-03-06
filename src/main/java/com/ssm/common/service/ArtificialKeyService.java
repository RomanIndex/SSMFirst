package com.ssm.common.service;

import com.ssm.common.enumeration.ArtificialKeyEnum;
import com.ssm.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ArtificialKeyService {
    //@Autowired private RedisDao redis;

    //测试自定义生成主键，临时用
    public String getCustomUUIDByClass(Object object) {
        ArtificialKeyEnum keyEnum = ArtificialKeyEnum.getEnumByClass(object.getClass());
        int sixNumber = (int) (Math.random() * 1000000);
        String id = keyEnum.getPrefix()+ sixNumber;
        return id;
    }

    public String getKeyByTableEnum(ArtificialKeyEnum enumKey) {
        String redisTableKey = "MAJOR_NO_"+ enumKey.getTable();
        String redisTableValue = "";

        /*if (StringUtils.isNotBlank(redisTableKey) && redis.hasKey(redisTableKey)){
            redisTableValue = redis.getValue(redisTableKey);
            redisTableValue = nextTableValue(redisTableValue, enumKey);
        }else{
            redisTableValue = defaultTableValue(enumKey);
        }
        redis.setKey(redisTableKey, redisTableValue);*/

        return redisTableValue;
    }

    private String nextTableValue(String redisTableValue, ArtificialKeyEnum enumKey) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(redisTableValue);
        int nextNum = Integer.parseInt(m.replaceAll("").trim()) + 1;
        String format = "%s%0"+ enumKey.getLimit() +"d";
        return String.format(format, enumKey.getPrefix(), nextNum);
    }

    private String defaultTableValue(ArtificialKeyEnum enumKey) {
        int beginNum = 1;
        String format = "%s%0"+ enumKey.getLimit() +"d";
        return String.format(format, enumKey.getPrefix(), beginNum);
    }

}
