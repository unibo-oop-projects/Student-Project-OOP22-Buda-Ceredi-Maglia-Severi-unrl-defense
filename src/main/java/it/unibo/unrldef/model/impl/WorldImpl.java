package it.unibo.unrldef.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.*;

public class WorldImpl implements World{

    final String name;
    final Integrity CastleIntegrity;
    //final Bank bank;
    //final Path path;
    //final List<Wave> Waves;
    final Map<Position, Optional<Tower>> placedTowers;
    final Set<Tower> availableTowers;
    final List<Enemy> livingEnemies;



    WorldImpl(WorldBuilder builder){
        // TODO
    }

    public void startGame(){
        // TODO
    }

    @Override
    private void startWave(Wave wave) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void buildTower(Position pos, Tower tower) {
        this.placedTowers.add(pos, tower);
        
    }

    @Override
    public List<Entity> getSceneEntities() {
        List<Entity> ret = new ArrayList<Entity>();
        ret.addAll(this.availableTowers);
        ret.addAll(this.livingEnemies);
        return ret;
    }

    @Override
    public Integrity getCastleIntegrity() {
        return this.CastleIntegrity;
    }

    @Override
    public int getMoney() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Set<Tower> getAvailableTowers() {
        return this.availableTowers;
    }

    @Override
    public Path getPath() {
        return this.path;
    }

	@Override
	public List<Enemy> sorroundingEnemies(Position center, float radius) {
		// TODO Auto-generated method stub
	}
    
}
