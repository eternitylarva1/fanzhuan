package SoulDeck.config;

/**
 * 自定义遗物的常量接口
 * 写这个常量接口是为了遗物的名称并不重复
 *
 * @author : Administrator
 * @date : 2020-08-06 17:22
 **/
public interface RelicConfig {
    /**
     * 遗物的前缀 用来防止与游戏内部资源的遗物相冲突
     */
    String RELIC_PRE_NAME = "CANDY_MOD_";
}