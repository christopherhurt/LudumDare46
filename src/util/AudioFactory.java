package util;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;

public final class AudioFactory {

    private static final long DEVICE;
    private static final long CONTEXT;
    static {
        DEVICE = ALC10.alcOpenDevice(ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER));
        CONTEXT = ALC10.alcCreateContext(DEVICE, (IntBuffer)null);
        ALC10.alcMakeContextCurrent(CONTEXT);
        AL.createCapabilities(ALC.createCapabilities(DEVICE));
        AL10.alDistanceModel(AL11.AL_EXPONENT_DISTANCE);
        AL10.alListener3f(AL10.AL_POSITION, 0.0f, 0.0f, 0.0f);
        AL10.alListener3f(AL10.AL_VELOCITY, 0.0f, 0.0f, 0.0f);
    }

    private static final List<Audio> ALL_AUDIO = new ArrayList<>();

    public static Audio createSound(String pFilePath, double pGain) {
        Audio sound = new Audio(pFilePath, pGain, false);
        ALL_AUDIO.add(sound);
        return sound;
    }

    public static Audio createMusic(String pFilePath, double pGain) {
        Audio music = new Audio(pFilePath, pGain, true);
        ALL_AUDIO.add(music);
        return music;
    }

    static void stopAll() {
        ALL_AUDIO.forEach(Audio::stop);
    }

    static void destroy() {
        stopAll();
        ALL_AUDIO.forEach(Audio::destroy);

        ALC10.alcDestroyContext(CONTEXT);
        ALC10.alcCloseDevice(DEVICE);
        ALC.destroy();
    }

}
