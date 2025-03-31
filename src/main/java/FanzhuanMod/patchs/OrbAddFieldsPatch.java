package FanzhuanMod.patchs;


import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

@SpirePatch(
        clz = AbstractOrb.class,
        method = "<class>"
)
public class OrbAddFieldsPatch {

    private static boolean hasChange = false;

    public static SpireField<Boolean> HasChange =  new SpireField<>(() -> hasChange);

    public OrbAddFieldsPatch() {
    }
}