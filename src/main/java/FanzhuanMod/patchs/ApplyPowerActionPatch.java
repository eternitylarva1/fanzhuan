package FanzhuanMod.patchs;

import FanzhuanMod.cardModifier.RandomStanceModifier;
import FanzhuanMod.hook.MyModConfig;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.FaceTrader;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.FaceOfCleric;
import com.megacrit.cardcrawl.relics.GremlinMask;
import com.megacrit.cardcrawl.relics.NlothsMask;
import com.megacrit.cardcrawl.stances.CalmStance;

@SpirePatch(clz = ApplyPowerAction.class, method = "update")
public class ApplyPowerActionPatch {

    @SpireInsertPatch(
            rloc=5
    )

    public static SpireReturn Insertfix(ApplyPowerAction action, @ByRef AbstractPower[] ___powerToApply) {
        if(MyModConfig.EnableYishang)
        {
            if( action.source!= AbstractDungeon.player)
            {return SpireReturn.Continue();}
            if(___powerToApply[0] instanceof VulnerablePower)
            {
                ___powerToApply[0] = new WeakPower(action.target, ___powerToApply[0].amount,false);
                return SpireReturn.Continue();
            }
            if(___powerToApply[0] instanceof WeakPower)
            {
                ___powerToApply[0] = new VulnerablePower(action.target,___powerToApply[0].amount,false);
                return SpireReturn.Continue();
            }
        }


        return SpireReturn.Continue();
    }

}
