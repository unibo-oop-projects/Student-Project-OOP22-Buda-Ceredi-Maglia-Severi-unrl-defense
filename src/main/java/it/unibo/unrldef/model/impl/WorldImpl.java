package it.unibo.unrldef.model.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
    private final Player player;
    private final Integrity castleIntegrity;
    //private final Bank bank;
    private final Path path;
    private final List<Wave> waves;
    private int waveCounter;
    private final List<Tower> placedTowers;
    private final Map<String, Tower> availableTowers;
    private final Set<Position> validPositions;
    private final List<Enemy> livingEnemies;
    private final Queue<Enemy> spawningQueue;
    private long timeToNextHorde;
    private long timeToNextSpawn;




    public WorldImpl(String name, Player player, Integrity castleIntegrity, Path path, List<Wave> waves, Map<String, Tower> availableTowers, Set<Position> validPositions){
        this.name = name;
        this.player = player;
        this.castleIntegrity = castleIntegrity;
        this.path = path;
        this.waves = waves;
        this.availableTowers = availableTowers;
        this.placedTowers = new ArrayList<>();
        this.livingEnemies = new ArrayList<>();
        this.spawningQueue = new LinkedList<>();
        this.validPositions = validPositions;
        this.timeToNextHorde = 0;
        this.timeToNextSpawn = 0;
        this.waveCounter = 0; 
    }

    public void updateState(long time){
        this.timeToNextHorde = (this.timeToNextHorde - time < 0) ? 0 : (this.timeToNextHorde - time);
        this.timeToNextSpawn = (this.timeToNextSpawn - time < 0) ? 0 : (this.timeToNextSpawn - time);
        this.getSceneEntities().forEach(x -> x.updateState(time));
        this.player.uptatePotions();
        if (timeToNextHorde == 0 && !this.areWavesEnded()) {
            if (this.waves.get(this.waveCounter).isWaveOver()) {
                this.waveCounter++;
            }
            Pair<Horde, Long> nextHorde = this.waves.get(this.waveCounter).getNextHorde().get();
            this.timeToNextHorde = nextHorde.getSecond();
            this.addToQueue(nextHorde.getFirst().getEnemies());
        }
        if (timeToNextSpawn == 0 && !this.spawningQueue.isEmpty()) {
            timeToNextSpawn = SPAWNING_TIME;
            Enemy newEnemy = this.spawningQueue.poll();
            Position spawningPoint = this.path.getSpawningPoint();
            newEnemy.setPosition(spawningPoint.getX(), spawningPoint.getY());
            this.livingEnemies.add(newEnemy);
        }
        
    }
    private Boolean areWavesEnded() {
        return (this.waveCounter == this.waves.size()-1  &&
                this.waves.get(this.waveCounter).isWaveOver());
    }

    private void addToQueue(List<Enemy> Enemies) {
        this.spawningQueue.addAll(Enemies);
    }

    public void startGame(){
        this.waves.get(0).getNextHorde();
    }

    @Override
    public Boolean tryBuildTower(Position pos, String towerName) {
        if(this.validPositions.contains(pos)) {
            this.validPositions.remove(pos);
            Tower newTower = this.availableTowers.get(towerName).copy();
            this.placedTowers.add(newTower);
            newTower.setWorld(this);
            newTower.setPosition(pos.getX(), pos.getY());
            return true;
        }
        return false;
    }

    @Override
    public List<Entity> getSceneEntities() {
        List<Entity> ret = new ArrayList<Entity>();
        ret.addAll(this.placedTowers);
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
    public Map<String, Tower> getAvailableTowers() {
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
        return ((this.waves.size()-1 == this.waveCounter && 
               this.waves.get(this.waveCounter).isWaveOver() &&
               this.livingEnemies.size() == 0) ||
               this.castleIntegrity.getValue() == 0);
    }

    public String getName() {
        return this.name;
    }

}
