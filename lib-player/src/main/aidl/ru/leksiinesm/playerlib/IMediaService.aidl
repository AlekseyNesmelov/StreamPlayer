// IMediaService.aidl
package ru.leksiinesm.playerlib;

/**
 * AIDL interface of media player
 */
interface IMediaService {

    /**
     * Start playing stream from source
     */
    void start(String source);

    /**
     * Stop playing stream
     */
    void stop();

    /**
     * Checks if media player is playing
     */
    boolean isPlaying();
}
