package it.unibo.unrldef.model.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.*;

public class WorldImpl implements World{

    private final static long SPAWNING_TIME = 1;

    private final String name;
    private final Integrity castleIntegrity;
    //private final Bank bank;
    private final Path path;
    private final List<Wave> waves;
    private int waveCounter;
    private final Map<Position, Optional<Tower>> placedTowers;
    private final Set<Tower> availableTowers;
    private final List<Enemy> livingEnemies;
    private final Queue<Enemy> spawningQueue;
    private long timeToNextHorde;
    private long timeToNextSpawn;




    private WorldImpl(String name, Integrity castleIntegrity, Path path, List<Wave> waves, Set<Tower> availableTowers){
        this.name = name;
        this.castleIntegrity = castleIntegrity;
        this.path = path;
        this.waves = waves;
        this.availableTowers = availableTowers;
        this.placedTowers = new HashMap<>();
        this.livingEnemies = new ArrayList<>();
        this.spawningQueue = new LinkedList<>();
        this.timeToNextHorde = 0;
        this.timeToNextSpawn = 0;
        this.waveCounter = 0; 
    }

    public void updateState(long time){
        this.timeToNextHorde = (this.timeToNextHorde - time < 0) ? 0 : (this.timeToNextHorde - time);
        this.timeToNextSpawn = (this.timeToNextSpawn - time < 0) ? 0 : (this.timeToNextSpawn - time);
        this.livingEnemies.stream().forEach(x -> x.updateState(time));
        this.placedTowers.values().stream().filter(Optional::isPresent).forEach(x -> x.get().updateState(time));
        if (timeToNextHorde == 0) {
            Optional<Pair<Horde, Long>> nextHorde = this.waves.get(this.waveCounter).getNextHorde();
            if (!nextHorde.isPresent()) {
                this.waveCounter++;
                nextHorde = this.waves.get(this.waveCounter).getNextHorde();
            }
            this.timeToNextHorde = nextHorde.get().getSecond();
            this.addToQueue(nextHorde.get().getFirst().getEnemies());
        }
        if (timeToNextSpawn == 0 && !this.spawningQueue.isEmpty()) {
            timeToNextSpawn = SPAWNING_TIME;
            Enemy newEnemy = this.spawningQueue.poll();
            Position spawningPoint = this.path.getSpawningPoint();
            newEnemy.setPosition(spawningPoint.getX(), spawningPoint.getY());
            this.livingEnemies.add(newEnemy);
        }
        
    }

    private void addToQueue(Collection<Enemy> Enemies) {
        this.spawningQueue.addAll(Enemies);
    }

    public void startGame(){
        this.waves.get(0).getNextHorde();
    }

   



    @Override
    public void buildTower(Position pos, Tower tower) {
        this.placedTowers.put(pos, Optional.of(tower));
        
    }

    @Override
    public List<Entity> getSceneEntities() {
        List<Entity> ret = new ArrayList<Entity>();
        ret.addAll(this.placedTowers.entrySet().stream().map(x -> x.getValue()).filter(Optional::isPresent).map(x -> x.get()).toList());
        ret.addAll(this.livingEnemies);
        return ret;
    }

    @Override
    public Integrity getCastleIntegrity() {
        return this.castleIntegrity;
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
	public List<Enemy> sorroundingEnemies(Position center, double radius) {
		return this.livingEnemies.stream().filter(x -> (distance(center, x.getPosition().get()) <= radius )).toList();
	}  
    
    private double distance(Position a, Position b ) {
        return Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }

    @Override
    public boolean isGameOver() {
        //TODO
    }
}
