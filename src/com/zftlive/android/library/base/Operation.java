package com.zftlive.android.library.base;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zftlive.android.R;
import com.zftlive.android.library.MApplication;
import com.zftlive.android.library.config.SysEnv;
import com.zftlive.android.library.data.DTO;
import com.zftlive.android.library.tools.ToolAlert;
import com.zftlive.android.library.tools.ToolAlert.ILoadingOnKeyListener;

/**
 * 基本的操作共通抽取
 * @author 曾繁添
 * @version 1.0
 *
 */
public class Operation {

	/**激活Activity组件意图**/
	private Intent mIntent = new Intent();
	/***上下文**/
	private Activity mContext = null;
	/***整个应用Applicaiton**/
	private MApplication mApplication = null;
	
	public Operation(Activity mContext) {
		this.mContext = mContext;
		mApplication = (MApplication) this.mContext.getApplicationContext();
	}

	/**
	 * 跳转Activity
	 * @param activity 需要跳转至的Activity
	 */
	public void forward(Class activity) {
		forward(activity.getName());
	}
	
	/**
	 * 跳转Activity
	 * @param activity 需要跳转至的Activity
	 */
	public void forward(String className) {
		forward(className,IBaseActivity.NONE);
	}
	
	/**
	 * 跳转Activity
	 * @param activity 需要跳转至的Activity
	 * @param animaType 动画类型IBaseActivity.LEFT_RIGHT/TOP_BOTTOM/FADE_IN_OUT
	 */
	public void forward(String className,int animaType) {
		mIntent.setClassName(mContext, className);
		mIntent.putExtra(IBaseActivity.ANIMATION_TYPE, animaType);
		mContext.startActivity(mIntent);
		switch (animaType) {
		case IBaseActivity.LEFT_RIGHT:
			mContext.overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
			break;
		case IBaseActivity.TOP_BOTTOM:
			mContext.overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
			break;
		case IBaseActivity.FADE_IN_OUT:
			mContext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 设置传递参数
	 * @param value 数据传输对象
	 */
	public void addParameter(DTO value) {
		mIntent.putExtra(SysEnv.ACTIVITY_DTO_KEY, value);
	}
	
	/**
	 * 设置传递参数
	 * @param key 参数key
	 * @param value 数据传输对象
	 */
	public void addParameter(String key,DTO value) {
		mIntent.putExtra(key, value);
	}
	
	/**
	 * 设置传递参数
	 * @param key 参数key
	 * @param value 数据传输对象
	 */
	public void addParameter(String key,Bundle value) {
		mIntent.putExtra(key, value);
	}
	
	/**
	 * 设置传递参数
	 * @param key 参数key
	 * @param value 数据传输对象
	 */
	public void addParameter(String key,Serializable value) {
		mIntent.putExtra(key, value);
	}
	
	/**
	 * 设置传递参数
	 * @param key 参数key
	 * @param value 数据传输对象
	 */
	public void addParameter(String key,String value) {
		mIntent.putExtra(key, value);
	}
	
	/**
	 * 获取跳转时设置的参数
	 * @param key
	 * @return
	 */
	public Object getParameters(String key) {
		DTO parms = getParameters();
		if(null != parms){
			return parms.get(key);
		}else{
			parms = new DTO();
			parms.put(key, mContext.getIntent().getExtras().get(key));
		}
		return parms;
	}
	
	/**
	 * 获取跳转参数集合
	 * @return
	 */
	public DTO getParameters() {
		DTO parms = (DTO)mContext.getIntent().getExtras().getSerializable(SysEnv.ACTIVITY_DTO_KEY);
		return parms;
	}
	
	/**
	 * 设置全局Application传递参数
	 * @param strKey 参数key
	 * @param value 数据传输对象
	 */
	public void addGloableAttribute(String strKey,Object value) {
		mApplication.assignData(strKey, value);
	}
	
	/**
	 * 获取跳转时设置的参数
	 * @param strKey
	 * @return
	 */
	public Object getGloableAttribute(String strKey) {
		return mApplication.gainData(strKey);
	}
	
	/**
	 * 弹出等待对话框
	 * @param message 提示信息
	 */
	public void showLoading(String message){
		ToolAlert.loading(mContext, message);
	}
	
	/**
	 * 弹出等待对话框
	 * @param message 提示信息
	 * @param listener 按键监听器
	 */
	public void showLoading(String message,ILoadingOnKeyListener listener){
		ToolAlert.loading(mContext, message, listener);
	}
	
	/**
	 * 更新等待对话框显示文本
	 * @param message 需要更新的文本内容
	 */
	public void updateLoadingText(String message){
		ToolAlert.updateProgressText(message);
	}
	
	/**
	 * 关闭等待对话框
	 */
	public void closeLoading(){
		ToolAlert.closeLoading();
	}
	
}
