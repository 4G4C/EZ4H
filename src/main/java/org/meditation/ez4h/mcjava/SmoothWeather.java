package org.meditation.ez4h.mcjava;

import com.github.steveice10.mc.protocol.data.game.world.notify.ClientNotification;
import com.github.steveice10.mc.protocol.data.game.world.notify.RainStrengthValue;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerNotifyClientPacket;
import org.meditation.ez4h.bedrock.Client;

public class SmoothWeather {
    public static void changeWeather(float to,boolean isThunder,Client client){
        WeatherThread weatherThread=new WeatherThread();
        System.out.println(isThunder);
        weatherThread.isThunder=isThunder;
        weatherThread.to=to;
        weatherThread.client=client;
        Thread thread = new Thread(weatherThread);
        thread.start();
    }
}

class WeatherThread implements Runnable {
    public boolean isThunder,mode=false;//mode true plus,false minus
    public Client client;
    public float to;
    public void run() {
        try {
            System.out.println(isThunder);
            if (to == 1) {
                mode = true;
                to = 0;
            } else {
                to = 1;
            }
            for (int i = 0; i < 40; i++) {
                if (mode) {
                    to += 0.025;
                } else {
                    to -= 0.025;
                }
                if (isThunder) {
                    client.JESession.send(new ServerNotifyClientPacket(ClientNotification.THUNDER_STRENGTH, new RainStrengthValue(to)));
                }else{
                    client.JESession.send(new ServerNotifyClientPacket(ClientNotification.RAIN_STRENGTH, new RainStrengthValue(to)));
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}