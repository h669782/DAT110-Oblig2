package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.common.Logger;

public class TemperatureDevice {

    private static final int COUNT = 10;

    public static void main(String[] args) {

        Logger.log("Temperature device started");

        String user = "sensor";
        String topic = "temperature";

        Client client = new Client(user, Common.BROKERHOST, Common.BROKERPORT);

        client.connect();

        TemperatureSensor sensor = new TemperatureSensor();

        for (int i = 0; i < COUNT; i++) {
            int temp = sensor.read();
            Logger.log("READING: " + temp);
            client.publish(topic, String.valueOf(temp));

            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        client.disconnect();

        Logger.log("Temperature device stopping ...");
    }
}
