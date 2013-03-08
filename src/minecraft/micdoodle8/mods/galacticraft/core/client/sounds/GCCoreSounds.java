package micdoodle8.mods.galacticraft.core.client.sounds;

import micdoodle8.mods.galacticraft.API.IGalacticraftSubModClient;
import micdoodle8.mods.galacticraft.API.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraftforge.client.event.sound.PlayBackgroundMusicEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * Copyright 2012-2013, micdoodle8
 *
 *  All rights reserved.
 *
 */
public class GCCoreSounds
{
    @ForgeSubscribe
    public void onSound(SoundLoadEvent event)
    {
        try
        {
            event.manager.soundPoolSounds.addSound("music/orbit_JC.ogg", GalacticraftCore.class.getResource("/micdoodle8/mods/galacticraft/core/client/sounds/music/orbit_JC.ogg"));
            event.manager.soundPoolSounds.addSound("player/oil.wav", GalacticraftCore.class.getResource("/micdoodle8/mods/galacticraft/core/client/sounds/player/oil.wav"));
            event.manager.soundPoolSounds.addSound("shuttle/shuttle.wav", GalacticraftCore.class.getResource("/micdoodle8/mods/galacticraft/core/client/sounds/shuttle/shuttle.wav"));
            event.manager.soundPoolSounds.addSound("player/parachute.wav", GalacticraftCore.class.getResource("/micdoodle8/mods/galacticraft/core/client/sounds/player/parachute.wav"));
            event.manager.soundPoolSounds.addSound("player/closeairlock.wav", GalacticraftCore.class.getResource("/micdoodle8/mods/galacticraft/core/client/sounds/player/closeairlock.wav"));
            event.manager.soundPoolSounds.addSound("player/openairlock.wav", GalacticraftCore.class.getResource("/micdoodle8/mods/galacticraft/core/client/sounds/player/openairlock.wav"));
            event.manager.soundPoolSounds.addSound("music/scary-ambience.ogg", GalacticraftCore.class.getResource("/micdoodle8/mods/galacticraft/core/client/sounds/music/scary-ambience.ogg"));
        }
        catch (final Exception e)
        {
            System.err.println("[GCCore] Failed to register one or more sounds.");
        }
    }

    @ForgeSubscribe
    public void onMusicSound(PlayBackgroundMusicEvent event)
    {
    	final Minecraft mc = FMLClientHandler.instance().getClient();

    	if (mc.thePlayer.worldObj.provider instanceof IGalacticraftWorldProvider)
    	{
    		for (int i = 0; i < GalacticraftCore.clientSubMods.size(); i++)
    		{
    			final IGalacticraftSubModClient client = GalacticraftCore.clientSubMods.get(i);

    			if (client != null && client.getDimensionName() != null)
    			{
    	    		if (mc.thePlayer.worldObj.provider.getDimensionName().toLowerCase().equals(client.getDimensionName().toLowerCase()))
    	    		{
    	    			if (client.getPathToMusicFile() != null)
    	    			{
    	    				final String[] strings = client.getPathToMusicFile().split("//");

    	    				for (final String string : strings)
    	    				{
    	    					if (string.toLowerCase().contains(".ogg"))
    	    					{
            	        			event.result = new SoundPoolEntry(string, GalacticraftCore.class.getResource(client.getPathToMusicFile()));
            	        			break;
    	    					}
    	    				}
    	    			}
    	    		}
    			}
    		}

    		final int randInt = FMLClientHandler.instance().getClient().thePlayer.worldObj.rand.nextInt(3);

    		if (randInt == 0)
    		{
    			event.result = new SoundPoolEntry("music/spacerace_JC.ogg", GalacticraftCore.class.getResource("/micdoodle8/mods/galacticraft/core/client/sounds/music/spacerace_JC.ogg"));
    		}
    		else if (randInt == 1)
    		{
    			event.result = new SoundPoolEntry("music/scary-ambience.ogg", GalacticraftCore.class.getResource("/micdoodle8/mods/galacticraft/core/client/sounds/music/scary-ambience.ogg"));
    		}
    	}
    	else
    	{
    		event.result = null;
    	}
    }
}