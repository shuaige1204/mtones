/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.modules.lang;

public interface Const {
	
	String KEY_LOGIN = "profile.login";
	String KEY_SESSIONID = "sessionId";
	String KEY_ADMIN_LOGIN = "profile.admin";
	
	// 预置超级管理员的ID
	long ADMIN_ID = 1;

	// 预置超级管理员的角色名
	String ROLE_ADMIN = "admin";

	// 忽略值
	int IGNORE = -1;
	
	int ZERO = 0;

	// 正常状态
	int STATUS_NORMAL = 0;

	// 禁用状态
	int STATUS_CLOSED = 1;

	// 删除状态
	int STATUS_REMOVED = 2;

	// 未审核
	int AUDIT_INIT = 0;

	// 审核通过
	int AUDIT_PASSED = 1;

	// 已驳回
	int AUDIT_REJECTED = 2;
	
}
