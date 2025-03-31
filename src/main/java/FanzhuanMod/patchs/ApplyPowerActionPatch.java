
package FanzhuanMod.patchs;

import FanzhuanMod.cardModifier.RandomStanceModifier;
import FanzhuanMod.hook.MyModConfig;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.CalmStance;

@SpirePatch(clz = ApplyPowerAction.class, method = "update")
public class ApplyPowerActionPatch {

    @SpireInsertPatch(
            rloc=5
    )

    public static SpireReturn Insertfix(ApplyPowerAction action, @ByRef AbstractPower[] ___powerToApply) {
        if(MyModConfig.EnableStrength)
        {
            if( action.source!= AbstractDungeon.player)
            {return SpireReturn.Continue();}
            if( action.target!=AbstractDungeon.player)
            {return SpireReturn.Continue();}
            if(___powerToApply[0] instanceof StrengthPower)
            {
                ___powerToApply[0] = new DexterityPower(AbstractDungeon.player,___powerToApply[0].amount);
                return SpireReturn.Continue();
            }
            if(___powerToApply[0] instanceof DexterityPower)
            {
                ___powerToApply[0] = new StrengthPower(AbstractDungeon.player,___powerToApply[0].amount);
                return SpireReturn.Continue();
            }
        }
        return SpireReturn.Continue();
    }

}
