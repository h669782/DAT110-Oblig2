package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.messagetransport.Connection;

public class Storage {

    protected ConcurrentHashMap<String, Set<String>> subscriptions;
    protected ConcurrentHashMap<String, ClientSession> clients;

    public Storage() {
        subscriptions = new ConcurrentHashMap<>();
        clients = new ConcurrentHashMap<>();
    }

    public Collection<ClientSession> getSessions() {
        return clients.values();
    }

    public Set<String> getTopics() {
        return subscriptions.keySet();
    }

    public ClientSession getSession(String user) {
        return clients.get(user);
    }

    public Set<String> getSubscribers(String topic) {
        return subscriptions.getOrDefault(topic, ConcurrentHashMap.newKeySet());
    }

    public void addClientSession(String user, Connection connection) {
        clients.put(user, new ClientSession(user, connection));
    }

    public void removeClientSession(String user) {
        clients.remove(user);
    }

    public void createTopic(String topic) {
        subscriptions.putIfAbsent(topic, ConcurrentHashMap.newKeySet());
    }

    public void deleteTopic(String topic) {
        subscriptions.remove(topic);
    }

    public void addSubscriber(String user, String topic) {
        subscriptions.computeIfAbsent(topic, k -> ConcurrentHashMap.newKeySet()).add(user);
    }

    public void removeSubscriber(String user, String topic) {
        Set<String> subs = subscriptions.get(topic);
        if (subs != null) {
            subs.remove(user);
            if (subs.isEmpty()) {
                subscriptions.remove(topic);
            }
        }
    }
}
