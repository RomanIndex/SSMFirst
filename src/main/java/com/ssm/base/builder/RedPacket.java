package com.ssm.base.builder;

import java.math.BigDecimal;
import java.util.Date;

public class RedPacket {
	private String publisherName; //发包人

    private String acceptName; //手包人

    private BigDecimal packetAmount; //红包金额

    private int packetType; //红包类型

    private Date pulishPacketTime; //发包时间

    private Date openPacketTime; //抢包时间

	public RedPacket(String publisherName, String acceptName, BigDecimal packetAmount, int packetType,
			Date pulishPacketTime, Date openPacketTime) {
		super();
		this.publisherName = publisherName;
		this.acceptName = acceptName;
		this.packetAmount = packetAmount;
		this.packetType = packetType;
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

	public int getPacketType() {
		return packetType;
	}

	public Date getPulishPacketTime() {
		return pulishPacketTime;
	}

	public Date getOpenPacketTime() {
		return openPacketTime;
	}

}
