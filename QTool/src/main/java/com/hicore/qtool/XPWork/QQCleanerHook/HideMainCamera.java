package com.hicore.qtool.XPWork.QQCleanerHook;

import com.hicore.HookItem;
import com.hicore.UIItem;
import com.hicore.qtool.XposedInit.ItemLoader.BaseHookItem;
import com.hicore.qtool.XposedInit.ItemLoader.BaseUiItem;
import com.hicore.qtool.XposedInit.ItemLoader.HookLoader;

@HookItem(isRunInAllProc = false,isDelayInit = false)
//@UIItem(itemName = "屏蔽主界面相机",mainItemID = 2,itemType = 1,ID = "HideMainActCamera")
public class HideMainCamera extends BaseHookItem implements BaseUiItem {
    boolean IsEnable;
    @Override
    public boolean startHook() throws Throwable {
        return false;
    }

    @Override
    public boolean isEnable() {
        return false;
    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public void SwitchChange(boolean IsCheck) {
        IsEnable = IsCheck;
        if (IsCheck){
            HookLoader.CallHookStart(HideMainCamera.class.getName());
        }
    }

    @Override
    public void ListItemClick() {

    }
}
