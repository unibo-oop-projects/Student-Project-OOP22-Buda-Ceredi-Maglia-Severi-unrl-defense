package it.unibo.unrldef.model.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.*;
import it.unibo.unrldef.model.api.Path.Direction;

public class WorldImpl implements World{

    private final static long SPAWNING_TIME = 1;
    private final static int ENEMY_POWER = 1;

    private final String name;
    private final Player player;
    private final Integrity castleIntegrity;
    //private final Bank bank;
    private final Path path;
    private final List<Wave> waves;
    private int waveCounter;
    private final List<Tower> placedTowers;
    private final Map<String, Tower> availableTowers;
    private final Set<Position> availablePositions;
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
        this.availablePositions = validPositions;
        this.timeToNextHorde = 0;
        this.timeToNextSpawn = 0;
        this.waveCounter = 0; 
    }

    public void updateState(long time){
        this.timeToNextHorde = (this.timeToNextHorde - time < 0) ? 0 : (this.timeToNextHorde - time);
        this.timeToNextSpawn = (this.timeToNextSpawn - time < 0) ? 0 : (this.timeToNextSpawn - time);
        this.livingEnemies.removeAll(this.livingEnemies.stream().filter(Enemy::isDead).toList());
        this.livingEnemies.stream().filter(Enemy::hasReachedEndOfPath).forEach(x -> this.castleIntegrity.damage(ENEMY_POWER));
        this.livingEnemies.removeAll(this.livingEnemies.stream().filter(Enemy::hasReachedEndOfPath).toList());
        this.getSceneEntities().forEach(x -> x.updateState(time));
        //this.player.uptatePotions();
        //this is temporary (
        PlayerImpl tmp = (PlayerImpl) this.player;
        tmp.updateSpellState(time);
        // )
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
        if(this.availablePositions.contains(pos)) {
            this.availablePositions.remove(pos);
            Tower newTower = this.availableTowers.get(towerName).copy();
            this.placedTowers.add(newTower);
            newTower.setParentWorld(this);
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
        return (this.areWavesEnded() && this.livingEnemies.size() == 0) || this.castleIntegrity.isCompromised();
    }

    public String getName() {
        return this.name;
    }

    public static class Builder {
        private String name;
        private Player player;
        private Integrity castleIntegrity;
        private Path path;
        private List<List<Pair<Horde, Long>>> wavesTemp;
        private Map<String, Tower> availableTowers;
        private Set<Position> validTowersPositions;

        public Builder(String worldName, Player player, Position spawnPoint, int castleHearts) {
            this.name = worldName;
            this.player = player;
            this.path = new PathImpl(spawnPoint);
            this.wavesTemp = new ArrayList<>();
            this.castleIntegrity = new IntegrityImpl(castleHearts);
            this.availableTowers = new HashMap<>();
            this.validTowersPositions = new HashSet<>();
        }

        public void addPathSegment(Direction direction, double Lenght) {
            this.path.addDirection(direction, Lenght);
        }

        public void addWave() {
            this.wavesTemp.add(new ArrayList<>());
        }

        public void addHordeToWave (int waveIndex, long hordeDuration) throws IndexOutOfBoundsException{
            this.wavesTemp.get(waveIndex).add(new Pair<Horde,Long>(new HordeImpl(), hordeDuration));
        }

        public void addEnemyToHorde (int waveIndex, int hordeIndex, Enemy enemy) throws IndexOutOfBoundsException{
            this.wavesTemp.get(waveIndex).get(hordeIndex).getFirst().addEnemy(enemy);
        }

        public void addMultipleEnemiesToHorde (int waveIndex, int hordeIndex, Enemy enemy, short numberOfEnemies) throws IndexOutOfBoundsException {
            this.wavesTemp.get(waveIndex).get(hordeIndex).getFirst().addMultipleEnemies(enemy, numberOfEnemies);
        }

        public void addAvailableTower( String name, Tower tower ) {
            this.availableTowers.put(name, tower);
        }

        public void addTowerBuildingSpace (double x, double y) {
            this.validTowersPositions.add(new Position(x, y));
        }


        public WorldImpl build() throws IllegalStateException {
            if (this.name == null || this.player == null || this.castleIntegrity == null || 
                this.path == null) {
                    throw new IllegalStateException("some fields are not initialized");
            }
            List<Wave> waves = new ArrayList<>();

            this.wavesTemp.forEach(x -> waves.add(new WaveImpl()));
            for (int i = 0; i < this.wavesTemp.size(); i++) {
                for (Pair<Horde, Long> horde : this.wavesTemp.get(i)) {
                    waves.get(i).addHorde(horde.getFirst(), horde.getSecond());
                }
            }

            return new WorldImpl(this.name, this.player, this.castleIntegrity, this.path, waves, this.availableTowers, this.validTowersPositions);
        }
    }

    @Override
    public Set<Position> getAvailablePositions() {
        return this.availablePositions;
    }
}
