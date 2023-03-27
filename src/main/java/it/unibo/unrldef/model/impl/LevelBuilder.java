package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.Path.Direction;
import it.unibo.unrldef.model.api.Path;

public class LevelBuilder {

    private final Player player;
	private final int scale = 2;

    public LevelBuilder(Player player) {
        this.player = player;
    }

    public WorldImpl levelOne() {
        return new WorldImpl.Builder("mondo1", player, new Position(60, 0), 20, 10000)
		.addPathSegment(Path.Direction.DOWN, 7*scale)
		.addPathSegment(Path.Direction.LEFT, 25*scale)
		.addPathSegment(Path.Direction.DOWN, 10*scale)
		.addPathSegment(Path.Direction.RIGHT, 12*scale)
		.addPathSegment(Path.Direction.UP, 2*scale)
		.addPathSegment(Path.Direction.RIGHT, 8*scale)
		.addPathSegment(Path.Direction.DOWN, 11*scale)
		.addPathSegment(Path.Direction.RIGHT, 10*scale)
		.addPathSegment(Path.Direction.DOWN, 8*scale)
		.addPathSegment(Path.Direction.LEFT, 20*scale)
		.addPathSegment(Path.Direction.DOWN, 3*scale)
		.addPathSegment(Path.Direction.END, 0)
		.addWave()
		.addHordeToWave(0, 50000)
		.addMultipleEnemiesToHorde(0, 0, new Orc(Optional.of(new Position(30*scale, 0))), (short)5)
		.addMultipleEnemiesToHorde(0, 0, new Goblin(Optional.of(new Position(30*scale, 0))), (short)5)
		.addAvailableTower(Cannon.NAME, new Cannon(null))
		.addAvailableTower(Hunter.NAME, new Hunter(null))
		.addTowerBuildingSpace(14*scale, 3*scale)
		.addTowerBuildingSpace(24*scale, 3*scale)
		.addTowerBuildingSpace(34*scale, 4*scale)
		.addTowerBuildingSpace(9*scale, 12*scale)
		.addTowerBuildingSpace(19*scale, 11*scale)
		.addTowerBuildingSpace(3*scale, 21*scale)
		.addTowerBuildingSpace(29*scale, 21*scale)
		.addTowerBuildingSpace(17*scale, 30*scale)
		.addTowerBuildingSpace(31*scale, 30*scale)
		.addTowerBuildingSpace(23*scale, 38*scale)
		.addTowerBuildingSpace(33*scale, 38*scale)
		.build();
    }

    
    
}
