package FanzhuanMod.hook;

import FanzhuanMod.helpers.ModHelper;
import basemod.EasyConfigPanel;

public class MyModConfig extends EasyConfigPanel {
    public MyModConfig() {
        super(LoadMySpireMod.ModID, ModHelper.makePath(MyModConfig.class.getSimpleName()));
    }
    public static boolean EnableLighting = true;
    public static boolean EnableDark = true;
    public static boolean EnableCalm = true;
    public static boolean EnableRandomStance = true;
    public static boolean EnableStrength = true;
}
