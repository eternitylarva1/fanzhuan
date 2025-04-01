package FanzhuanMod.cards;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import FanzhuanMod.hook.MyModConfig;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

import java.util.ArrayList;
import java.util.Arrays;

public class EmptyMind extends AbstractCard {
    public static final String ID = "EmptyMind";
    private static final CardStrings cardStrings;

    public EmptyMind() {
        super("EmptyMind", cardStrings.NAME, "purple/skill/empty_mind", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = 2;
        this.baseMagicNumber = 2;
        this.tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(this.magicNumber));

        String[] temp={"Calm","Wrath","Divinity"};
        String currentStance = p.stance.ID;
        if(MyModConfig.EnableRandomStance) {
            ArrayList<String> availableStances = new ArrayList<>(Arrays.asList("Calm", "Wrath"));
// 排除当前架势
            availableStances.remove(currentStance);
            String as = availableStances.get((int) AbstractDungeon.cardRandomRng.random(availableStances.size() - 1));
            this.addToBot(new NotStanceCheckAction(as, new VFXAction(new EmptyStanceEffect(p.hb.cX, p.hb.cY), 0.1F)));
            this.addToBot(new ChangeStanceAction(as));
        }else {
            this.addToBot(new ChangeStanceAction("Neutral"));
        }

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new EmptyMind();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("EmptyMind");
    }
}
