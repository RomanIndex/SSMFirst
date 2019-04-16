var URL_API = {};

URL_API.ACCOUNT = {
    "query": "/ssm/admin/account/query",
    //"getVo": "/ssm/admin/account",// \/{empNo}
    "add": "/ssm/admin/account/add",
    "update": "/ssm/admin/account/update",
    "del": "/ssm/admin/account/del",
};

URL_API.ROLE = {
    "query": "/ssm/admin/role/jpaQuery",
    "add": "/ssm/admin/role/add",
    "update": "/ssm/admin/role/update",
    "del": "/ssm/admin/role/del",
    "getRoleByAccount": "/ssm/admin/role/account",
    "getRoleBranch": "/ssm/admin/role/branch",//包括员工 已有 和 尚未拥有 的
}

URL_API.MODULE = {
    "getTop": "/ssm/admin/module/top",
    "getSecond": "/ssm/admin/module/second",
    "getBtn": "/ssm/admin/module/btn",
    "add": "/ssm/admin/module/add",
    "update": "/ssm/admin/module/update",
    "del": "/ssm/admin/module/del",
    "getTree": "/ssm/admin/module/tree",
}

URL_API.PRIVILEGE = {
    "query": "/ssm/admin/privilege/query",
    "add": "/ssm/admin/privilege/add",
    "update": "/ssm/admin/privilege/update",
    "del": "/ssm/admin/privilege/del",
    "operateList": "/ssm/admin/privilege/operateList",//操作类型集合
    "getPrivilegeByRole": "/ssm/admin/privilege/role",//角色拥有的权限
}