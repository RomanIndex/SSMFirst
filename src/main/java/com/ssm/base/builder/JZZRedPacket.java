package com.ssm.base.builder;

import java.math.BigDecimal;
import java.util.Date;

public class JZZRedPacket {
	
	private String publisherName;

    private String acceptName;

    private BigDecimal packetAmount;

    private Date pulishPacketTime;

    private Date openPacketTime;
    
    public JZZRedPacket() {
		// TODO Auto-generated constructor stub
	}

    public JZZRedPacket(String publisherName, String acceptName, BigDecimal packetAmount, Date pulishPacketTime, Date openPacketTime) {
        this.publisherName = publisherName;
        this.acceptName = acceptName;
        this.packetAmount = packetAmount;
        this.pulishPacketTime = pulishPacketTime;
        this.openPacketTime = openPacketTime;
    }

	public String getPublisherName() {
        return publisherName;
    }

    public String getAcceptName() {
        return acceptName;
    }

    public BigDecimal getPacketAmount() {
        return packetAmount;
    }

    public Date getPulishPacketTime() {
        return pulishPacketTime;
    }

    public Date getOpenPacketTime() {
        return openPacketTime;
    }

    public static  Builder getBulider(){
        return new Builder();
    }

    //Builder
    public static class Builder{

        private String publisherName;

        private String acceptName;

        private BigDecimal packetAmount;

        private Date pulishPacketTime;

        private Date openPacketTime;

        public JZZRedPacket build(){

            return new JZZRedPacket(publisherName,acceptName,packetAmount,pulishPacketTime,openPacketTime);
        }
        
        public Builder setPublisherName(String publisherName) {
            this.publisherName = publisherName;
            return this;
        }

        public Builder setAcceptName(String acceptName) {
            this.acceptName = acceptName;
            return this;
        }

        public Builder setPacketAmount(BigDecimal packetAmount) {
            this.packetAmount = packetAmount;
            return this;
        }

        public Builder setPulishPacketTime(Date pulishPacketTime) {
            this.pulishPacketTime = pulishPacketTime;
            return this;
        }

        public Builder setOpenPacketTime(Date openPacketTime) {
            this.openPacketTime = openPacketTime;
            return this;
        }

    }

}