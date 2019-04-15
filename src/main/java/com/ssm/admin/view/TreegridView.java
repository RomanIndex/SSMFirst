package com.ssm.admin.view;

public class TreegridView {
	
	private Integer status;
	
	private Integer type;
	
	private String id;
	
	private String moduleId;
	
	private String state = "open";//是否展开，默认为open，可以为closed
	
	private String iconCls = "icon-city";//选项前面的图标，如果自己不设定，父级节点默认为文件夹图标，子级节点为文件图标（这里应该根据type的值来显示）
	
	private boolean checked;//是否选中（用于复选框）（这个角色是否拥有该菜单的show权限）
	
	/**
	 * （必须）：记得前面有“_” ，他是用来记录父级节点，没有这个属性，是没法展示父级节点；其次就是这个父级节点必须存在，不然信息也是展示不出来，
		在后台遍历组合的时候，如果父级节点不存在或为0时，此时 _parentId 应该不赋值。如果赋值 “0” 则显示不出来
		state：是否展开
	 */
	private String _parentId;
	
	private String name;
	
	private Integer seq;
	
	private String url;
	
	//扩展几个和权限有关字段
	private boolean isShowTicket;//模块 是否 已经生成 operate = show 的权限票据
	
	//1、不生成show票据的都是普通页面，向全部角色展示；2、重要页面，加show票据管理，拿到show票据的才有查看权限
	private boolean getShowTicket;//若module已有show 票据，是否获得

	public boolean isShowTicket() {
		return isShowTicket;
	}

	public void setShowTicket(boolean isShowTicket) {
		this.isShowTicket = isShowTicket;
	}

	public boolean isGetShowTicket() {
		return getShowTicket;
	}

	public void setGetShowTicket(boolean getShowTicket) {
		this.getShowTicket = getShowTicket;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String get_parentId() {
		return _parentId;
	}

	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
