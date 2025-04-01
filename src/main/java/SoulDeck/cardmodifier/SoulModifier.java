package SoulDeck.cardmodifier;


import SoulDeck.helpers.ModHelper;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class SoulModifier extends AbstractCardModifier {
    public static String ID = ModHelper.makePath(SoulModifier.class.getSimpleName());
    private static final UIStrings STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    // 修改描述
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {

        return String.format(STRINGS.TEXT[0], rawDescription);
    }
    @Override
    public boolean  shouldApply(AbstractCard card) {
        return !SoulboundField.soulbound.get(card)&&!CardModifierManager.hasModifier(card,ID);
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new SoulModifier();
    }
    @Override
    public void onInitialApplication(AbstractCard card) {
        SoulboundField.soulbound.set(card, true);
    }
    @Override
    public String modifyName(String name, AbstractCard card) {

        return String.format(STRINGS.TEXT[1], name);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
