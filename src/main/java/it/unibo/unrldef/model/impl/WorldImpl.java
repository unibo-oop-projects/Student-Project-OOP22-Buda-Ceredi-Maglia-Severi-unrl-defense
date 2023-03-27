package it.unibo.unrldef.model.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.*;
import it.unibo.unrldef.model.api.Path.Direction;

public class WorldImpl implements World{

    private final static long SPAWNING_TIME = 1500;
    private final static int ENEMY_POWER = 1;
    private final static int PATH_DEPHT = 3;
    private final static double SAFETY_MARGIN = 0.2;

    private final String name;
    private final Player player;
    private final Integrity castleIntegrity;
    private final Bank bank;
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




    public WorldImpl(String name, Player player, Integrity castleIntegrity, Path path, List<Wave> waves, Map<String, Tower> availableTowers, Set<Position> validPositions, Bank bank){
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
        this.bank = bank;
    }

    public void updateState(long time){
        this.timeToNextHorde = (this.timeToNextHorde - time < 0) ? 0 : (this.timeToNextHorde - time);
        this.timeToNextSpawn = (this.timeToNextSpawn - time < 0) ? 0 : (this.timeToNextSpawn - time);
        this.livingEnemies.stream().filter(Enemy::isDead).forEach(x -> this.bank.addMoney(x.getDropAmount()));
        this.livingEnemies.removeAll(this.livingEnemies.stream().filter(Enemy::isDead).toList());
        this.livingEnemies.stream().filter(Enemy::hasReachedEndOfPath).forEach(x -> this.castleIntegrity.damage(ENEMY_POWER));
        this.livingEnemies.removeAll(this.livingEnemies.stream().filter(Enemy::hasReachedEndOfPath).toList());
        this.livingEnemies.forEach(x -> x.updateState(time));
        this.placedTowers.forEach(x -> x.updateState(time));
        this.player.updateSpellState(time);
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
            Random rand = new Random();
            newEnemy.setPosition(spawningPoint.getX() + rand.nextInt(-PATH_DEPHT/2, PATH_DEPHT/2), spawningPoint.getY() + rand.nextInt(-PATH_DEPHT/2, PATH_DEPHT/2));
            this.livingEnemies.add(newEnemy);
        }   
    }
    private Boolean areWavesEnded() {
        return (this.waveCounter == this.waves.size()-1  &&
                this.waves.get(this.waveCounter).isWaveOver());
    }

    private List<Enemy> enemiesInArea(Position upLeft, Position downRight) {
        return this.livingEnemies.stream()
                            .filter(x -> x.getPosition().get().getX() >= upLeft.getX() )
                            .filter(x -> x.getPosition().get().getX() <= downRight.getX())
                            .filter(x -> x.getPosition().get().getY() >= upLeft.getY())
                            .filter(x -> x.getPosition().get().getY() <= downRight.getY())
                            .toList();
    }

    public List<Enemy> sorroundingEnemies(Position center, double radius) {
        Position pathCur = this.path.getSpawningPoint().copy();
        boolean end = false;
        Optional<Enemy> firstInLine = Optional.empty();
        int i = 0;
        List<Enemy> sorroundingEnemies = this.livingEnemies.stream().filter(x -> (distance(center, x.getPosition().get()) <= radius )).collect(Collectors.toList());
        while (!end) {
            Pair<Direction, Double> dir = this.path.getDirection(i);
            Position A;
            Position B = new Position(0, 0);
            Position ul;
            Position dr;
            Optional<Enemy> tmp = Optional.empty();
            switch (dir.getFirst()) {
                case UP:
                    A = pathCur;
                    B = new Position(A.getX(), A.getY() - dir.getSecond());
                    ul = new Position(B.getX() - (PATH_DEPHT + SAFETY_MARGIN)/2, B.getY());
                    dr = new Position(A.getX() + (PATH_DEPHT + SAFETY_MARGIN)/2, A.getY());
                    tmp = sorroundingEnemies.stream()
                            .filter(x -> this.enemiesInArea(ul, dr).contains(x))
                            .reduce((a, b) -> a.getPosition().get().getY() < b.getPosition().get().getY() ? a : b);
                    break;
                case DOWN:
                    A = pathCur;
                    B = new Position(A.getX(), A.getY() + dir.getSecond());
                    ul = new Position(A.getX() - (PATH_DEPHT + SAFETY_MARGIN)/2, A.getY());
                    dr = new Position(B.getX() + (PATH_DEPHT + SAFETY_MARGIN)/2, B.getY());
                    tmp = sorroundingEnemies.stream()
                            .filter(x -> this.enemiesInArea(ul, dr).contains(x))
                            .reduce((a, b) -> a.getPosition().get().getY() > b.getPosition().get().getY() ? a : b);
                    break;
                case RIGHT:
                    A = pathCur;
                    B = new Position(A.getX() + dir.getSecond(), A.getY());
                    ul = new Position(A.getX(), A.getY() - (PATH_DEPHT + SAFETY_MARGIN)/2 );
                    dr = new Position(B.getX(), B.getY() + (PATH_DEPHT + SAFETY_MARGIN)/2);
                    tmp = sorroundingEnemies.stream()
                            .filter(x -> this.enemiesInArea(ul, dr).contains(x))
                            .reduce((a, b) -> a.getPosition().get().getX() > b.getPosition().get().getX() ? a : b);
                    break;
                case LEFT:
                    A = pathCur;
                    B = new Position(A.getX() - dir.getSecond(), A.getY());
                    ul = new Position(B.getX(), B.getY() - (PATH_DEPHT + SAFETY_MARGIN)/2);
                    dr = new Position(A.getX(), A.getY() + (PATH_DEPHT + SAFETY_MARGIN)/2);
                    tmp = sorroundingEnemies.stream()
                            .filter(x -> this.enemiesInArea(ul, dr).contains(x))
                            .reduce((a, b) -> a.getPosition().get().getX() < b.getPosition().get().getX() ? a : b);
                    break;
                default:
                    end = true;
                    break;            
            }
            if (!end) {
                if (tmp.isPresent()) {
                    firstInLine = tmp;
                }
                pathCur = B;
                i++;
            }
        }
        if (firstInLine.isPresent()) {
                sorroundingEnemies.remove(firstInLine.get());
                sorroundingEnemies.add(0, firstInLine.get());
        }
            return sorroundingEnemies;
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
            Tower newTower = this.availableTowers.get(towerName).copy();
            if (this.bank.trySpend(newTower.getCost())) {
                this.availablePositions.remove(pos);
                this.placedTowers.add(newTower);
                newTower.setParentWorld(this);
                newTower.setPosition(pos.getX(), pos.getY());
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Entity> getSceneEntities() {
        List<Entity> ret = new ArrayList<Entity>();
        this.livingEnemies.sort((a, b) -> {
            if (a.getPosition().get().getY() > b.getPosition().get().getY()) {
                return 1;
            } else if (a.getPosition().get().getY() < b.getPosition().get().getY()) {
                return -1;
            } else {
                if (a.getPosition().get().getX() > b.getPosition().get().getX()) {
                    return 1;
                } else if (a.getPosition().get().getX() < b.getPosition().get().getX()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        ret.addAll(this.placedTowers);
        ret.addAll(this.livingEnemies);
        ret.addAll(((PlayerImpl) this.player).getActiveSpells());
        return ret;
    }

    @Override
    public Integrity getCastleIntegrity() {
        return this.castleIntegrity;
    }

     
    @Override
    public double getMoney() {
        return this.bank.getMoney();
    } 

    @Override
    public Set<Tower> getAvailableTowers() {
        return this.availableTowers.entrySet().stream().map(x -> x.getValue()).collect(Collectors.toSet());
    }

    @Override
    public Path getPath() {
        return this.path;
    }
    
    
    private double distance(Position a, Position b ) {
        return Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }

    @Override
    public GameState gameState() {
        if (this.areWavesEnded() && this.livingEnemies.size() == 0) {
            return GameState.VICTORY;
        } else if ( this.castleIntegrity.isCompromised() ) {
            return GameState.DEFEAT;
        } else {
            return GameState.PLAYING;
        }
    }

    public String getName() {
        return this.name;
    }
    
    @Override
    public Set<Position> getAvailablePositions() {
        return this.availablePositions;
    }

    public static class Builder {
        private String name;
        private Player player;
        private Integrity castleIntegrity;
        private Path path;
        private Bank bank;
        private List<List<Pair<Horde, Long>>> wavesTemp;
        private Map<String, Tower> availableTowers;
        private Set<Position> validTowersPositions;

        public Builder(String worldName, Player player, Position spawnPoint, int castleHearts, double startingMoney) {
            this.name = worldName;
            this.player = player;
            this.path = new PathImpl(spawnPoint);
            this.wavesTemp = new ArrayList<>();
            this.castleIntegrity = new IntegrityImpl(castleHearts);
            this.bank = new Bank(startingMoney);
            this.availableTowers = new HashMap<>();
            this.validTowersPositions = new HashSet<>();
        }

        public Builder addPathSegment(Direction direction, double Lenght) {
            this.path.addDirection(direction, Lenght);
            return this;
        }

        public Builder addWave() {
            this.wavesTemp.add(new ArrayList<>());
            return this;
        }

        public Builder addHordeToWave (int waveIndex, long hordeDuration) throws IndexOutOfBoundsException{
            this.wavesTemp.get(waveIndex).add(new Pair<Horde,Long>(new HordeImpl(), hordeDuration));
            return this;
        }

        public Builder addEnemyToHorde (int waveIndex, int hordeIndex, Enemy enemy) throws IndexOutOfBoundsException{
            this.wavesTemp.get(waveIndex).get(hordeIndex).getFirst().addEnemy(enemy);
            return this;
        }

        public Builder addMultipleEnemiesToHorde (int waveIndex, int hordeIndex, Enemy enemy, short numberOfEnemies) throws IndexOutOfBoundsException {
            this.wavesTemp.get(waveIndex).get(hordeIndex).getFirst().addMultipleEnemies(enemy, numberOfEnemies);
            return this;
        }

        public Builder addAvailableTower( String name, Tower tower ) {
            this.availableTowers.put(name, tower);
            return this;
        }

        public Builder addTowerBuildingSpace (double x, double y) {
            this.validTowersPositions.add(new Position(x, y));
            return this;
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

            WorldImpl ret = new WorldImpl(this.name, this.player, this.castleIntegrity, this.path, waves, this.availableTowers, this.validTowersPositions, this.bank);
            for (int i = 0; i < this.wavesTemp.size(); i++) {
                for (Pair<Horde, Long> horde : this.wavesTemp.get(i)) {
                    horde.getFirst().getEnemies().forEach(x -> x.setParentWorld(ret));
                }
            }

            this.player.setGameMap(ret);

            return ret;
        }
    }    
}
