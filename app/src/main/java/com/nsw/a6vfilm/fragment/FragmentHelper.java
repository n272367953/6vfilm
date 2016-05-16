/*
 *	Copyright (c) 2015, Red Finance Information Technologies
 *	All rights reserved.
 *  
 *  @author: liujunbo	
 */
package com.nsw.a6vfilm.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FragmentHelper {
	
	public static final String TAG = FragmentHelper.class.getSimpleName();
	
	private static final String STATE_IDS = ".fragment_ids";
	private static final String STATE_LAST_FRAGMENT_ID =  ".last_fragment_id";
	private static final String STATE_CURRENT_FRAGMENT_ID = ".current_fragment_id";
	private static final String STATE_CURRENT_FRAGMENT = ".current_fragment";

	private Context context;
	private FragmentManager manager;
	private int containerId;
	
	private Map<String, SoftReference<Fragment>> fragments = new HashMap<String, SoftReference<Fragment>>();
	private String lastFragmentId = null;
	private String currentFragmentId = null;
	private Fragment currentFragment = null;
	
	/**
	 * 
	 * @param context 上下文
	 * @param manager 依附于activity的FragmentManager
	 * @param containerId Activity中包裹此Fragment的ViewGroup Id
	 */
	public FragmentHelper(Context context, FragmentManager manager, int containerId) {
		this.context = context;
		this.manager = manager;
		this.containerId = containerId;
	}
	
	/**
	 * 根据唯一标识获取Fagement
	 * @param fragmentId 唯一标识
	 * @return 若fragments中包含此fragment，未被回收，则返回此fragment否则返null
	 */
	public Fragment getFragmentFromManager(String fragmentId) {
		Fragment fragment = manager.findFragmentByTag(fragmentId);
		return fragment;
	}
	
	/**
	 * 根据唯一标识获取Fagement
	 * @param fragmentId 唯一标识
	 * @return 若fragments中包含此fragment，未被回收，则返回此fragment否则返null
	 */
	public Fragment getFragmentFromMap(String fragmentId) {
		Fragment fragment = null;
		SoftReference<Fragment> reference = fragments.get(fragmentId);
		if(reference != null) {
			fragment = reference.get();
		}
		return fragment;
	}
	
	/**
	 * 
	 * @return 当前使用的fragment标识
	 */
	public String getCurrentFragmentId() {
		return currentFragmentId;
	}

	/**
	 * 获取当前使用的fragment
	 * @return 若fragments中包含此fragment，未被回收，则返回此fragment否则返null
	 */
	public Fragment getCurrentFragment() {
		return getFragmentFromMap(getCurrentFragmentId());
	}

	/**
	 * 切换fragment
	 * @param fragmentId 唯一标识
	 * @param intent
	 */
	public void switchFragment(String fragmentId, Intent intent) {
		switchFragment(fragmentId, intent, -1);
	}
	
//	/**
//	 * 切换fragment
//	 * @param newFragmentId 唯一标识
//	 * @param intent
//	 * @param transition
//	 */
//	public void switchFragment(String newFragmentId, Intent intent, int transition) {
//		try {
//			Fragment newFragment = getFragment(newFragmentId);
//			if(currentFragment == null || currentFragment != newFragment) {
//				FragmentTransaction ft = manager.beginTransaction();
//				if(currentFragment != null) {
//					ft.detach(currentFragment);//分离当前fragment
//				}
//				if(newFragment == null) {//新的fragment不在数据集中
//					if(intent != null) {
//						String fname = intent.getComponent().getClassName();
//						Bundle args = intent.getExtras();
//						newFragment = Fragment.instantiate(context, fname);//实例化一个新的fragment
//						if(args != null) {
//							newFragment.setArguments(args);
//						}
//						fragments.put(newFragmentId, new SoftReference<Fragment>(newFragment));//将新的fragment放入数据集
//						ft.add(containerId, newFragment, newFragmentId);
//					}
//					
//					if(newFragment == null) {//实例化失败直接返回
//						return;
//					}
//					
//				} else {//新的fragment在数据集中
//					ft.attach(newFragment);//新fragment附着
//				}
//				
//				if(transition != -1) {
//					ft.setTransition(transition);
//				}
//				
//				lastFragmentId = currentFragmentId;//设置最近使用的fragment标识为当前fragment标识
//				currentFragment = newFragment;//设置最近使用的fragment为新fragment
//				currentFragmentId = newFragmentId;//设置当前fragment标识为新fragment标识
////				ft.commit();
//				ft.commitAllowingStateLoss();
////				mManager.executePendingTransactions();
//			}
//		} catch (Exception e) {
//			Logger.e(TAG, e.getMessage(), e);
//		}
//	}
	
	
	/**
	 * 切换fragment
	 * @param newFragmentId 唯一标识
	 * @param intent
	 * @param transition
	 */
	public void switchFragment(String newFragmentId, Intent intent, int transition) {
		try {
			Fragment newFragment = getFragmentFromMap(newFragmentId);//从map中取出fragment
			
			//如果新的fragment不在数据集中或已经被回收，创建一个fragment,保存到HashMap中
			if(newFragment == null) {
				if(intent != null) {
					String fname = intent.getComponent().getClassName();
					Bundle args = intent.getExtras();
					newFragment = Fragment.instantiate(context, fname);//实例化一个新的fragment
					if(args != null) {
						newFragment.setArguments(args);
					}
					fragments.put(newFragmentId, new SoftReference<Fragment>(newFragment));//将新的fragment放入数据集
				}
			}
			
			//如果manager中存在此id,但对象已不是同一个，删除manager中的Fragment
			Fragment fragmentFromManager = getFragmentFromManager(newFragmentId);
			if(fragmentFromManager != null && fragmentFromManager != newFragment){
				FragmentTransaction ft = manager.beginTransaction();
				ft.remove(fragmentFromManager);
				ft.commitAllowingStateLoss();
			}
			
			if(currentFragment == null || !newFragmentId.equals(currentFragment.getTag())) {//当前碎片为空或当前碎片不是将要加载的碎片
				FragmentTransaction ft = manager.beginTransaction();
				if(currentFragment != null) {
					ft.hide(currentFragment);//隐藏当前fragment
				}
				
				if(newFragment.isAdded()){//如果已添加，显示
					ft.show(newFragment);
				}else{//未添加，添加
					ft.add(containerId, newFragment, newFragmentId);
				}
				
//				if(transition != -1) {
//					ft.setTransition(transition);
//				}
				
				lastFragmentId = currentFragmentId;//设置最近使用的fragment标识为当前fragment标识
				currentFragment = newFragment;//设置最近使用的fragment为新fragment
				currentFragmentId = newFragmentId;//设置当前fragment标识为新fragment标识
				ft.commitAllowingStateLoss();
			}
		} catch (Exception e) {
//			Logger.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * 返回到上一个Fragment（只能回退一步）
	 * @return 是否跳转回去
	 */
	public boolean back() {
		if(lastFragmentId != null) {
			switchFragment(lastFragmentId, null, FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			lastFragmentId = null;
			return true;
		}
		return false;
	}
	
	/**
	 * 移除currentFragment，并清空数据集中的fragment
	 */
	public void clearFragments() {
		if(currentFragment != null) {
			manager.beginTransaction().remove(currentFragment).commit();
		}
		fragments.clear();
		currentFragmentId = null;
		currentFragment = null;
	}
	
	/**
	 * 在Bundle中保存状态，所有fragment
	 * @param bundle
	 */
	public void saveState(Bundle bundle) {
		if(bundle == null) {
			return;
		}
		ArrayList<String> idList = new ArrayList<String>(fragments.keySet());
		bundle.putStringArrayList(STATE_IDS, idList);
		
		Iterator<Entry<String, SoftReference<Fragment>>> iter = fragments.entrySet().iterator();
		Entry<String, SoftReference<Fragment>> entry;
		SoftReference<Fragment> reference;
		Fragment fragment;
		while (iter.hasNext()) {
			entry = iter.next();
			reference = entry.getValue();
			if(reference != null) {
				fragment = reference.get();
				if(fragment != null) {
					manager.putFragment(bundle, entry.getKey(), fragment);
				}
			}
		}
		
		if(lastFragmentId != null) {
			bundle.putString(STATE_LAST_FRAGMENT_ID, lastFragmentId);
		}
		
		if(currentFragmentId != null) {
			bundle.putString(STATE_CURRENT_FRAGMENT_ID, currentFragmentId);
		}
		
		if(currentFragment != null) {
			manager.putFragment(bundle, STATE_CURRENT_FRAGMENT, currentFragment);
		}
	}
	
	/**
	 * 在Bundle中保存状态，只保存当前的fragment
	 * @param bundle
	 */
	public void saveStateSimple(Bundle bundle) {
		if(bundle == null) {
			return;
		}
		
		if(currentFragmentId != null) {
			bundle.putString(STATE_CURRENT_FRAGMENT_ID, currentFragmentId);
			
			ArrayList<String> idList = new ArrayList<String>(1);
			idList.add(currentFragmentId);
			bundle.putStringArrayList(STATE_IDS, idList);
			
			Fragment currentFragment = getCurrentFragment();
			if(currentFragment != null) {
				manager.putFragment(bundle, currentFragmentId, currentFragment);
			}
		}
		
		if(lastFragmentId != null) {
			bundle.putString(STATE_LAST_FRAGMENT_ID, lastFragmentId);
		}
		
		if(currentFragment != null) {
			manager.putFragment(bundle, STATE_CURRENT_FRAGMENT, currentFragment);
		}
	}
	
	/**
	 * 从Bundle中恢复状态
	 * @param bundle
	 */
	public void restoreFromBundle(Bundle bundle) {
		if(bundle == null) {
			return;
		}
		ArrayList<String> idList = bundle.getStringArrayList(STATE_IDS);//Activity 中attach
		if(idList != null) {
			Fragment fragment;
			for(String id : idList) {
				fragment = manager.getFragment(bundle, id);
				if(fragment != null) {
					fragments.put(id, new SoftReference<Fragment>(fragment));
				}
			}
		}
		
		String lastId = bundle.getString(STATE_LAST_FRAGMENT_ID);
		if(lastId != null) {
			lastFragmentId = lastId;
		}
		
		String currentId = bundle.getString(STATE_CURRENT_FRAGMENT_ID);
		if(currentId != null) {
			currentFragmentId = currentId;
		}
		
		Fragment lastFragment = manager.getFragment(bundle, STATE_CURRENT_FRAGMENT);
		if(lastFragment != null) {
			currentFragment = lastFragment;
		}
		
		//删除manager中除当前显示的fragment外的所有fragment.
		FragmentTransaction ft = manager.beginTransaction();
		List<Fragment> addedPragments = manager.getFragments();
		for (int i = 0; i < addedPragments.size(); i++) {
			Fragment fragment = addedPragments.get(i);
			if(fragment !=null){
				if(currentId == null || !currentId.equals(fragment.getTag())){
					ft.remove(fragment);
				}
			}

		}
		
		ft.commitAllowingStateLoss();
	}
}
