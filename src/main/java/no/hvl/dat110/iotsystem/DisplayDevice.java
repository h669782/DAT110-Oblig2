package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messages.PublishMsg;
import no.hvl.dat110.common.Logger;

public class DisplayDevice {

    private static final int COUNT = 10;

    public static void main(String[] args) {

        Logger.log("Display starting ...");

        String user = "display";
        String topic = "temperature";

        Client client = new Client(user, Common.BROKERHOST, Common.BROKERPORT);

        client.connect();

        client.createTopic(topic);

        client.subscribe(topic);

        for (int i = 0; i < COUNT; i++) {
            Message msg = client.receive();
            if (msg instanceof PublishMsg) {
                String temp = ((PublishMsg) msg).getMessage();
                Logger.log("DISPLAY: " + temp);
            }
        }

        client.unsubscribe(topic);

        client.disconnect();

        Logger.log("Display stopping ...");
    }
}
