package com.zx.moa.ioa.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;

public class AudioChange
{

    public static void changeToMp3(String sourcePath, String targetPath) throws InputFormatException
    {

        File source = new File(sourcePath);
        File target = new File(targetPath);
        
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);

        try
        {
            Encoder encoder = new Encoder();
            encoder.encode(source, target, attrs);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (InputFormatException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (EncoderException e)
        {
            // e.printStackTrace();
        }
    }
}
