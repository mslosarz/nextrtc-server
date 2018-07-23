package org.nextrtc.signalingserver.domain;

import org.nextrtc.signalingserver.repository.ConversationRepository;
import org.nextrtc.signalingserver.repository.MemberRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

@Component
class CloseableContext implements Closeable {
    private final ScheduledExecutorService scheduler;
    private final RTCConnections connections;
    private final MemberRepository members;
    private final ConversationRepository conversations;

    @Inject
    public CloseableContext(ScheduledExecutorService scheduler,
                            RTCConnections connections,
                            MemberRepository members,
                            ConversationRepository conversations) {
        this.scheduler = scheduler;
        this.connections = connections;
        this.members = members;
        this.conversations = conversations;
    }

    @Override
    public void close() throws IOException {
        scheduler.shutdownNow();
        conversations.close();
        members.close();
        connections.close();
    }


}
