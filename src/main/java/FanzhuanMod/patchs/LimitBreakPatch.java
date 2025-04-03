package FanzhuanMod.patchs;

import FanzhuanMod.hook.MyModConfig;
import FanzhuanMod.utils.Invoker;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LimitBreakAction;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.CalmStance;

@SpirePatch(clz = LimitBreakAction.class, method = "update")
public class LimitBreakPatch {

    @SpirePrefixPatch

    public static SpireReturn Insertfix(LimitBreakAction action,float ___duration) {
        if(MyModConfig.EnableStrength) {
            
            if (___duration == Settings.ACTION_DUR_XFAST && AbstractDungeon.player.hasPower("Strength")) {
                int strAmt = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
              AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, strAmt), strAmt));
            }
            Invoker.invoke(action,"tickDuration");

            return SpireReturn.Return();
        }

        return SpireReturn.Continue();
    }

}
