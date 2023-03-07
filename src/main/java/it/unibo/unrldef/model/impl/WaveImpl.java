package it.unibo.unrldef.model.impl;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.model.api.Horde;
import it.unibo.unrldef.model.api.Wave;

/**
 * Implementation of a wave of enemies' hordes in the game Unreal Defense
 * @author danilo.maglia@studio.unibo.it
 */
public class WaveImpl implements Wave{

    Queue<Pair<Horde, Long>> hordesQueue;

    public WaveImpl() {
        this.hordesQueue = new LinkedList<Pair<Horde, Long>>();
    }

    @Override
    public Optional<Pair<Horde, Long>> getNextHorde(){
        Pair<Horde, Long> horde = this.hordesQueue.poll();
        return horde == null ? Optional.empty() : Optional.of(horde);
    }

    @Override
    public void addHorde(Horde horde, long secondsToSpawn) {
        this.hordesQueue.add(new Pair<Horde,Long>(horde, secondsToSpawn));
    }

    @Override
    public boolean isWaveOver() {
        return this.hordesQueue.size() == 0;
    }
    
}
