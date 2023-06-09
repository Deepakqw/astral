package hellfirepvp.astralsorcery.common.constellation;

import java.awt.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: DrawnConstellation
 * Created by HellFirePvP
 * Date: 03.05.2017 / 13:58
 */
public class DrawnConstellation {

    public static final int CONSTELLATION_DRAW_SIZE = 30;

    public final Point point;
    public final IConstellation constellation;

    public DrawnConstellation(Point point, IConstellation constellation) {
        this.point = point;
        this.constellation = constellation;
    }

}
