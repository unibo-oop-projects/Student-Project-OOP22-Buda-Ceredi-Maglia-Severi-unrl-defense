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
        .addPathSegment(Direction.DOWN, 15)
		.addPathSegment(Direction.LEFT, 50)
		.addPathSegment(Direction.DOWN, 20)
		.addPathSegment(Direction.RIGHT, 24)
		.addPathSegment(Direction.UP, 6)
		.addPathSegment(Direction.RIGHT, 16)
		.addPathSegment(Direction.DOWN, 22)
		.addPathSegment(Direction.RIGHT, 20)
		.addPathSegment(Direction.DOWN, 18)
		.addPathSegment(Direction.LEFT, 40)
		.addPathSegment(Direction.DOWN, 5)
		.addPathSegment(Direction.END, 0)
		.addWave()
		.addHordeToWave(0, 50000)
		.addMultipleEnemiesToHorde(0, 0, new Orc(Optional.of(new Position(0.75, 0))), (short)5)
		.addMultipleEnemiesToHorde(0, 0, new Goblin(Optional.of(new Position(0.75, 0))), (short)5)
		.addAvailableTower(Cannon.NAME, new Cannon(null))
		.addAvailableTower(Hunter.NAME, new Hunter(null))
		.addTowerBuildingSpace(28, 6)
		.addTowerBuildingSpace(48, 6)
		.addTowerBuildingSpace(62, 8)
		.addTowerBuildingSpace(18, 24)
		.addTowerBuildingSpace(38, 22)
		.addTowerBuildingSpace(6, 42)
		.addTowerBuildingSpace(58, 42)
		.addTowerBuildingSpace(34, 60)
		.addTowerBuildingSpace(62, 60)
		.addTowerBuildingSpace(46, 74)
		.addTowerBuildingSpace(66, 74)
		.build();
    }

    
    
}
