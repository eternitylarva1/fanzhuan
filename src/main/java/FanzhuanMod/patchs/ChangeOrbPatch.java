package FanzhuanMod.patchs;

import FanzhuanMod.cardModifier.CalmModifier;
import FanzhuanMod.cardModifier.RandomStanceModifier;
import FanzhuanMod.hook.MyModConfig;
import FanzhuanMod.utils.Invoker;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.Coolheaded;
import com.megacrit.cardcrawl.cards.blue.Fusion;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.cards.purple.EmptyMind;
import com.megacrit.cardcrawl.cards.purple.LikeWater;
import com.megacrit.cardcrawl.cards.purple.Vigilance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;

@SpirePatch(clz = AbstractPlayer.class, method = "channelOrb")

public class ChangeOrbPatch {

    @SpireInsertPatch(
            rloc=0
    )

    public static void Insertfix(AbstractPlayer player, @ByRef AbstractOrb[] orbToSet) {
        if(OrbAddFieldsPatch.HasChange.get(orbToSet[0])){
            return;
        }
      switch (orbToSet[0].ID){
          case Lightning.ORB_ID:
              if(MyModConfig.EnableLighting)
              orbToSet[0]=new Frost();
              break;
          case Frost.ORB_ID:
              if(MyModConfig.EnableLighting)
              orbToSet[0]=new Lightning();

                  break;
          case Dark.ORB_ID:
              if(MyModConfig.EnableDark)
              orbToSet[0]=new Plasma();
              break;
          case Plasma.ORB_ID:
              if(MyModConfig.EnableDark)
              orbToSet[0]=new Dark();

              break;
      }
        OrbAddFieldsPatch.HasChange.set(orbToSet[0],true);
    }
}
