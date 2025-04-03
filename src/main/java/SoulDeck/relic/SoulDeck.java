package SoulDeck.relic;

import SoulDeck.cardmodifier.SoulModifier;
import SoulDeck.monsters.Transient;
import basemod.abstracts.CustomRelic;
import SoulDeck.helpers.ModHelper;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.evacipated.cardcrawl.mod.stslib.patches.SoulboundPatch;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.monsters.exordium.Sentry;
import com.megacrit.cardcrawl.potions.DistilledChaosPotion;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 这个遗物会在开始的时候赋予房间所有怪物3点血量
 *
 * @author : Administrator
 * @date : 2020-08-06 16:27
 **/
@SuppressWarnings("unused")
public class SoulDeck extends CustomRelic implements ClickableRelic {
    /**
     * 遗物ID 随便写 但是需要和json文件名称一致
     * 比如我这里最终是 CANDY_MOD_Money 就需要最后json文件内有 CANDY_MOD_Money 的遗物信息
     */
    public static final String ID = ModHelper.makePath(SoulDeck.class.getSimpleName());
    /**
     * 日志对象
     */
    private static final Logger log = LogManager.getLogger(SoulDeck.class);
    
    /**
     * 构造函数
     */
    public SoulDeck() {
        //图片使用内置的 使用破碎王冠 的图标
        //使用内置图标就不需要导入了 想自定义可以抄其他的mod或者看教程

        super(ID, new Texture(Gdx.files.internal("images/relics/souldeck.png")), RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter=1;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("灵魂密语","战斗，燃烧，杀戮尖塔！ NL 唤醒： #g收集灵魂，诅咒之路 NL 强化： #b明耀擢升，黑暗低语。 NL 限制： #y燃烧魂火，力量释放。 NL 失控： #r邪恶缠身，深渊乱战。"));
        this.initializeTips();
    }
    private CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);


    
    /**
     * 重写遗物的描述内容 可以不用管
     *
     * @return 字符串内容
     */
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick() {
        if(AbstractDungeon.getCurrRoom().phase!= AbstractRoom.RoomPhase.COMBAT&&this.counter>0&&(AbstractDungeon.screen== AbstractDungeon.CurrentScreen.MAP||AbstractDungeon.screen== AbstractDungeon.CurrentScreen.COMBAT_REWARD )){
            this.flash();
            tmp.clear();
            this.counter -=1;
            AbstractCard card;
            do{

                int roll = AbstractDungeon.cardRandomRng.random(CardLibrary.getAllCards().size()-1);


                card = CardLibrary.getAllCards().get(roll).makeStatEquivalentCopy();
                if(this.counter<=9){
                    if(AbstractDungeon.cardRandomRng.randomBoolean(0.5F))
                    {
                        card = CardLibrary.getCurse().makeCopy();
                    }
                }
                boolean canadd = true;
                for(AbstractCard c:tmp.group){
                    if(c.cardID.equals(card.cardID)){
                        canadd = false;
                    }
                }
                if(canadd) {

                    if(this.counter>=10&&this.counter<=19){
                            card.upgrade();
                    }

                        CardModifierManager.addModifier(card, new SoulModifier());

                    tmp.group.add(card);
                }
            }while(tmp.size() < 3);
            if (!AbstractDungeon.isScreenUp) {
                AbstractDungeon.gridSelectScreen.open(tmp, 1, this.DESCRIPTIONS[1],false,false,false,false);
            } else {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
                AbstractDungeon.gridSelectScreen.open(tmp, 1, this.DESCRIPTIONS[1],false,false,false,false);
            }

        }
    }
    public void update() {
        super.update();
        if ((AbstractDungeon.screen== AbstractDungeon.CurrentScreen.MAP||AbstractDungeon.screen== AbstractDungeon.CurrentScreen.COMBAT_REWARD )&& !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            AbstractCard c = ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeCopy();
            CardModifierManager.addModifier(c,new SoulModifier());
            if(this.counter>=9&&this.counter<=19){
                c.upgrade();
            }
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }
    public void atBattleStart() {
        if(this.counter<=29&&this.counter>=20){
            this.flash();
            int decreace=AbstractDungeon.cardRandomRng.random(0,3);
            this.counter-=decreace;
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(3));
        }
        if(this.counter>=30){
            this.flash();
            if(AbstractDungeon.cardRandomRng.randomBoolean((float) this.counter /100)) {
                AbstractMonster transientMonster = new Transient(this.counter);
                transientMonster.usePreBattleAction();
                transientMonster.drawX = AbstractDungeon.getCurrRoom().monsters.monsters.get(0).drawX - Settings.WIDTH / 10.0F;
                transientMonster.drawY = AbstractDungeon.getCurrRoom().monsters.monsters.get(0).drawY + Settings.HEIGHT / 10.0F;
                this.addToBot(new SpawnMonsterAction(transientMonster, false));
            }
            if(AbstractDungeon.cardRandomRng.randomBoolean((float) this.counter /100)) {
                for(int i = 0; i < 3; ++i) {
                    this.addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), false));
                }
            }

        }
    }


    @Override
    public void onMonsterDeath(AbstractMonster m) {
        super.onMonsterDeath(m);
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m,this));
        if(m.hasPower(MinionPower.POWER_ID)||m instanceof Darkling ||m instanceof AwakenedOne){
            if(AbstractDungeon.cardRandomRng.randomBoolean(0.5f)){
                this.counter+=1;
            }
            return;
        }

        if(m.type== AbstractMonster.EnemyType.ELITE)
        {
            this.counter+=2;
            return;
        }
        if(m.type== AbstractMonster.EnemyType.BOSS)
        {
            this.counter+=3;
            return;
        }
        this.counter+=1;
    }
    public void onCardDraw(AbstractCard drawnCard) {
        if(this.counter<20&&this.counter>=10){
            if (drawnCard.type == CardType.STATUS || drawnCard.type == CardType.CURSE) {
                this.flash();

                int roll = AbstractDungeon.cardRandomRng.random(1, 5);
                this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(null, roll, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }

    }

  public void onVictory() {
        if(this.counter<=9){
            this.flash();
            int increments = AbstractDungeon.cardRandomRng.random(0,4);
            this.counter+=increments;
        }

  }
}
