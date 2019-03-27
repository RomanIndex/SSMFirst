package com.ssm.base.builder;

import java.math.BigDecimal;
import java.util.Date;

public interface Builder {
	
	Builder setPublisherName(String publishName);

    Builder setAcceptName(String acceptName);

    Builder setPacketAmount(BigDecimal packetAmount);

    Builder setPacketType(int packetType);

    Builder setPulishPacketTime(Date pushlishPacketTime);

    Builder setOpenPacketTime(Date openPacketTime);

    RedPacket build();

}
