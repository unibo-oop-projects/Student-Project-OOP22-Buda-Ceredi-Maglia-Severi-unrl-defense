package it.unibo.unrldef.model.impl;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.model.api.Horde;
import it.unibo.unrldef.model.api.Wave;

/**
 * Implementation of a wave of enemies' hordes in the game Unreal Defense.
 * 
 * @author danilo.maglia@studio.unibo.it
 */
public final class WaveImpl implements Wave {

    private final Queue<Pair<Horde, Long>> hordesQueue;

    /**
     * Create a new wave.
     */
    public WaveImpl() {
        this.hordesQueue = new LinkedList<>();
    }

    @Override
    public Optional<Pair<Horde, Long>> getNextHorde() {
        final Pair<Horde, Long> horde = this.hordesQueue.poll();
        return horde == null ? Optional.empty() : Optional.of(horde);
    }

    @Override
    public void addHorde(final Horde horde, final long secondsToSpawn) {
        this.hordesQueue.add(new Pair<Horde, Long>(horde, secondsToSpawn));
    }

    @Override
    public boolean isWaveOver() {
        return this.hordesQueue.size() == 0;
    }

}
