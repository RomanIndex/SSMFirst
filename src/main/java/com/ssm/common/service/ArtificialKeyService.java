package com.ssm.common.service;

import com.ssm.common.enumeration.ArtificialKeyEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ArtificialKeyService {
    //@Autowired private RedisDao redis;

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
