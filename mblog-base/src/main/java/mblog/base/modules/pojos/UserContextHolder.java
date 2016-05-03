/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.modules.pojos;

/**
 * 用户上下文Holder和当前线程相关
 * 
 * @author langhsu
 * 
 */
public class UserContextHolder {
	private static ThreadLocal<UserProfile> local = new ThreadLocal<UserProfile>();

	public static UserProfile getUserProfile(){
		return (UserProfile)local.get();
	}
	
	public static void setUserProfile(UserProfile context){
		local.set(context);
	}
	
	public static void removeUserContext(){
		local.remove();
	}
}
