package xs.util;

import org.bukkit.entity.*;

public class Mobs {
    public static boolean isPassiveMob(Entity mob) {
        return (mob instanceof Axolotl) ||
                (mob instanceof Bat) ||
                (mob instanceof Cat) ||
                (mob instanceof Chicken) ||
                (mob instanceof Cod) ||
                (mob instanceof Cow) ||
                (mob instanceof Donkey) ||
                (mob instanceof Fox) ||
                (mob instanceof Horse) ||
                (mob instanceof Mule) ||
                (mob instanceof Ocelot) ||
                (mob instanceof Parrot) ||
                (mob instanceof Pig) ||
                (mob instanceof PolarBear) ||
                (mob instanceof PufferFish) ||
                (mob instanceof Rabbit) ||
                (mob instanceof Salmon) ||
                (mob instanceof Sheep) ||
                (mob instanceof SkeletonHorse) ||
                (mob instanceof Snowman) ||
                (mob instanceof Squid) ||
                (mob instanceof Strider) ||
                (mob instanceof TropicalFish) ||
                (mob instanceof Turtle) ||
                (mob instanceof Villager) ||
                (mob instanceof WanderingTrader);
    }

    public static boolean isNeutralMob(Entity mob) {
        return (mob instanceof Bee) ||
                (mob instanceof CaveSpider) ||
                (mob instanceof Dolphin) ||
                (mob instanceof Enderman) ||
                (mob instanceof Goat) ||
                (mob instanceof IronGolem) ||
                (mob instanceof Llama) ||
                (mob instanceof Piglin) ||
                (mob instanceof Panda) ||
                (mob instanceof PolarBear) ||
                (mob instanceof Wolf) ||
                (mob instanceof PigZombie);
    }
}
