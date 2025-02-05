package com.hicore.qtool.QQGroupHelper.FileHelper;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.bumptech.glide.util.Util;
import com.hicore.HookItem;
import com.hicore.LogUtils.LogUtils;
import com.hicore.ReflectUtils.MClass;
import com.hicore.ReflectUtils.MField;
import com.hicore.ReflectUtils.MMethod;
import com.hicore.ReflectUtils.XPBridge;
import com.hicore.UIItem;
import com.hicore.Utils.Utils;
import com.hicore.qtool.HookEnv;
import com.hicore.qtool.XposedInit.ItemLoader.BaseHookItem;
import com.hicore.qtool.XposedInit.ItemLoader.BaseUiItem;
import com.hicore.qtool.XposedInit.ItemLoader.HookLoader;

import java.lang.reflect.Method;
import java.util.Locale;

@UIItem(itemName = "上传重命名base.apk",mainItemID = 1,ID = "UploadFileRename",itemType = 1)
@HookItem(isDelayInit = false,isRunInAllProc = false)
public class FileUploadRename extends BaseHookItem implements BaseUiItem {
    boolean IsEnable;
    @Override
    public boolean startHook() throws Throwable {
        Method hookMethod = getMethod();
        XPBridge.HookBefore(hookMethod,param -> {
            if (!IsEnable)return;
            String path = (String) param.args[0];
            if (path.toLowerCase(Locale.ROOT).endsWith("/base.apk")){
                String Name = GetPackageInfo(path);
                param.setResult(Name);
            }
        });
        return true;
    }

    @Override
    public boolean isEnable() {
        return IsEnable;
    }

    @Override
    public boolean check() {
        return getMethod() != null;
    }

    @Override
    public void SwitchChange(boolean IsCheck) {
        IsEnable = IsCheck;
        if (IsCheck){
            HookLoader.CallHookStart(FileUploadRename.class.getName());
        }
    }

    @Override
    public void ListItemClick() {

    }
    public Method getMethod(){
        Method m = MMethod.FindMethod(MClass.loadClass("com.tencent.mobileqq.uftransfer.depend.UFTDependFeatureApi"),"e",String.class,
                new Class[]{String.class});
        return m;
    }
    public static String GetPackageInfo(String Path) {
        PackageManager manager = HookEnv.AppContext.getPackageManager();
        PackageInfo info = manager.getPackageArchiveInfo(Path,PackageManager.GET_ACTIVITIES);
        if(info!=null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = Path;
            appInfo.publicSourceDir = Path;
            String appName = manager.getApplicationLabel(appInfo).toString();
            String version = info.versionName;
            return appName + "-"+version+".apk";
        }
        return "base.apk";
    }
}
