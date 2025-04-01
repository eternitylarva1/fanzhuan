/*
package SoulDeck.patchs;

import SoulDeck.cardmodifier.SoulModifier;
import SoulDeck.relic.SoulDeck;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

@SpirePatch(cls = "com.megacrit.cardcrawl.screens.CombatRewardScreen", method = "rewardViewUpdate")
public class ReplayRewardSkipPositionPatch {

    public static float HIDE_X = -1.0f;
    public static float SHOW_X = -1.0f;

    public static void Postfix(CombatRewardScreen __Instance) {

        if (SoulDeck.cannotSkipReward()) {
            if (HIDE_X == -1.0f) {
                HIDE_X = AbstractDungeon.topPanel.mapHb.cX - 400.0f * Settings.scale;
                SHOW_X = AbstractDungeon.topPanel.mapHb.cX;
            }
            boolean proceed = true;
            for (int i = 0; i < __Instance.rewards.size(); i++){
                if (__Instance.rewards.get(i).type == RewardItem.RewardType.CARD){
                        RewardItem reward = __Instance.rewards.get(i);
                        for(AbstractCard c: reward.cards){
                        CardModifierManager.addModifier(c, new SoulModifier());
                        }
                    proceed = false;
                    break;
                }
            }
            if (proceed) {
                AbstractDungeon.overlayMenu.proceedButton.show();
                AbstractDungeon.topPanel.mapHb.move(SHOW_X, AbstractDungeon.topPanel.mapHb.cY);
            } else {
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.topPanel.mapHb.move(ReplayRewardSkipPositionPatch.HIDE_X, AbstractDungeon.topPanel.mapHb.cY);
            }
        }
    }
}*/