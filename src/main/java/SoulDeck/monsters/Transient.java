package SoulDeck.monsters;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.FadingPower;
import com.megacrit.cardcrawl.powers.ShiftingPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import static java.lang.Math.pow;

public class Transient extends AbstractMonster {
    public static final String ID = "Transient";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP = 999;
    private int count = 0;
    private static final int DEATH_DMG = 30;
    private static final int INCREMENT_DMG = 10;
    private static final int A_2_DEATH_DMG = 40;
    private int startingDeathDmg;
    private static final byte ATTACK = 1;

    public Transient() {
      this(999);
    }
    public Transient(int amount) {
        super("影魔", "Transient", 50+(20-amount)*5, 0.0F, -15.0F, 270, 240.0F, (String)null, 0.0F, 20.0F);
        this.loadAnimation("images/monsters/theForest/transient/skeleton.atlas", "images/monsters/theForest/transient/skeleton.json", 1.5F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.gold = 1;
        this.dialogX = -100.0F * Settings.scale;
        this.dialogY -= 20.0F * Settings.scale;
        int increase=(20-amount)*2;
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.startingDeathDmg = 10;
        } else {
            this.startingDeathDmg = 15;
        }

        for(int i=0;i<7;i++){
            this.damage.add(new DamageInfo((AbstractCreature) this, (int) (this.startingDeathDmg+(increase*2*pow(1.15, i)-increase*2))));
        }
    }

    public void usePreBattleAction() {

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FadingPower(this, 5)));


    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(this.count), AttackEffect.BLUNT_HEAVY));
                ++this.count;
                this.setMove((byte)1, Intent.ATTACK, this.damage.get(this.count).base);
            default:
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hurt", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void changeState(String key) {
        switch (key) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
            default:
        }
    }

    public void die() {
        super.die();
        UnlockTracker.unlockAchievement("TRANSIENT");
    }

    protected void getMove(int num) {
        this.setMove((byte)1, Intent.ATTACK, this.startingDeathDmg + this.count * 10);
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Transient");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
