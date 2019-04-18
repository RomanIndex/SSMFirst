var URL_API = {};

var base_url = {
    "account": "/ssm/admin/account",
    "role": "/ssm/admin/role",
    "module": "/ssm/admin/module",
    "privilege": "/ssm/admin/privilege",
    "accountRole": "/ssm/admin/accountRole",
    "rolePrivilege": "/ssm/admin/rolePrivilege",
};

URL_API.ROLE_PRIVILEGE = {
    "getMenuTree": base_url.rolePrivilege+ "/menuTree",//根据 角色ID 获取 权限（所有菜单权限、已拥有的）
    "updateByRole": base_url.rolePrivilege+ "/updateByRole",//（混合）更新 角色 的 菜单权限
}

URL_API.ACCOUNT_ROLE = {
    "getRoleScope": base_url.accountRole+ "/roleScope",//根据 员工编号 获取 角色（已拥有、未拥有）
    "updateByAccount": base_url.accountRole+ "/updateByAccount",//（混合）更新 用户 的 角色
}

URL_API.ACCOUNT = {
    "query": base_url.account+ "/query",
    //"getVo": "/ssm/admin/account",// \/{empNo}
    "add": base_url.account+ "/add",
    "update": base_url.account+ "/update",
    "del": base_url.account+ "/del",
};

URL_API.ROLE = {
    "query": base_url.role+ "/jpaQuery",
    "add": base_url.role+ "/add",
    "update": base_url.role+ "/update",
    "del": base_url.role+ "/del",
    "getRoleByAccount": base_url.role+ "/account",
}

URL_API.MODULE = {
    "getTop": base_url.module+ "/top",
    "getSecond": base_url.module+ "/second",
    "getBtn": base_url.module+ "/btn",
    "add": base_url.module+ "/add",
    "update": base_url.module+ "/update",
    "del": base_url.module+ "/del",
}

URL_API.PRIVILEGE = {
    "query": base_url.privilege+ "/query",
    "add": base_url.privilege+ "/add",
    "update": base_url.privilege+ "/update",
    "del": base_url.privilege+ "/del",
    "operateList": base_url.privilege+ "/operateList",//操作类型 枚举转集合
}