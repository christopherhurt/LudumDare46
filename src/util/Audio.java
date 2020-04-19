package util;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.libc.LibCStdlib;

public class Audio {

    private static final float MAX_GAIN = 100.0f;

    private final int mSoundId;
    private final int mSourceId;

    Audio(String pFilePath, double pGain, boolean pLooping) {
        mSoundId = AL10.alGenBuffers();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer channelsMem = stack.mallocInt(1);
            IntBuffer sampleRateMem = stack.mallocInt(1);

            ShortBuffer soundData = STBVorbis.stb_vorbis_decode_filename(pFilePath, channelsMem, sampleRateMem);
            if (soundData == null) {
                throw new IllegalArgumentException("Failed to load sound " + pFilePath);
            }

            int channels = channelsMem.get();
            int sampleRate = sampleRateMem.get();

            int format = -1;
            if (channels == 1) {
                format = AL10.AL_FORMAT_MONO16;
            } else if (channels == 2) {
                format = AL10.AL_FORMAT_STEREO16;
            }

            AL10.alBufferData(mSoundId, format, soundData, sampleRate);
            LibCStdlib.free(soundData);
        }

        mSourceId = AL10.alGenSources();
        AL10.alSourcef(mSourceId, AL10.AL_MAX_GAIN, MAX_GAIN);
        AL10.alSource3f(mSourceId, AL10.AL_POSITION, 0.0f, 0.0f, 0.0f);
        AL10.alSource3f(mSourceId, AL10.AL_VELOCITY, 0.0f, 0.0f, 0.0f);
        AL10.alSourcef(mSourceId, AL10.AL_GAIN, (float)pGain);
        AL10.alSourcef(mSourceId, AL10.AL_PITCH, 1.0f);
        AL10.alSourcei(mSourceId, AL10.AL_LOOPING, pLooping ? AL10.AL_TRUE : AL10.AL_FALSE);
        AL10.alSourcei(mSourceId, AL10.AL_BUFFER, mSoundId);
    }

    public void play() {
        stop();
        AL10.alSourcePlay(mSourceId);
    }

    void stop() {
        AL10.alSourceStop(mSourceId);
    }

    void destroy() {
        AL10.alDeleteBuffers(mSoundId);
        AL10.alDeleteSources(mSourceId);
    }

}
