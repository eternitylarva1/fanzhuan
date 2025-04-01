package FanzhuanMod.cardModifier;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//




import FanzhuanMod.helpers.ModHelper;
import FanzhuanMod.patchs.InterruptUseCardFieldPatches;
import FanzhuanMod.utils.TextureHueShiftUtils;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;

public class CalmModifier extends AbstractCardModifier {
    public static String ID = ModHelper.makePath(CalmModifier.class.getSimpleName());
    private static final UIStrings uiStrings;


    public CalmModifier() {
    }
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
          }
    public String modifyDescription(String rawDescription, AbstractCard card) {
        //rawDescription中的格挡改为金属化

        String temp = "TEMP_PLACEHOLDER";
        return rawDescription
                .replace(uiStrings.TEXT[0], temp)  // 平静 -> 临时
                .replace(uiStrings.TEXT[1], uiStrings.TEXT[0]) // 愤怒 -> 平静
                .replace(temp, uiStrings.TEXT[1]); // 临时 -> 愤怒
    }

    public boolean shouldApply(AbstractCard card) {
        return !(CardModifierManager.hasModifier(card, ID))&&(card.rawDescription.contains(uiStrings.TEXT[0])||card.rawDescription.contains(uiStrings.TEXT[1]));
    }

    public void onInitialApplication(AbstractCard card) {

    }

    public void onRemove(AbstractCard card) {
        String temp = "TEMP_PLACEHOLDER";
        card.rawDescription = card.rawDescription
                .replace(uiStrings.TEXT[1], temp)  // 愤怒 -> 临时
                .replace(uiStrings.TEXT[0], uiStrings.TEXT[1]) // 平静 -> 愤怒
                .replace(temp, uiStrings.TEXT[0]); // 临时 -> 平静
    }

    public AbstractCardModifier makeCopy() {
        return new CalmModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    }
}

