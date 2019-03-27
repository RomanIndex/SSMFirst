package com.ssm.base.builder;

import java.math.BigDecimal;
import java.util.Date;

public class ConcreteBuilder implements Builder{
	
	private String publisherName;

    private String acceptName;

    private BigDecimal packetAmount;
    
    private int packetType;

    private Date pulishPacketTime;

    private Date openPacketTime;
    
    public static ConcreteBuilder getBulider(){
        return new ConcreteBuilder();
    }
    
    public RedPacket build() {
        return new RedPacket(publisherName, acceptName, packetAmount, packetType, pulishPacketTime, openPacketTime);
    }

    @Override
    public Builder setPublisherName(String publishName) {
        this.publisherName = publishName;
        return this;
    }

    @Override
    public Builder setAcceptName(String acceptName) {
       this.acceptName = acceptName;
       return this;
    }

    @Override
    public Builder setPacketAmount(BigDecimal packetAmount) {
       this.packetAmount = packetAmount;
       return this;
    }

    @Override
    public Builder setPacketType(int packetType) {
        this.packetType = packetType;
        return this;
    }

    @Override
    public Builder setPulishPacketTime(Date pushlishPacketTime) {
       this.pulishPacketTime = pushlishPacketTime;
        return this;
    }

    @Override
    public Builder setOpenPacketTime(Date openPacketTime) {
      this.openPacketTime = openPacketTime;
        return this;
    }


}
