package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.Path.Direction;

public class LevelBuilder {

    private final Player player;

    public LevelBuilder(Player player) {
        this.player = player;
    }

    public WorldImpl levelOne() {
        return new WorldImpl.Builder("River Castle", this.player, new Position(0.75 , 0), 5, 100)
        .addPathSegment(Direction.DOWN, 0.1875)
		.addPathSegment(Direction.LEFT, 0.625)
		.addPathSegment(Direction.DOWN, 0.25)
		.addPathSegment(Direction.RIGHT, 0.3)
		.addPathSegment(Direction.UP, 0.075)
		.addPathSegment(Direction.RIGHT, 0.2)
		.addPathSegment(Direction.DOWN, 0.275)
		.addPathSegment(Direction.RIGHT, 0.25)
		.addPathSegment(Direction.DOWN, 0.225)
		.addPathSegment(Direction.LEFT, 0.5)
		.addPathSegment(Direction.DOWN, 0.0625)
		.addPathSegment(Direction.END, 0)
		.addWave()
		.addHordeToWave(0, 50000)
		.addMultipleEnemiesToHorde(0, 0, new Orc(Optional.of(new Position(0.75, 0))), (short)5)
		.addMultipleEnemiesToHorde(0, 0, new Goblin(Optional.of(new Position(0.75, 0))), (short)5)
		.addAvailableTower(Cannon.NAME, new Cannon(null))
		.addAvailableTower(Hunter.NAME, new Hunter(null))
		.addTowerBuildingSpace(14/40, 3/40)
		.addTowerBuildingSpace(24/40, 3/40)
		.addTowerBuildingSpace(34/40, 4/40)
		.addTowerBuildingSpace(9/40, 12/40)
		.addTowerBuildingSpace(19/40, 11/40)
		.addTowerBuildingSpace(3/40, 21/40)
		.addTowerBuildingSpace(29/40, 21/40)
		.addTowerBuildingSpace(17/40, 30/40)
		.addTowerBuildingSpace(31/40, 30/40)
		.addTowerBuildingSpace(23/40, 37/40)
		.addTowerBuildingSpace(33/40, 37/40)
		.build();
    }

    
    
}
