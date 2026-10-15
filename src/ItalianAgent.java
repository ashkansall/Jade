package jadelab1;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import java.net.*;
import java.io.*;

public class ItalianAgent extends Agent {
    protected void setup() {
        // Describe this agent
        DFAgentDescription dfad = new DFAgentDescription();
        dfad.setName(getAID());

        // Register the Italian-to-English service
        ServiceDescription service = new ServiceDescription();
        service.setType("answers");
        service.setName("italian-english");

        dfad.addServices(service);

        try {
            DFService.register(this, dfad);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }

        // Start the agent's behaviour
        addBehaviour(new ItalianEnglishBehaviour(this));
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
    }

    public String makeRequest(String word) {
        StringBuffer response = new StringBuffer();

        try {
            URL url = new URL("https://dict.org/bin/Dict");
            URLConnection connection = url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            connection.setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded");

            String request = "Form=Dict1&Strategy=*&Database=" +
                    URLEncoder.encode("fd-ita-eng") +
                    "&Query=" +
                    URLEncoder.encode(word) +
                    "&submit=Submit+query";

            DataOutputStream output = new DataOutputStream(connection.getOutputStream());

            output.writeBytes(request);
            output.flush();
            output.close();

            DataInputStream input = new DataInputStream(connection.getInputStream());

            String line;

            while ((line = input.readLine()) != null) {
                response.append(line);
            }

            input.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return response.substring(
                response.indexOf("<hr>") + 4,
                response.lastIndexOf("<hr>"));
    }
}

class ItalianEnglishBehaviour extends CyclicBehaviour {
    ItalianAgent agent;

    public ItalianEnglishBehaviour(ItalianAgent agent) {
        this.agent = agent;
    }

    public void action() {
        MessageTemplate template = MessageTemplate.MatchOntology("italian-english");

        ACLMessage message = agent.receive(template);

        if (message == null) {
            block();
        } else {
            String word = message.getContent();

            ACLMessage reply = message.createReply();
            reply.setPerformative(ACLMessage.INFORM);

            String response = agent.makeRequest(word);

            reply.setContent(response);
            agent.send(reply);
        }
    }
}