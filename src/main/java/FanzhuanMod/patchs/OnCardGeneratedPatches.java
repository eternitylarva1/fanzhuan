package FanzhuanMod.patchs;


import FanzhuanMod.cardModifier.*;
import FanzhuanMod.hook.MyModConfig;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.relics.PandorasBox;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import javassist.CtBehavior;

public class OnCardGeneratedPatches {
  
  public static void addModifier(AbstractCard c){
      if(MyModConfig.EnableRandomStance)
          CardModifierManager.addModifier(c, new RandomStanceModifier());
      if(MyModConfig.EnableCalm)
          CardModifierManager.addModifier(c, new CalmModifier());
      if(MyModConfig.EnableDark)
          CardModifierManager.addModifier(c,new DarkModifier()) ;
      if(MyModConfig.EnableLighting)
          CardModifierManager.addModifier(c,new LightningModifier()) ;
      if(MyModConfig.EnableYishang)
          CardModifierManager.addModifier(c,new YishangModifier()) ;
      if(MyModConfig.EnableStrength)
          CardModifierManager.addModifier(c,new StrengthModifier()) ;


  }
  
  
  @SpirePatch2(clz = CombatRewardScreen.class, method = "setupItemReward")
  public static class ModifyRewardScreenStuff {
    @SpirePostfixPatch
    public static void patch(CombatRewardScreen __instance) {
      for (RewardItem r : __instance.rewards) {
        if (r.cards != null)
          for (AbstractCard c : r.cards) {
              addModifier(c);
          }
      } 
    }
  }
  
  @SpirePatch2(clz = AbstractDungeon.class, method = "getRewardCards")
  public static class ModifySpawnedCardsPatch {
    @SpirePostfixPatch
    public static void patch(ArrayList<AbstractCard> __result) {
      for (AbstractCard c : __result)
      {
    addModifier(c);
      }
    }
  }
  
  @SpirePatch2(clz = AbstractDungeon.class, method = "getColorlessRewardCards")
  public static class ModifySpawnedColorlessCardsPatch {
    @SpirePostfixPatch
    public static void patch(ArrayList<AbstractCard> __result) {
      for (AbstractCard c : __result)
      {
    addModifier(c);
      }
    }
  }
  
  @SpirePatch2(clz = GridCardSelectScreen.class, method = "openConfirmationGrid")
  public static class ModifyConfirmScreenCards {
    @SpirePostfixPatch
    public static void patch(CardGroup group) {

        for (AbstractCard c : group.group)
        {
addModifier(c);
        }
    }
  }
  
  @SpirePatch2(clz = GremlinMatchGame.class, method = "initializeCards")
  public static class ModifyMatchGameCards {
    @SpireInsertPatch(locator = Locator.class, localvars = {"retVal"})
    public static void patch(ArrayList<AbstractCard> retVal) {
      for (AbstractCard c : retVal)
      {
    addModifier(c);
      }
    }
    
    private static class Locator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "getStartCardForEvent");
        return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
      }
    }
  }
  
  @SpirePatch2(clz = NeowReward.class, method = "getRewardCards")
  public static class ModifyNeowRewardCardsPatch {
    @SpirePostfixPatch
    public static void patch(ArrayList<AbstractCard> __result) {
      for (AbstractCard c : __result)
      {
    addModifier(c);
      }
    }
  }
  
  @SpirePatch2(clz = NeowReward.class, method = "getColorlessRewardCards")
  public static class ModifyNeowRewardColorlessCardsPatch {
    @SpirePostfixPatch
    public static void patch(ArrayList<AbstractCard> __result) {
      for (AbstractCard c : __result)
      {
    addModifier(c);
      }
    }
  }
  
  @SpirePatch2(clz = AbstractPlayer.class, method = "initializeStarterDeck")
  public static class ModifyStarterCards {
    @SpirePostfixPatch
    public static void patch(AbstractPlayer __instance) {

        for (AbstractCard c : __instance.masterDeck.group)
        {
addModifier(c);
        }
    }
  }
  
  @SpirePatch2(clz = PandorasBox.class, method = "onEquip")
  public static class ModifyPandoraCards {
    @SpireInsertPatch(locator = Locator.class, localvars = {"group"})
    public static void patch(CardGroup group) {

        for (AbstractCard c : group.group)
        {
addModifier(c);
        }
    }
    
    private static class Locator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(GridCardSelectScreen.class, "openConfirmationGrid");
        return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
      }
    }
  }
  
  @SpirePatch2(clz = TheLibrary.class, method = "buttonEffect")
  public static class ModifyTheLibraryCards {
    @SpireInsertPatch(locator = Locator.class, localvars = {"group"})
    public static void patch(CardGroup group) {
      for (AbstractCard c : group.group)
      {
    addModifier(c);
      }
    }
    
    private static class Locator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(GridCardSelectScreen.class, "open");
        return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
      }
    }
  }
  
  @SpirePatches2({@SpirePatch2(clz = ShowCardAndObtainEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class, float.class, float.class, boolean.class}), @SpirePatch2(clz = FastCardObtainEffect.class, method = "<ctor>")})
  public static class ModifySpawnedMasterDeckCards {
    @SpirePostfixPatch
    public static void patch(AbstractCard ___card) {

      {
        CardModifierManager.addModifier(___card, new CalmModifier());
        CardModifierManager.addModifier(___card, new RandomStanceModifier());
      }
    }
  }
  
  @SpirePatch2(clz = Merchant.class, method = "<ctor>", paramtypez = {float.class, float.class, int.class})
  public static class ModifyShopCards {
    @SpireInsertPatch(locator = Locator.class)
    public static void patch(Merchant __instance, ArrayList<AbstractCard> ___cards1, ArrayList<AbstractCard> ___cards2) {

        for (AbstractCard c : ___cards1)
        {
addModifier(c);
        }
        for (AbstractCard c : ___cards2)
        {
addModifier(c);
        }

    }
    
    private static class Locator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ShopScreen.class, "init");
        return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
      }
    }
  }
  
  @SpirePatch2(clz = NeowEvent.class, method = "dailyBlessing")
  public static class RunModifierPatches {
    @SpireInsertPatch(locator = SealedLocator.class, localvars = {"sealedGroup"})
    public static void sealedDeck(CardGroup sealedGroup) {

        for (AbstractCard c : sealedGroup.group)
        {
addModifier(c);
        }
    }
    
    @SpireInsertPatch(locator = AddedLocator.class, localvars = {"group"})
    public static void addedCards(CardGroup group) {
      for (AbstractCard c : group.group) {

        {
addModifier(c);
        }

      } 
    }
    
    @SpireInsertPatch(locator = FastObtainLocator.class, localvars = {"tmpCard"})
    public static void fixSpecialized(AbstractCard tmpCard) {

      {
addModifier(tmpCard);
      }

    }
    
    public static class SealedLocator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctBehavior) throws Exception {
        Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(GridCardSelectScreen.class, "open");
        return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
      }
    }
    
    public static class AddedLocator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctBehavior) throws Exception {
        Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(GridCardSelectScreen.class, "openConfirmationGrid");
        return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
      }
    }
    
    public static class FastObtainLocator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctBehavior) throws Exception {
        Matcher.NewExprMatcher newExprMatcher = new Matcher.NewExprMatcher(FastCardObtainEffect.class);
        return LineFinder.findInOrder(ctBehavior, (Matcher)newExprMatcher);
      }
    }
  }
  
  @SpirePatch2(clz = CardRewardScreen.class, method = "draftOpen")
  public static class DraftFix {
    @SpirePostfixPatch
    public static void plz(CardRewardScreen __instance) {

        for (AbstractCard c : __instance.rewardGroup)
        {
addModifier(c);
        }
    }
  }
  
  @SpirePatch2(clz = CardRewardScreen.class, method = "customCombatOpen")
  public static class DiscoveryStyleCards {
    @SpirePrefixPatch
    public static void roll(ArrayList<AbstractCard> choices) {

        for (AbstractCard c : choices)
        {
addModifier(c);
        }
    }
  }
  
  @SpirePatches2({@SpirePatch2(clz = ShowCardAndAddToHandEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class}), @SpirePatch2(clz = ShowCardAndAddToHandEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class, float.class, float.class}), @SpirePatch2(clz = ShowCardAndAddToDrawPileEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class, boolean.class, boolean.class}), @SpirePatch2(clz = ShowCardAndAddToDrawPileEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class, float.class, float.class, boolean.class, boolean.class, boolean.class}), @SpirePatch2(clz = ShowCardAndAddToDiscardEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class}), @SpirePatch2(clz = ShowCardAndAddToDiscardEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class, float.class, float.class})})
  public static class CreatedCards {
    @SpirePrefixPatch
    public static void roll(Object[] __args) {
      if (__args[0] instanceof AbstractCard) {
        AbstractCard card = (AbstractCard)__args[0];
        {
addModifier(card);
        }

      } 
    }
  }
  
  @SpirePatch2(clz = CardGroup.class, method = "initializeDeck")
  public static class CopiesMadeAreFinal {
    @SpirePostfixPatch
    public static void fixMods(CardGroup __instance) {
      for (AbstractCard c : __instance.group) {
addModifier(c);
      }

    }
  }
}
